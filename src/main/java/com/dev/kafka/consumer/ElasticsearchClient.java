package com.dev.kafka.consumer;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

public class ElasticsearchClient {

	public static RestHighLevelClient createClient(){
	
		String hostname = "kafka-1785150758.ap-southeast-2.bonsaisearch.net:443";
		String username = "mozw8ok95r";
		String password = "1ropju79ok";
		
		final CredentialsProvider cp = new BasicCredentialsProvider();
		cp.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
		RestClientBuilder builder =  RestClient.builder(new HttpHost(hostname,443,"https")).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback(){
			public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder){
				return httpClientBuilder.setDefaultCredentialsProvider(cp);
			}
		});
		
		RestHighLevelClient client = new RestHighLevelClient(builder);
		return client;
	}
	
}
