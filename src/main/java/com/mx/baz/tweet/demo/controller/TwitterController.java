package com.mx.baz.tweet.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.baz.tweet.demo.service.TweeterService;
import reactor.core.publisher.Flux;
import twitter4j.Status;

@RestController
public class TwitterController {
	
	@Autowired
	private TweeterService tweeterService;
	
	@GetMapping(path="/tweets", produces =  MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Status> all(){
		Flux<Status> flux = tweeterService.fetchTweets();
		return flux;
	}
	
	@GetMapping(path="/spring-social/tweets", produces =  MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Tweet> tweets(){
		Flux<Tweet> flux = tweeterService.getTweets();
		System.out.println("Retornando Tweets");
		flux.subscribe(tweet -> System.out.println(tweet), error -> System.err.println(error));
		return flux;
	}
	
	
}
