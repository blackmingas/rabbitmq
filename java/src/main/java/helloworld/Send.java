package helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.101.129");
        factory.setUsername("dejiangdexiaoming");
        factory.setPassword("962021");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()){
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
