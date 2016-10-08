package app;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * メッセージ送信を行う
 */
public class MessageSender {

    private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static final String DEFAULT_DESTINATION = "TEST_QUEUE";
    private static final String PROVIDER_URL = "http-remoting://localhost:9090";
    
    /**
     * コンテキスト作成
     * @return コンテキスト
     */
    private Context createContext() throws NamingException {
        final Hashtable<String,String> env = new Hashtable<String,String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
        env.put(Context.PROVIDER_URL, PROVIDER_URL);
        return new InitialContext(env);
    }
    
    /**
     * コネクション作成
     * @param コンテキスト
     * @return コネクション
     */
    private QueueConnection createConnection( Context context ) throws NamingException, JMSException {
        
        QueueConnectionFactory connectionFactory = (QueueConnectionFactory)context.lookup(DEFAULT_CONNECTION_FACTORY);
        return connectionFactory.createQueueConnection();
    }
    
    /**
     * キュー作成
     */
    private Queue createQueue( Context context ) throws NamingException {
        return (Queue)context.lookup(DEFAULT_DESTINATION);
    }
    
    /**
     * メッセージ送信
     * 
     * @param message メッセージ
     */
    public void send( final String message ) {

        QueueConnection connection = null;
        try {
            // メッセージ送信先との通信準備
            Context context = createContext();
            Queue queue = createQueue(context);
            connection = createConnection(context);
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            connection.start();
            
            // メッセージ送信
            QueueSender sender = session.createSender(queue);
            sender.send(session.createTextMessage(message));

        } catch ( NamingException e ) {
            e.printStackTrace();
        } catch ( JMSException e ) {
            e.printStackTrace();
        } finally {
            if (connection!=null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
