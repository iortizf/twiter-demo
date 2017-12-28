package com.mx.baz.tweet.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

@Configuration
public class TwitterConfig {

	@Autowired
	private Environment env;
	
	@Bean
	public Twitter twitter() {
		return new TwitterTemplate(env.getProperty("spring.social.twitter.appId"),
				env.getProperty("spring.social.twitter.appSecret"),
				env.getProperty("twitter.access.token"),
				env.getProperty("twitter.access.token.secret"));
	}
	 
}
