package keilen.gdkm.weixin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import keilen.gdkm.weixin.domain.InMessage;
import keilen.gdkm.weixin.service.JsonRedisSerializer;

@SpringBootApplication
public class WeiXinApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeiXinApplication.class, args);
	}

	@Bean
	public RedisTemplate<String, ? extends InMessage> inMessageTemplate(
			@Autowired RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, ? extends InMessage> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		template.setValueSerializer(new JsonRedisSerializer<InMessage>());
		return template;
	}
}
