package keilen.gdkm.weixin.service;

public interface AccessTokenManager {
	String getToken(String account) throws RuntimeException;
}
