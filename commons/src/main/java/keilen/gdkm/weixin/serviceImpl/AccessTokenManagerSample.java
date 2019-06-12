package keilen.gdkm.weixin.serviceImpl;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;

import keilen.gdkm.weixin.domain.ResponseError;
import keilen.gdkm.weixin.domain.ResponseMessage;
import keilen.gdkm.weixin.domain.ResponseToken;
import keilen.gdkm.weixin.service.AccessTokenManager;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AccessTokenManagerSample implements AccessTokenManager {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String getToken(String account) throws RuntimeException {
		String appid = "wx556cbe1ff0dcbe5e";
		String appSecret = "5940598cbe4faa3bf4600a3747586a6c";
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"//
				+ "&appid=" + appid//
				+ "&secret=" + appSecret;
		HttpClient hc = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder(URI.create(url))//
				.GET()
				.build();
		ResponseMessage msg;
		try {
			HttpResponse<String> response = hc.send(request, BodyHandlers.ofString(Charset.forName("UTF-8")));
			String body = response.body();
			if (body.contains("errcode")) {
				msg = objectMapper.readValue(body, ResponseError.class);
				msg.setStatus(2);
			} else {
				msg = objectMapper.readValue(body, ResponseToken.class);
				msg.setStatus(1);
			}

			if (msg.getStatus() == 1) {
				return ((ResponseToken) msg).getToken();
			}
		} catch (Exception e) {
			throw new RuntimeException("获取访问令牌出现问题：" + e.getLocalizedMessage(), e);
		}

		throw new RuntimeException("获取访问令牌出现问题，"
				+ "错误代码=" + ((ResponseError) msg).getErrorCode() 
				+ "，错误信息=" + ((ResponseError) msg).getErrorMessage());
	}

}
