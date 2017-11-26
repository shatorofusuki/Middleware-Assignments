package orderProcessor;


import java.util.Hashtable;

import javax.jms.*;
import javax.naming.*;

import config.Config;

public class JMSConsumer implements MessageListener {
 
    // connection factory
    private QueueConnectionFactory qconFactory;
 
    // connection to a queue
    private QueueConnection qcon;
 
    // session within a connection
    private QueueSession qsession;
 
    // queue receiver that receives a message to the queue
    private QueueReceiver qreceiver;
 
    // queue where the message will be sent to
    private Queue queue;
    
    private JMSProducer producer;
 
    // callback when the message exist in the queue
    public void onMessage(Message msg) {
        try {
            String msgText;
            if (msg instanceof TextMessage) {
                msgText = ((TextMessage) msg).getText();
            } else {
                msgText = msg.toString();
            }
            // send it further
            String msgback = "Received: " + msgText;
            
            String nextQueueName = (msgText.equals("Booking")) ? "jms/mdw-queue-bookings" : "jms/mdw-queue-trips" ;
            
            System.out.println(msgback);
            // create the producer object and send the message
            
            producer.send(nextQueueName, msgText);
        } catch (JMSException jmse) {
            System.err.println("An exception occurred: " + jmse.getMessage());
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 
    // create a connection to the WLS using a JNDI context
    public void init(Context ctx, String queueName)
            throws NamingException, JMSException {
 
        qconFactory = (QueueConnectionFactory) ctx.lookup(Config.JMS_FACTORY);
        qcon = qconFactory.createQueueConnection();
        qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        queue = (Queue) ctx.lookup(queueName);
 
        qreceiver = qsession.createReceiver(queue);
        qreceiver.setMessageListener(this);
 
        qcon.start();
    }
 
    // close sender, connection and the session
    public void close() throws JMSException {
        qreceiver.close();
        qsession.close();
        qcon.close();
    }
 
    // start receiving messages from the queue
    public void receive(String queueName) throws Exception {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, Config.JNDI_FACTORY);
        env.put(Context.PROVIDER_URL, Config.PROVIDER_URL);
 
        InitialContext ic = new InitialContext(env);
 
        init(ic, queueName);
 
        System.out.println("Connected to " + queue.toString() + ", receiving messages...");
        try {
            synchronized (this) {
                while (true) {
                    this.wait();
                }
            }
        } finally {
            close();
            System.out.println("Finished.");
        }
    }
 
    public static void main(String[] args) throws Exception {
        // input arguments
        String receiveQueueName = "jms/mdw-queue-all" ;
 
        // create the producer object and receive the message
        JMSConsumer consumer = new JMSConsumer();
        consumer.producer = new JMSProducer();
        
        consumer.receive(receiveQueueName);
    }
}