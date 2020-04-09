package com.dev.kafka.consumer;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

public class Consumer {

	public KafkaConsumer<String, String> getDefaultConsumer(){
		
		String topic = "twitter_tweets";
		Properties properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ConsumerConfig.GROUP_ID_CONFIG,"twitter-consumer");
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		KafkaConsumer <String, String> consumer = new KafkaConsumer<String, String>(properties);
		consumer.subscribe(Arrays.asList(topic));
		return  consumer;
		
		
	}
	
	public void consume() throws IOException{
		RestHighLevelClient client = ElasticsearchClient.createClient();
		
		KafkaConsumer<String, String> consumer = getDefaultConsumer();
		while(true){
			
			
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
			
			for(ConsumerRecord<String, String> record: records){
			
				IndexRequest request = new IndexRequest("twitter","tweets").source(record.value(), XContentType.JSON);
				
				IndexResponse response = client.index(request, RequestOptions.DEFAULT);
				String id = response.getId();
				System.out.println(id);
				try{
					Thread.sleep(1000);
					
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
				
				
			}
			
		}
		
		
		
	}
	
	public static void main(String [] args) throws IOException{
		Consumer consumer = new Consumer();
		consumer.consume();
	}
	
}
