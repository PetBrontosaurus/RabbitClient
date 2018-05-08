package javaRabbitReciever;

import com.rabbitmq.client.Envelope;

public class RabbitMessageDataStructure {
	private Boolean error = false;
	private String messageText = "";
	private Envelope envelope;
	private String errorText = "";
	
	public String getErrorText() {
		return errorText;
	}

	public RabbitMessageDataStructure(String message, Envelope inEnvelope) {
		messageText = message;
		envelope = inEnvelope;
		error = false;
	}
	
	public RabbitMessageDataStructure(Boolean errorCase, String errorMessage)
	{
		error = true;
		errorText = errorMessage;
	}
	
	public String getMessageText() {
		return messageText;
	}
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	public Envelope getEnvelope() {
		return envelope;
	}
	public void setEnvelope(Envelope envelope) {
		this.envelope = envelope;
	}
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	
	
}
