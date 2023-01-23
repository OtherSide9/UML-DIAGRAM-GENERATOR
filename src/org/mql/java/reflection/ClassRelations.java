package org.mql.java.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ClassRelations {
	
	private Class<?> targetClass;
	private String projectDirectory;
	private PackageExplorer packageExplorer;
	private Set<Field> classField;
	private Set<Field> ListClassField;
	private Set<Field> association;
	private Set<Field> agregation;
	private Set<Field> composition;
	private Set<Parameter> utilisation;

	public ClassRelations(String projectDirectory ,Class<?> targetClass ) {
	this.projectDirectory = projectDirectory;
	packageExplorer = new PackageExplorer(projectDirectory);
	this.targetClass = targetClass;
	ListClassField = new HashSet<>();
	classField = new HashSet<>();
	utilisation = new HashSet<>();
	association = new HashSet<>();
	agregation = new HashSet<>();
	composition = new HashSet<>();
	classField = getClassFields();
	ListClassField = getListClassFields();
	utilisation = getUtilisationRelations();
	association = getAssociationRelations();
	agregation = getAgregationRelations();
	composition = getCompositionRelations();

	}
	
	public Class<?> getTargetClass() {
		return targetClass;
	}
	
public boolean classMemePack(String parameterType) {
	Set<String> classList = new HashSet<>();
	String packegeName = targetClass.getPackage().getName();
	packageExplorer.getClassList(packegeName, classList);
	if(classList.contains(parameterType)) {
		return true;
	}
	else return false;
}
public Set<Field> getClassFields() {
	Field[] fields = targetClass.getDeclaredFields();
	for (Field field : fields) {
		if (!field.getType().isPrimitive() && classMemePack(field.getType().getName())) {
				classField.add(field);
		}
	}
	return classField;
}

public boolean isConstuctorParameter(Field field) {
	Constructor<?>[] constructors = targetClass.getConstructors();
	for (Constructor<?> constructor : constructors) {
		Parameter[] parameters = constructor.getParameters();
		for (Parameter parameter : parameters) {
			if (parameter.getParameterizedType() instanceof ParameterizedType && Collection.class.isAssignableFrom(field.getType()) ) {
				Class<?> elementType = getElementType(field);
				Class<?> constructorEelementType = getElementType(parameter);
				if ( elementType == constructorEelementType) return true;
			} else if(field.getType().isArray() && parameter.getType().isArray()) {
				Class<?> componentType = field.getType().getComponentType();
				Class<?> constructorElementType = parameter.getType().getComponentType();
				if(componentType== constructorElementType) return true;
			}else {
				if(parameter.getType() == field.getType()) return true ;
			}	
		}
	}
	return false;
}

public Set<Field> getListClassFields() {
	Field[] fields = targetClass.getDeclaredFields();
	for (Field field : fields) {
		if (Collection.class.isAssignableFrom(field.getType())) {
			Class<?> elementType = getElementType(field);
			if (!elementType.isPrimitive() && classMemePack(elementType.getName())) {
					ListClassField.add(field);
			}
		}
	}
	return ListClassField;
}
public Set<Field> getArrayClassFields() {
		Field[] fields = targetClass.getDeclaredFields();
		for (Field field : fields) {
			if (field.getType().isArray()) {
				Class<?> componentType   = field.getType().getComponentType();
				if (classMemePack(componentType.getName())) {
						ListClassField.add(field);
				}
			}
		}
		return ListClassField;
}
public Class<?> getElementType(Field field) {
	ParameterizedType genericType =(ParameterizedType) field.getGenericType();
	Class<?> elementType =(Class<?>) genericType.getActualTypeArguments()[0];
	return elementType;
}
public Class<?> getElementType(Parameter parameter) {
	ParameterizedType genericType = (ParameterizedType) parameter.getParameterizedType();
	Class<?> elementType = (Class<?>) genericType.getActualTypeArguments()[0];
	return elementType;
}
public Class<?>[] getImplementationInterface() {
	Class<?> [] implementedInterface = targetClass.getInterfaces();
	return implementedInterface;
}
public Class<?> getExensionRelation() {
	Class<?> superClass = targetClass.getSuperclass();
	return superClass;
}
public Set<Field> getAssociationRelations() {
	association.addAll(getClassFields());
	association.addAll(getListClassFields());
	association.addAll(getArrayClassFields());
	return association;
}
public Set<Field> getAgregationRelations() {

	Set<Field> test = new HashSet<Field>();
	for (Field field : association) {
		if (isConstuctorParameter(field)) {
			agregation.add(field);
			test.add(field);
		}
	}
	association.removeAll(test);
	return agregation;
}

public Set<Field> getCompositionRelations() {
	Set<Field> test = new HashSet<Field>();
	for (Field field : agregation) {
		if (Modifier.isFinal(field.getModifiers())) {
			test.add(field);
			composition.add(field);
		}
	}
	agregation.removeAll(test);
	return composition;
}

public Set<Parameter> getMethodesParameters() {
	Method[] methods = targetClass.getDeclaredMethods();
	Set<Parameter> allParameters = new HashSet<>();
	for(Method method : methods) {
		allParameters.addAll(Arrays.asList(method.getParameters()));
	}
	return allParameters;
}

public Set<Parameter> getUtilisationRelations() {
	Set<Parameter> parameters = getMethodesParameters();
	for (Parameter parameter : parameters) {
		if (parameter.getParameterizedType() instanceof ParameterizedType) {
			Class<?> elementType = getElementType(parameter);
			if (!elementType.isPrimitive() && classMemePack(elementType.getName())) {
				utilisation.add(parameter);
			}
		}
		else if(parameter.getType().isArray()) {
			Class<?> componentType = parameter.getType().getComponentType();
			if(classMemePack(componentType.getName()))
			utilisation.add(parameter);
		}
		else if (!parameter.getType().isPrimitive() && classMemePack(parameter.getType().getName()) ) {
				utilisation.add(parameter);
		}
	}
	return utilisation;
}

public Set<Field> getAssociation() {
	return association;
}

public Set<Field> getAgregation() {
	return agregation;
}

public Set<Field> getComposition() {
	return composition;
}

public Set<Parameter> getUtilisation() {
	return utilisation;
}
	
public static void main(String[] args) {
	try {
		Class<?> cls = Class.forName("org.mql.java.examples.Etudiant");
		ClassRelations c = new ClassRelations("C:\\Users\\HP\\Documents\\UML Diagram Generator\\bin\\",cls);
		c.classMemePack("org.mql.java.examples.School");
		Set<Field> list = new HashSet<>();
		list =c.getAssociation();
		System.out.println(list);
	} catch (Exception e) { }
	
}


}

