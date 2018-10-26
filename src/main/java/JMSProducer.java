import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;


import javax.jms.*;


/**
 * Created by peaimage on 2018/10/26.
 */
public class JMSProducer {
    private static  final  String  USERNAME= ActiveMQConnection.DEFAULT_USER;//默认连接用户
    private static final   String  PASSWORD=ActiveMQConnection.DEFAULT_PASSWORD;//默认连接密码
    private static final   String  BROKERURL=ActiveMQConnection.DEFAULT_BROKER_URL;//默认连接路径
    private static final   int   SENDNUM=10;
    public static void main(String[] args) {

        ConnectionFactory  connectionFactory;//连接工厂
        Connection connection=null;//连接
        Session session;//发送或者接收消息的线程
        Destination destination;//消息目的地
        MessageProducer messageProducer;//消息生产者
        //实例化连接工厂
        connectionFactory=new ActiveMQConnectionFactory(JMSProducer.USERNAME,JMSProducer.PASSWORD,JMSProducer.BROKERURL);
        try {
            connection=connectionFactory.createConnection();//通过连接工厂获取连接
            connection.start();//启动连接
            session=connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);//创建Session
            destination=session.createQueue("FirstQueue1");//创建消息队列
            messageProducer=session.createProducer(destination);//创建消息生产者
            sendMessage(session,messageProducer);
            session.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (connection!=null){
               try{
                    connection.close();
               }catch (Exception e){
                   e.printStackTrace();
               }
            }
        }
    }
    public static void sendMessage(Session session,MessageProducer messageProducer){
        for (int i=0;i<JMSProducer.SENDNUM;i++){
            try {
                TextMessage message=  session.createTextMessage("ActiveMQ发送的消息"+i);
                messageProducer.send(message);
                System.out.println("发送消息"+i);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
