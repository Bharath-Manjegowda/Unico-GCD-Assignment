package org.bharath.unico.gcdassignment.dao;

import java.util.ArrayList;

import javax.jms.TextMessage;

import org.bharath.unico.gcdassignment.model.InputNumbers;

/**
 * Created by Bharath on 29-03-2017.
 */

public interface GcdDao {

	public ArrayList<Integer> getInputNumb()  throws Exception;
	
	public ArrayList<Integer> addInputNumb(InputNumbers inputNumbers) throws Exception;
	
	public void addToQueue(InputNumbers inputNumbers)  throws Exception;
	
	public TextMessage getFromQueue()  throws Exception;
	
}
