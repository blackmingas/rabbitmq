package topics;

import com.rabbitmq.client.*;

public class ReceiveLogsTopics {

    private final static String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.101.129");
        factory.setUsername("dejiangdexiaoming");
        factory.setPassword("962021");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        // 创建一个non-durable, exclusive, autodelete queue with a generated name
        String queueName = channel.queueDeclare().getQueue();

        if (argv.length < 1) {
            System.err.println("Usage: ReceiveLogsTopic [binding_key]...");
            System.exit(1);
        }
        for (String bindingKey  : argv) {
            channel.queueBind(queueName, EXCHANGE_NAME, bindingKey );
        }
        channel.queueBind(queueName, EXCHANGE_NAME, LogLevelEnum.WARNING.getName());

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
