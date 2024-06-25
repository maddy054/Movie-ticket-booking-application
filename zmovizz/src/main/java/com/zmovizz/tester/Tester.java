//$Id$
package com.zmovizz.tester;

import com.zmovizz.exceptions.MovieException;
import com.zmovizz.persistance.DbConnector;

public class Tester {
	public static void main(String[] args)  {
		DbConnector connector = new DbConnector();
		try {
		 System.out.println(connector.getUser(2).getName());	
		 connector.getMovie(1);
		 connector.getMovieList("Garudan");
		 
		connector.getLocation(1);
		 
		} catch (MovieException e) {	
			System.err.println(e.getMessage());
		}
	}

}
