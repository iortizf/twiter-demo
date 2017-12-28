package com.mx.baz.tweet.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.Stream;
import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamWarningEvent;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

@Service
public class TweeterService {
	
	@Autowired
    private Twitter twitter;
	
	private Stream userStream;
	
	public Flux<Status> fetchTweets() {
		Flux<Status> flux = Flux.create(emitter -> {
			 final TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
	            twitterStream.addListener(new StatusAdapter() {
	                public void onStatus(Status status) {
	                	emitter.next(status);
	                }
	                public void onException(Exception ex) {
	                	emitter.error(ex);
	                }
	            });
	            twitterStream.sample();
			});
		return flux;
	}
	
	public Flux<Tweet> getTweets(){		
		System.out.println("Obteniendo Tweets");
		Flux<Tweet> flux = Flux.create(emitter ->{			
			StreamListener streamListener = new StreamListener() {				
				@Override
				public void onWarning(StreamWarningEvent warningEvent) {
					System.out.println("onWarning Tweets");				
				}
				
				@Override
				public void onTweet(Tweet tweet) {
					System.out.println("onTweet Tweets" + tweet.toString());
					emitter.next(tweet);					
				}
				
				@Override
				public void onLimit(int numberOfLimitedTweets) {
					System.out.println("onLimit Tweets");			
				}
				
				@Override
				public void onDelete(StreamDeleteEvent deleteEvent) {
					System.out.println("onDelete Tweets");			
				}
			};
			List<StreamListener> listeners = new ArrayList<>();
			listeners.add(streamListener);
			userStream = twitter.streamingOperations().sample(listeners);					
		});
		return flux;
	}
}
