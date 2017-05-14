package apachespark.streaming.server;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import apachespark.streaming.client.SimpleStreamingApp;

public class DataStreamingTest {

	@Before
	public void setUp() throws Exception {
		//Start streaming server in endless while loop
		StreamingServer.main(null);
		System.out.println("Server sending Data on localhost:9999");
		
	}

	@Test
	public void test() {
		//Start listening to server data over localhost:9999
		SimpleStreamingApp.main(null);
	}

}
