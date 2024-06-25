//$Id$
package com.zmovizz.persistance;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import com.zmovizz.exceptions.MovieException;
import com.zmovizz.models.Location;
import com.zmovizz.models.Movie;
import com.zmovizz.models.Payment;
import com.zmovizz.models.Review;
import com.zmovizz.models.Show;
import com.zmovizz.models.Theater;
import com.zmovizz.models.Ticket;
import com.zmovizz.models.User;
import com.zmovizz.utility.CustomLogger;
import com.zmovizz.utility.QueryBuilder;
import com.zmovizz.models.Constants.Tables;

public class DbConnector {
	 java.util.logging.Logger logger = java.util.logging.Logger.getLogger(this.getClass().getName());

	public User getUser(int userId) throws MovieException {
		
		try {
			QueryBuilder queryBuilder = new QueryBuilder(Tables.USER_DETAILS.get());
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
	
	public void setUser(User user) throws MovieException {
		try {
			QueryBuilder queryBuilder = new QueryBuilder(Tables.USER_DETAILS.get());
			String query = queryBuilder.buildInsert();
			queryBuilder.execute(query, user);
			
			
		}catch(SQLException e) {
			throw new MovieException(e.getMessage());
		}
	}
	
		
	public void updateUser(User user) throws MovieException {
		try {
			QueryBuilder queryBuilder = new QueryBuilder(Tables.USER_DETAILS.get());
			String query = queryBuilder.where(1).buildUpdate();
			queryBuilder.execute(query, user);
			
			
		}catch(SQLException e) {
			throw new MovieException(e.getMessage());
		}
	}
	
	public Movie getMovie(int id) throws MovieException {
		try {
			QueryBuilder queryBuilder = new QueryBuilder(Tables.MOVIE_DETAILS.get());
			String query = queryBuilder.where(1).buildSelect();
			
			List<Object> result = queryBuilder.executeQuery(Movie.class, query, id);
			Movie movie = (Movie)result.get(0);
			return movie;
		}catch(SQLException e) {
			throw new MovieException(e.getMessage());
		}
	}
	

	public void setMovie(Movie movie) {
QueryBuilder queryBuilder  = new QueryBuilder("show_details");
		
		try {
			String query = queryBuilder.buildInsert();
			queryBuilder.execute(query, movie);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	public void updateMovie(Movie movie) throws MovieException {
		
		try {
			QueryBuilder queryBuilder = new QueryBuilder(Tables.MOVIE_DETAILS.get());
			String query = queryBuilder.buildInsert();
			queryBuilder.execute(query, movie);
			
			
		}catch(SQLException e) {
			throw new MovieException(e.getMessage());
		}
		
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
	public void getReview(Review review) {
		QueryBuilder queryBuilder  = new QueryBuilder("show_details");
		
		try {
			String query = queryBuilder.buildInsert();
			queryBuilder.execute(query, review);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	public void getLocation(int id) throws MovieException {
		
		QueryBuilder queryBuilder  = new QueryBuilder("locations");
		
		try {
			String query = queryBuilder.where(1).buildSelect();
			List<Object> result = queryBuilder.executeQuery(Location.class, query, id);
			String location = ((Location)result.get(0)).getName();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	public void getLocation(String name) throws MovieException {
		QueryBuilder queryBuilder  = new QueryBuilder("locations");
		
		try {
			String query = queryBuilder.where(1).buildSelect();
			List<Object> result = queryBuilder.executeQuery(Location.class, query, name);
			String location = ((Location)result.get(0)).getName();
			System.out.println("location "+location);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
	}
	public void setLocation(Location location) {
		QueryBuilder queryBuilder  = new QueryBuilder("locations");
		
		try {
			String query = queryBuilder.buildInsert();
			queryBuilder.execute(query, location);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void setShow(Show show) {
		QueryBuilder queryBuilder  = new QueryBuilder("show_details");
		
		try {
			String query = queryBuilder.buildInsert();
			queryBuilder.execute(query, show);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	public void CancelShow(Show show) {
		QueryBuilder queryBuilder  = new QueryBuilder("show_details");
		
		try {
			
			String query = queryBuilder.column(6).where(1).buildUpdate();
			queryBuilder.execute(query,show);
			cancelAllTickets(show);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//to do
	public void getShow(Show show) {
		QueryBuilder queryBuilder  = new QueryBuilder(Tables.SHOW_DETAILS.get());
		
		try {
			String query = queryBuilder.column(6).where(1).buildUpdate();
			queryBuilder.execute(query,show);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void cancelAllTickets(Show show) { 
		QueryBuilder queryBuilder  = new QueryBuilder(Tables.SHOW_DETAILS.get());
		
		try {
			String query = queryBuilder.column(5).where(1).buildUpdate();
			queryBuilder.execute(query,show);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void getTickets(User user) {
		QueryBuilder queryBuilder  = new QueryBuilder(Tables.TICKET_DETAILS.get());
		
		try {
			String query = queryBuilder.where(1).buildSelect();
			queryBuilder.execute(query,user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void getTickets(Show show) {
		QueryBuilder queryBuilder  = new QueryBuilder(Tables.TICKET_DETAILS.get());
		
		try {
			String query = queryBuilder.where(1).buildSelect();
			queryBuilder.execute(query,show);
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
	}
	
	public void setTicket(Ticket ticket) {
		QueryBuilder queryBuilder  = new QueryBuilder(Tables.TICKET_DETAILS.get());
		
		try {
			String query = queryBuilder.buildInsert();
			queryBuilder.execute(query,ticket);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void cancelTicket(Ticket ticket) {
		QueryBuilder queryBuilder  = new QueryBuilder(Tables.SHOW_DETAILS.get());
		
		try {
			String query = queryBuilder.column(5).where(1).buildUpdate();
			queryBuilder.execute(query,ticket);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void getPayment(Payment payment) {
		QueryBuilder queryBuilder  = new QueryBuilder(Tables.PAYMENT_DETAILS.get());
		
		try {
			String query = queryBuilder.where(1).buildSelect();
			queryBuilder.execute(query,payment);
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
	}
	
	public void setPayment(Payment payment) {
		QueryBuilder queryBuilder  = new QueryBuilder(Tables.PAYMENT_DETAILS.get());
		
		try {
			String query = queryBuilder.buildInsert();
			queryBuilder.execute(query,payment);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void getTheater(int theaterId) {
		QueryBuilder queryBuilder  = new QueryBuilder(Tables.THEATER_DETAILS.get());
		
		try {
			String query = queryBuilder.where(1).buildSelect();
			queryBuilder.executeQuery(Theater.class, query,theaterId);
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
	}
	
	public void setTheater(Theater theater) {
		QueryBuilder queryBuilder  = new QueryBuilder(Tables.THEATER_DETAILS.get());
		
		try {
			String query = queryBuilder.buildInsert();
			queryBuilder.execute(query,theater);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	public void updateTheater(Theater theater) throws MovieException {
		try {
			QueryBuilder queryBuilder = new QueryBuilder(Tables.MOVIE_DETAILS.get());
			String query = queryBuilder.buildInsert();
			queryBuilder.execute(query, theater);
			
			
		}catch(SQLException e) {
			throw new MovieException(e.getMessage());
		}
		
	}
	
	public void getAllShows(int movie) throws MovieException {
		try {
			QueryBuilder queryBuilder = new QueryBuilder(Tables.SHOW_DETAILS.get(),Tables.THEATER_DETAILS.get());
			String query = queryBuilder.join(4,1).where(5). buildSelect();
			queryBuilder.executeQuery(Show.class, query, movie);
			
		}catch(SQLException e) {
			
		}
	
	}
	public void getAllMovies(int location) {
		try {
			QueryBuilder queryBuilder = new QueryBuilder(Tables.MOVIE_DETAILS.get(),Tables.SHOW_DETAILS.get(),Tables.THEATER_DETAILS.get());
			String query = queryBuilder.join(1,5,4,1).where().buildSelect();
			//queryBuilder.executeQuery(Show.class, query, location);
			
		}catch(SQLException e) {
			
		}
	}

	
}
