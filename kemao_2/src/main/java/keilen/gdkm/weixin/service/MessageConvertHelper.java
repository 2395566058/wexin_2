package keilen.gdkm.weixin.service;

import java.io.StringReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXB;

import keilen.gdkm.weixin.domain.InMessage;
import keilen.gdkm.weixin.domain.text.*;

public class MessageConvertHelper {

	private static final Map<String, Class<? extends InMessage>> map = new ConcurrentHashMap<String, Class<? extends InMessage>>();
	static {
		map.put("text", TextInMessage.class);
		map.put("image", ImageInMessage.class);
		map.put("voice", VoiceInMessage.class);
		map.put("video", VideoInMessage.class);
		map.put("shortvideo", ShortvideoInMessage.class);
		map.put("location", LocationInMessage.class);
		map.put("link", LinkInMessage.class);
		map.put("event",EventInMessage.class);
	}

	public static <T extends InMessage> T convert(String xml) {
		String msgType = xml.substring(xml.indexOf("<MsgType><![CDATA[")+18);
		msgType = msgType.substring(0, msgType.indexOf("]"));
		Class<? extends InMessage> c = map.get(msgType);
		T object = (T) JAXB.unmarshal(new StringReader(xml), c);

		return object;
	}
}
