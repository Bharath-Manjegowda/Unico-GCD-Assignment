package org.bharath.unico.gcdassignment.controller;

import org.bharath.unico.gcdassignment.model.InputNumbers;
import org.bharath.unico.gcdassignment.service.GcdService;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * Created by Bharath on 29-03-2017.
 */

@Path("/gcd")
public class GcdController {

	GcdService gcdService = null;

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
    public String getNumbersCntr() {
		gcdService = new GcdService();
		Gson gson = new Gson();
        String response = gson.toJson(gcdService.getInputNumbers());
		return response;		
    }
	
	@GET
	@Path("/gcd")
    @Produces(MediaType.TEXT_PLAIN)
    public int gcd(@QueryParam("i1") int i1, @QueryParam("i2")int i2) {
		if(i1 == 0 || i2 == 0) return i1+i2;
		  return gcd(i2,i1%i2);
    }
	
	@POST
	@Path("/push")
    @Produces(MediaType.TEXT_PLAIN)
    public String insertNumbersCntr(@QueryParam("i1") int i1, @QueryParam("i2")int i2) {
		gcdService = new GcdService();
		InputNumbers inputNumbers = new InputNumbers();
		inputNumbers.setI1(i1);
		inputNumbers.setI2(i2);
		try{
			gcdService.addNumbers(inputNumbers);			        
		} 
		catch(Exception e){
			return "Error while processing the request.";
		}
		return "Numbers added to queue successfully";
    }	
	
	@GET
	@Path("/getSum")
    @Produces(MediaType.TEXT_PLAIN)
    public int getSum() {
		int response = 0;
		gcdService = new GcdService();		
		try{
			List<Integer> numbers = gcdService.getInputNumbers();
			response = gcdService.getSum(numbers);
		} 
		catch(Exception e){
			e.printStackTrace();;
		}
		return response;
    }	
	
}
