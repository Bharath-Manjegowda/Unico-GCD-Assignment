package org.bharath.unico.gcdassignment.daoImpl;

import java.sql.Connection;
import org.apache.activemq.ActiveMQConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.bharath.unico.gcdassignment.util.DatabaseUtil;

import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.bharath.unico.gcdassignment.dao.GcdDao;
import org.bharath.unico.gcdassignment.model.InputNumbers;

/**
 * Created by Bharath on 29-03-2017.
 */

public class GcdDaoImpl implements GcdDao{

	@Override
	public ArrayList<Integer> getInputNumb() throws Exception {
		ArrayList<Integer> inputsList = null;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			inputsList = new ArrayList<Integer>();
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM gcd_input_number");
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				inputsList.add(rs.getInt("in_number"));
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		return inputsList; 	
	}
	
	@Override
	public ArrayList<Integer> addInputNumb(InputNumbers inputNumbers) throws Exception {
		ArrayList<Integer> inputsList = null;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			inputsList = new ArrayList<Integer>();
			Statement statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO gcd_input_number " + "VALUES (" +inputNumbers.getI1() +")");
			statement.executeUpdate("INSERT INTO gcd_input_number " + "VALUES (" +inputNumbers.getI2() +")");
			connection.close();
		} catch (SQLException e){
			e.printStackTrace();
		}
		return inputsList; 	
	}
	
	@Override
	public void addToQueue(InputNumbers inputNumbers) throws Exception {
		ActiveMQConnection connection = null;
		try{
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:1521");
			connection = (ActiveMQConnection) connectionFactory.createConnection();
			Session session = ((javax.jms.Connection) connection).createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("gcdQueue");
			
			Message msg = session.createTextMessage(inputNumbers.toString());
			MessageProducer producer = session.createProducer(queue);
			producer.send(msg);
		}
		finally {
            if (connection != null) {
                connection.close();
            }
		}
	}

	@Override
	public TextMessage getFromQueue() throws Exception {
		ActiveMQConnection connection = null;
		Session session = null;
		TextMessage textMsg = null;
		try{
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:1521");
			connection = (ActiveMQConnection) connectionFactory.createConnection();
			session = ((javax.jms.Connection) connection).createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("gcdQueue");	
			
			MessageConsumer consumer = session.createConsumer(queue);
			((javax.jms.Connection) connection).start();		
			textMsg = (TextMessage) consumer.receive();
			System.out.println(textMsg);
			System.out.println("Received: " + textMsg.getText());
					
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
	        if (connection != null) {
	        	session.close();
	            connection.close();
	        }
		}
		return textMsg;	
	}

}
