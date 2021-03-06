/**
 * 
 */
package org.helios.jboss7.jms.mdb;

import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.transaction.Transaction;

import org.apache.log4j.Logger;
import org.helios.jboss7.util.TransactionHelper;

/**
 * @author nwhitehead
 *
 */
public class BaseMDB {
	protected static final AtomicInteger serialFactory = new AtomicInteger();
	protected final int serial = serialFactory.incrementAndGet();
	protected final Logger log = Logger.getLogger(getClass().getName() + "#" + serial);

	public BaseMDB() {
		log.info("Created [" + getClass().getSimpleName() + "] Instance #");
	}
	
	public void onMessage(Message message) {
		if(message instanceof TextMessage) {
			try {
				log.info("Received Message [" + ((TextMessage)message).getText() + "]" + "TX Status:" + getTxStatus());
			} catch (JMSException e) {
				log.error("Failed to process message", e);
				e.printStackTrace();
			}
		} else {
			log.info("Received Message [" + message + "]" + "\n\tTX:" + TransactionHelper.getCurrentTransaction());
		}
	}
	
	
	public String getTxStatus() {
		try {
			Transaction tx = TransactionHelper.getCurrentTransaction();
			return String.format("[%s] - [%s]  Status:%s", tx.getClass().getName(), tx.toString(), tx.getStatus());
		} catch (Exception ex) {
			log.error("Failed to get TX Status", ex);
			return ex.getMessage();
		}
	}

	
}
