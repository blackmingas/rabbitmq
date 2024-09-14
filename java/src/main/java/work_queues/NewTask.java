package work_queues;

import com.rabbitmq.client.*;

public class NewTask {

    private final static String QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.101.129");
        factory.setUsername("dejiangdexiaoming");
        factory.setPassword("962021");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()){
            //用来标识该消息队列是否持久化,rabbitmq不允许我们重新定义一个已经存在的队列，但是我们可以通过使用不同的队列名称来实现
            boolean durable = true;
            channel.queueDeclare(QUEUE_NAME,durable,false,false,null);
            String message = String.join("", argv);

            // 持久化消息:设置MessageProperties.PERSISTENT_TEXT_PLAIN属性，但这并不能完全保证消息不会丢失
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
