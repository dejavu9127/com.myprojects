package apachespark.streaming.server;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import apachespark.streaming.client.SimpleStreamingApp;

public class DataStreamingTest {

	@Before
	public void setUp() throws Exception {
		StreamingServer.main(null);
		System.out.println("Server sending Data on localhost:9999");
		
	}

	@Test
	public void test() {
		SimpleStreamingApp.main(null);
	}

}
