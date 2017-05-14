package apachespark.streaming.client;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.*;



public class SimpleStreamingApp {
	private static final String HOST="localhost";
	private static final int port=9999;
	
	public static void main(String[] args){
		
	//Configure and inti Spark Streaming contetx
		SparkConf conf=new SparkConf()
				.setMaster("local[*]")
				.setAppName("SimpleStreamingApp");
		JavaStreamingContext streamingContext=new JavaStreamingContext(conf,Durations.seconds(5));
		
		Logger.getRootLogger().setLevel(Level.ERROR);
		
		//Receive streaming data from source
		JavaReceiverInputDStream<String> lines=streamingContext.socketTextStream(HOST, port);
		lines.print();
		System.out.println(lines);
		
		
		//Exceute the Spark workflow 
		streamingContext.start();
		try {
			streamingContext.awaitTermination();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Server stopped sending Data");
		}
		
		
	}

}
