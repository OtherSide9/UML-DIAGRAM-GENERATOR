package org.mql.java.examples;

import java.util.Vector;

public class School extends University{
	private int id;
	private String nom;
	private Vector<Etudiant> etudiants;
	
	public School() {

	}
	
	public School(int id, String nom, Vector<Etudiant> etudiants) {
		super();
		this.id = id;
		this.nom = nom;
		this.etudiants = etudiants;
	}



	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public Vector<Etudiant> getEtudiants() {
		return etudiants;
	}
	
	public void setAge(Vector<Etudiant> etudiants) {
		this.etudiants = etudiants;
	}

	@Override
	public String toString() {
		return "School [id=" + id + ", nom=" + nom + ", etudiants=" + etudiants + "]";
	}

}
