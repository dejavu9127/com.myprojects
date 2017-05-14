package apachespark.streaming.server;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.Random;


public class StreamingServer {
	
	private static final Executor SERVER_EXECUTOR=Executors.newSingleThreadExecutor();
	private static final int port=9999;
	private static final String DELIMITER=":";
	private static final long EVENT_PERIOD_SECONDS=1;
	private static final Random random=new Random();
	
	
	public static void main(String[] args){
		BlockingQueue<String> eventQueue=new ArrayBlockingQueue<String>(100);
		SERVER_EXECUTOR.execute(new EventGenerator(eventQueue));
		int numEvents=10;
		while(true){
			try {
				eventQueue.put(generateEvent());
				//numEvents--;
				Thread.sleep(TimeUnit.SECONDS.toMillis(EVENT_PERIOD_SECONDS));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	
	/**
	 * Method to generate random user event
	 * @return
	 */
	private static String generateEvent(){
		int userNumber=random.nextInt(10);
		String event=random.nextBoolean()?"login":"purchase";
		
		return String.format("user-%s", userNumber)+DELIMITER+event;
		
	}
	
	private static class EventGenerator implements Runnable{
		private final BlockingQueue<String> eventQueue;
		
		public EventGenerator(BlockingQueue<String> eventQueue){
			this.eventQueue=eventQueue;
		}

		public void run() {
			try{
				ServerSocket serverSocket=new ServerSocket(port);
				Socket clientSocket=serverSocket.accept();
				PrintWriter out=new PrintWriter(clientSocket.getOutputStream(),true);
				while(true){
					String event=eventQueue.take();
					//System.out.println(String.format("Writing \"%s\" to the socket ",event));
					out.println(event);
				}
				
			}catch(IOException e){
				throw new RuntimeException("Server error",e);
				
			} catch (InterruptedException e) {
				throw new RuntimeException("Server error",e);
			}
			
		}
		
	}

}
