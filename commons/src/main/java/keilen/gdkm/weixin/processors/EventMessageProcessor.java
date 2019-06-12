package keilen.gdkm.weixin.processors;

import keilen.gdkm.weixin.domain.text.EventInMessage;

public interface EventMessageProcessor {

	void onMessage(EventInMessage msg);
}
