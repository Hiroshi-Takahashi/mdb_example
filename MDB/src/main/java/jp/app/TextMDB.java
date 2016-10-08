package jp.app;

import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(mappedName = "MAPPED_TEST_QUEUE", activationConfig =  {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "TEST_QUEUE"),
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class TextMDB implements MessageListener {

    private final static Logger LOGGER = Logger.getLogger(TextMDB.class.toString());

    public TextMDB() {
    }

    public void onMessage(Message message) {

    	try {
    		LOGGER.info("onMessage!!! ["+((TextMessage) message).getText()+"]");
    	} catch ( Exception e ) {
    		e.printStackTrace();
    	}
    }
}
