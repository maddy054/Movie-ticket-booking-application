//$Id$
package com.zmovizz.models;

public class Show extends Movie {
	private int id;
	private long time;
	private int ticketPrice;
	private int theater;
	private int movie;
	private int status;
	private int offer;
	public int getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public int getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(Integer ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public int getTheaterId() {
		return theater;
	}
	public void setTheater(Integer theater) {
		this.theater = theater;
	}
	public int getMovie() {
		return movie;
	}
	public void setMovie(Integer movie) {
		this.movie = movie;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public int getOffer() {
		return offer;
	}
	public void setOffer(Integer offer) {
		this.offer = offer;
	}
	

}
