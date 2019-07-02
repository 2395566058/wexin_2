package keilen.gdkm.weixin.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;

import keilen.gdkm.weixin.domain.User;
import keilen.gdkm.weixin.domain.text.TextOutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeiXinProxy {

	private static final Logger LOG = LoggerFactory.getLogger(WeiXinProxy.class);
	private ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	private AccessTokenManager accessTokenManager;
	private HttpClient httpClient = HttpClient.newBuilder().build();

	public User getUser(String account, String openId) {

		String accessToken = accessTokenManager.getToken(account);

		String url = "https://api.weixin.qq.com/cgi-bin/user/info" + "?access_token=" + accessToken + "&openid="
				+ openId + "&lang=zh_CN";
		HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();
		try {
			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString(Charset.forName("UTF-8")));
			String body = response.body();
			LOG.trace("调用远程接口返回的内容：\n{}", body);
			if (!body.contains("errcode")) {
				User user = objectMapper.readValue(body, User.class);
				return user;
			}
		} catch (Exception e) {
			LOG.error("调用远程接口出现错误：" + e.getLocalizedMessage(), e);
		}
		return null;
	}

	public void sendText(String account, String openId, String text) {
		TextOutMessage out = new TextOutMessage(openId, text);
		try {
			String json = this.objectMapper.writeValueAsString(out);
			LOG.trace("客服接口要发送的消息内容：{}", json);

			String accessToken = accessTokenManager.getToken(account);
			String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send" + "?access_token=" + accessToken;
			HttpRequest request = HttpRequest.newBuilder(URI.create(url))
					.POST(BodyPublishers.ofString(json, Charset.forName("UTF-8"))).build();
			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString(Charset.forName("UTF-8")));
			LOG.trace("发送客服消息的结果：{}", response.body());
		} catch (IOException | InterruptedException e) {
			LOG.error("发送消息出现问题：" + e.getLocalizedMessage(), e);
		}
	}
}
