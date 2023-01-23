package org.mql.java.examples;

public class Etudiant extends School{
	
	private int code;
	private String nom;
	private int age;
	private String pays;
	private Etudiant1 e1; 
	
	public Etudiant() {

	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getPays() {
		return pays;
	}
	
	public void setPays(String pays) {
		this.pays = pays;
	}
	
	@Override
	public String toString() {
		return "Etudiant [code=" + code + ", nom=" + nom + ", age=" + age + ", pays=" + pays + "]";
	}
	
	
}
