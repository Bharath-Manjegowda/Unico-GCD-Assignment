package org.bharath.unico.gcdassignment.service;

import org.bharath.unico.gcdassignment.controller.GcdController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jms.TextMessage;

import org.bharath.unico.gcdassignment.dao.GcdDao;
import org.bharath.unico.gcdassignment.daoImpl.GcdDaoImpl;
import org.bharath.unico.gcdassignment.model.InputNumbers;

/**
 * Created by Bharath on 29-03-2017.
 */

public class GcdService {

	GcdController gcdController = null;
	GcdDao gcdDao = null;
			
	public List<Integer> getInputNumbers(){
		gcdDao = new GcdDaoImpl();
		ArrayList<Integer> inputNumbrers = null;
		try {
			inputNumbrers = gcdDao.getInputNumb();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputNumbrers;
	}
	
	public String addNumbers(InputNumbers inputNumbers){
		gcdDao = new GcdDaoImpl();
		try {
			gcdDao.addInputNumb(inputNumbers);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Success";
	}
	
	public static int findGcd(int... numbers) {
		//Find the smallest integer in the number list
		int smallest = numbers[0];
		for (int i = 1; i < numbers.length; i++) {
			if (numbers[i] < smallest) {
				smallest = numbers[i];
			}
		}	
		//Find the GCD
		while (smallest > 1) {
			int counter = 0;
			int modTot = 0;
			while (counter < numbers.length) {
				modTot += numbers[counter] % smallest;
				counter++;
			}
			if (modTot == 0) {
				return smallest;
			}
			smallest--;
		}
		return -1;
	}	
	
	public static int[] convertIntegers(List<Integer> integers)
	{
	    int[] ret = new int[integers.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = integers.get(i).intValue();
	    }
	    return ret;
	}
	
	public int getSum(List<Integer> numbers) {
		List<Integer> respTemp = new ArrayList<Integer>();
		int response = 0;
		gcdController = new GcdController();
		try{
			Iterator<Integer> iterator = numbers.iterator();
			while (iterator.hasNext()) {
				int i1 = numbers.get(0);
				int i2 = numbers.get(1);
				int resp = gcdController.gcd(i1, i2);
				respTemp.add(resp);
				numbers.remove(0);
				numbers.remove(0);
			}
			response = sumOfListEle(respTemp);			        
		} 
		catch(Exception e){
			e.printStackTrace();;
		}
		return response;
    }
	
	public int sumOfListEle(List<Integer> inputList){
		int i;
		int sum = 0;
		for(i = 0; i < inputList.size(); i++)
		    sum += inputList.get(i);
		return sum;
	}
	
	public TextMessage getFromQueue(int i1, int i2){
		GcdDao gcdDao = new GcdDaoImpl();
		TextMessage textMsg = null;
		try {
			textMsg = gcdDao.getFromQueue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return textMsg;
	}
	
	public String addToQueue(int i1, int i2){
		GcdDao gcdDao = new GcdDaoImpl();
		try {
			gcdDao.addToQueue(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Success";
	}
}
