package keilen.gdkm.weixin.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD) // 字段获取信息
public abstract class InMessage implements Serializable {
	@XmlElement(name = "ToUserName")
	private String toUserName; // 开发者微信号
	@XmlElement(name = "FromUserName")
	private String fromUserName; // 发送方帐号（一个OpenID）
	@XmlElement(name = "CreateTime")
	private String createTime; // 消息创建时间 （整型）
	@XmlElement(name = "MsgType")
	private String msgType; // 消息类型，文本为text
	@XmlElement(name = "MsgId")
	private String msgId; // 消息id，64位整型

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	abstract public String toString();

}
