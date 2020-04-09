package com.dev.kafka.producer;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.hbc.core.Client;

public class Producer {
	
 Logger logger = LoggerFactory.getLogger(Producer.class.getName());

	public static KafkaProducer <String, String>  GetDefaultProducer(){
		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		KafkaProducer <String, String> producer = new KafkaProducer<String, String>(properties);
		return producer;
		
		
	}
	
	
	public void run(){
		
		
		BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100);
		
		Client client = TwitterClient.createClient( msgQueue );
		
		client.connect();
		KafkaProducer <String, String> producer = GetDefaultProducer();
		
		int i = 0;
		while (!client.isDone()) {
			String msg = null;  
			try {
				msg = msgQueue.poll(10, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
				client.stop();
			}
			  if(msg!=null){
				  logger.info(msg);
				  ProducerRecord <String, String> record = new ProducerRecord<String, String>("twitter_tweets",msg);
				  producer.send(record);
			  }
			  if(i==20)
				  break;
			  i++;
			}

	}
	
	
}
