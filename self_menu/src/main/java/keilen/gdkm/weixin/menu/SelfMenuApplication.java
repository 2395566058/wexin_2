package keilen.gdkm.weixin.menu;

import keilen.gdkm.weixin.CommonsConfig;
import keilen.gdkm.weixin.service.AccessTokenManager;
import keilen.gdkm.weixin.serviceImpl.AccessTokenManagerSample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("keilen.gdkm")
@EntityScan("keilen.gdkm")
public class SelfMenuApplication implements CommonsConfig {

	public static void main(String[] args) {
		SpringApplication.run(SelfMenuApplication.class, args);
		AccessTokenManager access=new AccessTokenManagerSample();
	}
}
