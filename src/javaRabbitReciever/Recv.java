package javaRabbitReciever;

import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.*;

public class Recv {

	private final static String QUEUE_NAME = "EventLog";
	private Stack<RabbitMessageDataStructure> myStack;
	Channel channel;
	

	public Recv() {

		myStack = new Stack<RabbitMessageDataStructure>();
		try {

			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			channel = connection.createChannel();

			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
			readMessages();
		} catch (Exception e) {
			System.out.println("Problem initializing the java rabbit client.");
		}
	}

	
	public void readMessages() {

		Boolean autoAck = false;
		
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				myStack.push(new RabbitMessageDataStructure(message, envelope));
			}
		};
		
		try {
			channel.basicConsume(QUEUE_NAME, autoAck, consumer); 
		} catch (IOException e) {
			System.out.println("Problem running the java rabbit client");
		}
	}

	public boolean messagesInQueue() {
		return !myStack.empty();
	}
	
	public void sendAck(Envelope e)
	{
        long deliveryTag = e.getDeliveryTag();
        // positively acknowledge a single delivery, the message will
        // be discarded
        try {
			channel.basicAck(deliveryTag, false);
		} catch (IOException ackFailure) {
			ackFailure.printStackTrace();
		}
	}


	// todo: make generic
	public RabbitMessageDataStructure popMessage() {
		if (!myStack.empty())
			return myStack.pop();
		return new RabbitMessageDataStructure(true, Thread.currentThread().getStackTrace().toString());
	}
	

}
