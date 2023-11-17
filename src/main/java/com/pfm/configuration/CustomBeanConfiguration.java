package com.pfm.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySources({@PropertySource("config.properties"),@PropertySource("queries.properties")})
public class CustomBeanConfiguration {
	
	@Bean
	public RestTemplate buildRestTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}

}
