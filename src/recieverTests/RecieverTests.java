package recieverTests;
import javaRabbitReciever.Recv;

public class RecieverTests {
	
	public static void main(String[] args)
	{
		Recv myReciever = new Recv();
		myReciever.readMessages();
		while (true)
		{
			if (myReciever.messagesInQueue())
			{
				System.out.println("Top message in queue is: " );
				System.out.println("--------------------------------------");
				System.out.println(myReciever.popMessage().getMessageText());
				System.out.println("--------------------------------------");
			}
			
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				System.out.println("Problem with sleep");
			}
			
		}
		
		
	}
}
