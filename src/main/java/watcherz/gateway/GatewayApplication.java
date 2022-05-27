package watcherz.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {
	@Autowired
	private DiscoveryClient discoveryClient;

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
}
