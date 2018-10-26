import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by peaimage on 2018/10/26.
 */
public class JMSConsumer {
    private static  final  String  USERNAME= ActiveMQConnection.DEFAULT_USER;//默认连接用户
    private static final   String  PASSWORD=ActiveMQConnection.DEFAULT_PASSWORD;//默认连接密码
    private static final   String  BROKERURL=ActiveMQConnection.DEFAULT_BROKER_URL;//默认连接路径
    private static final   int   SENDNUM=10;
    public static void main(String[] args){
        ConnectionFactory connectionFactory;//连接工厂
        Connection connection=null;//连接
        Session session;//发送或者接收消息的线程
        Destination destination;//消息目的地
        MessageConsumer messageConsumer;//消息消费者
        try {
            connectionFactory=new ActiveMQConnectionFactory(JMSConsumer.USERNAME,JMSConsumer.PASSWORD,JMSConsumer.BROKERURL);//实例化工厂
            connection=connectionFactory.createConnection();//创建连接
            connection.start();//启动连接
            session=connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);//创建session
            destination=session.createQueue("FirstQueue1");//创建连接队列
            messageConsumer=session.createConsumer(destination);//创建消息消费者
               while (true){
               TextMessage textMessage= (TextMessage)messageConsumer.receive(1000000);
               if (textMessage!=null){
                   System.out.println(textMessage.getText());
               }else {
                   break;
               }
               }


        }catch (Exception e){
              e.printStackTrace();
        }



    }
}
