package org.mql.java.examples;

public class Master extends School{
	private String intitule;
	private int id;
	
	public Master() {
	}
	public Master(String intitule, int id) {
		super();
		this.intitule = intitule;
		this.id = id;
	}
	public String getIntitule() {
		return intitule;
	}
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}

