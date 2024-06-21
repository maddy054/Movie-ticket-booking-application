//$Id$
package com.zmovizz.persistance;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import com.zmovizz.exceptions.MovieException;
import com.zmovizz.models.Movie;
import com.zmovizz.models.User;
import com.zmovizz.utility.CustomLogger;
import com.zmovizz.utility.QueryBuilder;

public class DbConnector {
	 java.util.logging.Logger logger = java.util.logging.Logger.getLogger(this.getClass().getName());

	public User getUser(int userId) throws MovieException {
		
		try {
			QueryBuilder queryBuilder = new QueryBuilder("user_detail");
			String query = queryBuilder.where(1).buildSelect();
			List<Object> result = queryBuilder.executeQuery(User.class,query,userId);
			User user = (User)result.get(0);
		
			CustomLogger.log(Level.INFO,"name of user is "+user.getName());		
			return user;
			
		} catch (SQLException e) {
			CustomLogger.log(Level.WARNING, "Some thing happened",e);
			throw new MovieException(e.getMessage());
			
		}
		
	}
	
	//to be done
	public void setUser(User user) throws MovieException {
		try {
			QueryBuilder queryBuilder = new QueryBuilder("user_details");
			String query = queryBuilder.column(2,3,4,5,6,7).buildInsert();
			queryBuilder.execute(query, user.getPassword(),user.getName(),user.getRole(),user.getPhoneNumber(),user.getLocation(),user.getCreatedAt());
			
			
		}catch(SQLException e) {
			throw new MovieException(e.getMessage());
		}
	}
	
	//to be done
	public void updateUser(User user) {
		
	}
	
	public Movie getMovie(int id) throws MovieException {
		try {
			QueryBuilder queryBuilder = new QueryBuilder("movie_details");
			String query = queryBuilder.where(1).buildSelect();
			
			List<Object> result = queryBuilder.executeQuery(Movie.class, query, id);
			Movie movie = (Movie)result.get(0);
			return movie;
		}catch(SQLException e) {
			throw new MovieException(e.getMessage());
		}
	}
	
	//to do
	public void setMovie(Movie movie) {
		
	}
	public void updateMovie(Movie movie) {
		
	}
	
	public void getMovieList(String name) throws MovieException {
		try {
			QueryBuilder queryBuilder = new QueryBuilder("movie_details");
			String query = queryBuilder.column(1,2).where(2).buildSelect();
			List<Object> result = queryBuilder.executeQuery(Movie.class, query, name);
			System.out.println("movie"+result);
			CustomLogger.log(Level.INFO, result.toString());
		}catch(SQLException e) {
			throw new MovieException(e.getMessage());
		}
	}
	
	

}
