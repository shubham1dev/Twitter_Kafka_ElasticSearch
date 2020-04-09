package com.dev.kafka.kafkastream;

import java.io.IOException;

import com.dev.kafka.consumer.Consumer;
import com.dev.kafka.producer.Producer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        System.out.println( "Hello World!" );
        Producer p1 = new Producer();
        p1.run();
        
    }
}
