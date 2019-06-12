package keilen.gdkm.weixin.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonMappingException;

import keilen.gdkm.weixin.domain.InMessage;
import keilen.gdkm.weixin.service.MessageConvertHelper;

@RestController
@RequestMapping("/kemao/MessageReceiver")
public class MessageReceiverController {

	private static final Logger LOG = LoggerFactory.getLogger(MessageReceiverController.class);

	@Autowired
	private RedisTemplate<String, ? extends InMessage> inMessaageTemplate;

	@GetMapping
	public String echo(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr) {
		return echostr;
	}

	@PostMapping
	public String getMessage(@RequestBody String xml) throws IOException {
		LOG.trace("收到消息原文：\n{}\n", xml);
		InMessage inMessage = MessageConvertHelper.convert(xml);
		LOG.debug("转换后的消息对象：\n{}\n", inMessage);
		// 序列化操作
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bos);
		out.writeObject(inMessage);
		byte[] data = bos.toByteArray();
		String channel = "weixin_" + inMessage.getMsgType();
		// 放入消息队列
		inMessaageTemplate.execute(new RedisCallback<InMessage>() {

			@Override
			public InMessage doInRedis(RedisConnection connection) throws DataAccessException {
				connection.publish(channel.getBytes(), data);
				return null;
			}
		});
		inMessaageTemplate.convertAndSend(channel, inMessage);
		return "success";
	}

}
