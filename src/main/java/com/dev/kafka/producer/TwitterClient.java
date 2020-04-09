package com.dev.kafka.producer;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

public class TwitterClient {

	public static Client createClient(BlockingQueue<String> msgQueue){
		String consumerKey = "nxDIqgAwZDdRC41GVJSrWnxyv";
		String consumerSecret = "RvXMDA1SMRDpMya39P4tOCmeuqSL6A87zV9BUimkqbXas3IJzJ";
		String token = "1247415828980436993-bEQVFbyCY96lZOthMzaS7nW3j6dTbG";
		String secret = "QAN0ZHE3L713090evTZx8fhFSokWMgUZyZVV2IdWd9RfZ";
		
		

		/** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
		Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
		StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
		// Optional: set up some followings and track terms
		List<Long> followings = Lists.newArrayList(1234L, 566788L);
		List<String> terms = Lists.newArrayList("kafka");
		hosebirdEndpoint.followings(followings);
		hosebirdEndpoint.trackTerms(terms);

		// These secrets should be read from a config file
		Authentication hosebirdAuth = new OAuth1(consumerKey, consumerSecret, token, secret);
		
		
		ClientBuilder builder = new ClientBuilder()
				  .name("Hosebird-Client-01")                              // optional: mainly for the logs
				  .hosts(hosebirdHosts)
				  .authentication(hosebirdAuth)
				  .endpoint(hosebirdEndpoint)
				  .processor(new StringDelimitedProcessor(msgQueue));
	                       // optional: use this if you want to process client events

				Client hosebirdClient = builder.build();
				return hosebirdClient;
		
	}
	
}
