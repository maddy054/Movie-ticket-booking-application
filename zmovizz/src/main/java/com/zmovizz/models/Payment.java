//$Id$
package com.zmovizz.models;

public class Payment {
	private int id;
	private int mode;
	private int ticketAmount;
	private int payaleAmount;
	private int status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public int getTicketAmount() {
		return ticketAmount;
	}
	public void setTicketAmount(int ticketAmount) {
		this.ticketAmount = ticketAmount;
	}
	public int getPayaleAmount() {
		return payaleAmount;
	}
	public void setPayaleAmount(int payaleAmount) {
		this.payaleAmount = payaleAmount;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
