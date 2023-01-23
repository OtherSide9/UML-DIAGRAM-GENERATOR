package org.mql.java.models;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class ClassModel {
	
	private String name;
	private Set<FieldModel> fields;
	private Set<MethodModel> methods;

	public ClassModel(String name) {
		this.name=name;
		Class<?> c = null;
		try {
			c = Class.forName(name);
		}catch(Exception e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		Field [] declaredFields = c.getDeclaredFields();
		Method[] declaredMethods =c.getDeclaredMethods();
		fields = new HashSet<FieldModel>();
		methods = new HashSet<MethodModel>();
		for (Field field : declaredFields) {
			fields.add(new FieldModel(field));
		}
		
		for (Method method : declaredMethods) {
			methods.add(new MethodModel(method));
		}
	}

	@Override
	public String toString() {
		return "ClassParser [name = " + name + ", fields = " + fields + ", methods = " + methods + "]";
	}
	
	

}
