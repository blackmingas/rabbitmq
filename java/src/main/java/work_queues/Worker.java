package work_queues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Worker {
    private final static String QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.101.129");
        factory.setUsername("dejiangdexiaoming");
        factory.setPassword("962021");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //用来标识该队列是否持久化,rabbitmq不允许我们重新定义一个已经存在的队列，但是我们可以通过使用不同的队列名称来实现
        boolean durable = true;
        channel.queueDeclare("task_queue",true,false,false,null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // 设置每次返回的消息数,将其设置为1是因为缓解workers的压力
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            // fake a second of work for every dot in the message body
            try {
                doWork(message);
            }finally {
                System.out.println(" [x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        boolean autoAck = false; // acknowledgment is covered below
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> {});
    }

    private static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
