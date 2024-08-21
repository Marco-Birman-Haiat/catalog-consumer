package com.marcohaiat.catalog_consumer.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.QueueDoesNotExistException;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerService {
    private final AmazonSQS sqsClient;

    @Value("${aws.sqs.queue.url}")
    private String queueUrl2;

    @Value("${aws.sns.topic}")
    private String messageQueueTopic;

    public ConsumerService(AmazonSQS sqsClient) {
        this.sqsClient = sqsClient;
        //this.queueUrl2 = sqsClient.getQueueUrl(messageQueueTopic).getQueueUrl();
    }

    //@PostConstruct
    public void startListening() {
        System.out.println("Listner Iniciado");
        while(true) {
            try {
                System.out.println("Começando a ouvir a fila");

                List<Message> messages = sqsClient.receiveMessage(queueUrl2).getMessages();

                for (Message message : messages) {
                    processMessage(message);
                    deleteMessage(message);
                }
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
    }

    public void testListen() {
        System.out.println("Listner Iniciado");
        try {
            System.out.println("Começando a ouvir a fila");

            List<Message> messages = sqsClient.receiveMessage(queueUrl2).getMessages();

            for (Message message : messages) {
                processMessage(message);
                //deleteMessage(message);
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void receiveMessages() {
        try {
            //String queueUrl = sqsClient.getQueueUrl(messageQueueTopic).getQueueUrl();
            System.out.println("Reading SQS Queue done: URL {}  ---> " + queueUrl2);
            ReceiveMessageResult receiveMessageResult = sqsClient.receiveMessage(queueUrl2);
            if (!receiveMessageResult.getMessages().isEmpty()) {
                Message message = receiveMessageResult.getMessages().get(0);
                System.out.println("Incoming Message From SQS {}  --> " + message.getMessageId());
                System.out.println("Message Body {} -->  " + message.getBody());

                processMessage(message);
                //amazonSQSClient.deleteMessage(queueUrl, message.getReceiptHandle());
            }
        } catch (QueueDoesNotExistException e) {
            System.err.println("Queue does not exist {} --> " + e.getMessage());
        }
    }

    public void receiveMessages2() {
        try {
            //String queueUrl = sqsClient.getQueueUrl(messageQueueTopic).getQueueUrl();
            System.out.println("Reading SQS Queue done: URL {}  ---> " + queueUrl2);
            ReceiveMessageResult receiveMessageResult = sqsClient.receiveMessage(queueUrl2);

            for (Message message : receiveMessageResult.getMessages()) {
                if (!receiveMessageResult.getMessages().isEmpty()) {
                   // Message message = receiveMessageResult.getMessages().get(0);
                    //System.out.println("Incoming Message From SQS {}  --> " + message.getMessageId());
                    //System.out.println("Message Body {} -->  " + message.getBody());

                    processMessage(message);
                    //amazonSQSClient.deleteMessage(queueUrl, message.getReceiptHandle());
                }
            }
        } catch (QueueDoesNotExistException e) {
            System.err.println("Queue does not exist {} --> " + e.getMessage());
        }
    }

    private void deleteMessage(Message message) {
        sqsClient.deleteMessage(queueUrl2, message.getReceiptHandle());
    }

    private void processMessage(Message message) {
        System.out.println("Mensagem sendo processada  -> " + message.getMessageId());
        System.out.println("Mensagem recebida -> " + message.getBody());
    }
}
