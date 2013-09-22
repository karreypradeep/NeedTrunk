/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.reporting.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Utility class.
 * 
 * @author pradeep
 * 
 */
public final class ReflectionHelper {

	/**
	 * Prevents instantiation of this type.
	 */
	private ReflectionHelper() {
	}

	/**
	 * <p>
	 * Creates a new instance of the given class type without throwing (checked)
	 * exceptions.
	 * </p>
	 * 
	 * <p>
	 * If no new instance could be created, this will result in a
	 * {@link RuntimeException} being thrown from this method.
	 * </p>
	 * 
	 * <p>
	 * By means of the given array of (optional) arguments, the right
	 * <code>public</code> {@link Constructor} will be selected and invoked with
	 * the same arguments. If no corresponding constructor is available in the
	 * given class, this method will throw a runtime exception that indicates
	 * that this is the case.
	 * </p>
	 * 
	 * @param clazz
	 *            The class for which a new instance should be created.
	 * @param constructorArguments
	 *            The arguments that should be passed to the constructor that is
	 *            used.
	 * @return An instance of the given type, never <code>null</code>.
	 * @param <T>
	 *            The type for which an instance should be created.
	 */
	// Checkstyle - disable: MethodLength|CyclomaticComplexity
	public static <T> T getNewInstance(final Class<T> clazz, final Object... constructorArguments) {

		T result = null;

		try {
			result = getConstructor(clazz, constructorArguments).newInstance(constructorArguments);
		} catch (final InstantiationException exception) {
			throw new RuntimeException(exception);
		} catch (final IllegalAccessException exception) {
			throw new RuntimeException(exception);
		} catch (final SecurityException exception) {
			throw new RuntimeException(exception);
		} catch (final IllegalArgumentException exception) {
			throw new RuntimeException(exception);
		} catch (final InvocationTargetException exception) {
			throw new RuntimeException(exception);
		}

		return result;
	}

	/**
	 * <p>
	 * Returns the constructor that can instantiate a new instance of the given
	 * class by providing the constructor the given constructor arguments.
	 * </p>
	 * 
	 * <p>
	 * When the system does not have sufficient permissions to access this
	 * constructor, or when no constructor with the sought signature is
	 * available, this method will throw a runtime exception that indicates what
	 * the problem is. In turn, this method will always return a valid
	 * constructor when it doesn't fail.
	 * </p>
	 * 
	 * @param clazz
	 *            The class for which the constructor should be returned.
	 * @param constructorArguments
	 *            The arguments that need to be passed to the constructor.
	 * @return The requested constructor.
	 * @param <T>
	 *            The type for which a constructor should be returned.
	 */
	// Checkstyle - disable: MethodLength
	private static <T> Constructor<T> getConstructor(final Class<T> clazz, final Object... constructorArguments) {
		Constructor<T> result = null;

		try {
			result = clazz.getConstructor(getClassArrayForValues(constructorArguments));
		} catch (final SecurityException exception) {
			throw new RuntimeException(exception);
		} catch (final NoSuchMethodException exception) {
			throw new RuntimeException(exception);
		}

		return result;
	}

	/**
	 * Returns an array that holds the {@link Class}es of the given values in
	 * the exact same order.
	 * 
	 * @param values
	 *            The values for which an array of classes should be returned.
	 * @return The requested array of classes.
	 */
	private static Class<?>[] getClassArrayForValues(final Object... values) {
		final Class<?>[] result = new Class<?>[values.length];

		for (int i = 0; i < values.length; i++) {
			result[i] = values[i].getClass();
		}

		return result;
	}

	/**
	 * Retrieves all the fields in the given class as well as all of its super
	 * classes.
	 * 
	 * @param clazz
	 *            The class for which the fields should be returned.
	 * @return The fields in the given class as well as all of its super
	 *         classes.
	 */
	public static List<Field> getDeclaredFieldsInTypeHierarchy(final Class<?> clazz) {
		final List<Field> result = new ArrayList<Field>();

		result.addAll(Arrays.asList(clazz.getDeclaredFields()));
		if (clazz.getSuperclass() != null) {
			result.addAll(getDeclaredFieldsInTypeHierarchy(clazz.getSuperclass()));
		}

		return result;
	}

	/**
	 * Retrieves all the fields in the given class as well as all of its super
	 * classes.
	 * 
	 * @param clazz
	 *            The class for which the fields should be returned.
	 * @return The fields in the given class as well as all of its super
	 *         classes.
	 */
	public static List<Field> getDeclaredFieldsInClass(final Class<?> clazz) {
		return Arrays.asList(clazz.getDeclaredFields());
	}

	/**
	 * Returns the field in the collection of all the declared fields in the
	 * type hierarchy of the given class that has the given field name.
	 * 
	 * @param clazz
	 *            The class.
	 * @param fieldName
	 *            The field name.
	 * @return The requested field, or <code>null</code> when the class
	 *         (hierarchy) has no declared field with the given field name.
	 */
	public static Field getFieldFromDeclaredFieldsInTypeHierarchy(final Class<?> clazz, final String fieldName) {
		Field result = null;

		final Iterator<Field> iterator = getDeclaredFieldsInTypeHierarchy(clazz).iterator();
		while (result == null && iterator.hasNext()) {
			result = getFieldWhenNamesAreEqual(iterator.next(), fieldName);
		}

		return result;
	}

	/**
	 * Returns the given field when it holds the given field name.
	 * 
	 * @param field
	 *            The field.
	 * @param fieldName
	 *            The field name.
	 * @return The given field when it holds the given field name,
	 *         <code>null</code> otherwise.
	 */
	private static Field getFieldWhenNamesAreEqual(final Field field, final String fieldName) {
		return fieldName.equals(field.getName()) ? field : null;
	}

	/**
	 * Get the direct subclass of the given end class, or the {@link Object}
	 * class when no direct subclass of the given end class can be found in its
	 * hierarchy.
	 * 
	 * @param startClass
	 *            The class to begin the traversion from.
	 * @param endClass
	 *            The class of which its direct sub class is sought by this
	 *            method.
	 * @return The direct subclass of the given end class, or the
	 *         <code>Object</code> class.
	 */
	public static Class<?> getDirectSubclassOrObjectClassByTraversingClassHierarchy(final Class<?> startClass,
			final Class<?> endClass) {

		Class<?> result = startClass;

		while (!(isObjectClass(result) || isDirectSubclass(result, endClass))) {
			result = result.getSuperclass();
		}

		return result;
	}

	/**
	 * Determine whether the given class is the {@link Object} class (i.e. its
	 * superclass is <code>null</code>).
	 * 
	 * @param clazz
	 *            The class for which to determine whether it is the
	 *            <code>Object</code> class.
	 * @return <code>true</code> if <code>clazz</code> is the
	 *         <code>Object</code> class, <code>false</code> otherwise.v
	 */
	private static boolean isObjectClass(final Class<?> clazz) {
		return clazz.getSuperclass() == null;
	}

	/**
	 * Determine whether the given class is a direct subclass of this class.
	 * 
	 * @param clazz
	 *            The class for which to determine whether it is a direct
	 *            subclass.
	 * @param superClass
	 *            The class which is (potentially) the direct super class of the
	 *            given class.
	 * @return <code>true</code> if <code>clazz</code> is a direct subclass,
	 *         <code>false</code> otherwise.
	 */
	private static boolean isDirectSubclass(final Class<?> clazz, final Class<?> superClass) {
		return clazz.getSuperclass().equals(superClass);
	}

	/**
	 * Returns the annotation that is of the given type for the given field.
	 * 
	 * @param field
	 *            The field for which the annotation should be returned.
	 * @param clazz
	 *            The type that is being sought.
	 * @return The requested annotation, or <code>null</code> when it could not
	 *         be found.
	 * @param <T>
	 *            The type of annotation.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Annotation> T getAnnotationForFieldAndType(final Field field, final Class<T> clazz) {
		T result = null;

		final int i = 0;
		while (result == null && i < field.getDeclaredAnnotations().length) {
			if (clazz.equals(field.getDeclaredAnnotations()[i].annotationType())) {
				result = (T) field.getDeclaredAnnotations()[i];
			}
		}

		return result;
	}

	/**
	 * Returns the value that is assigned to the given field.
	 * 
	 * @param object
	 *            The object of which the given field is a part.
	 * @param field
	 *            The field that the value should be returned for.
	 * @return The value that is assigned to the given field.
	 */
	public static Object getFieldValue(final Object object, final Field field) {
		Object result;

		final boolean wasAccessible = ensureFieldAccessible(field);

		try {
			result = getAccessibleFieldValue(object, field);
		} finally {
			restoreFieldAccessibility(field, wasAccessible);
		}

		return result;
	}

	/**
	 * Ensure that the given field can be accessed by this class.
	 * 
	 * @param field
	 *            the field to make accessible.
	 * @return <code>true</code> if the field was already accessible,
	 *         <code>false</code> otherwise.
	 */
	private static boolean ensureFieldAccessible(final Field field) {
		boolean wasAccessible = true;

		if (!field.isAccessible()) {
			wasAccessible = false;
			field.setAccessible(true);
		}

		return wasAccessible;
	}

	/**
	 * Returns the value that is assigned to the given field.
	 * 
	 * @param object
	 *            The object of which the given field is a part.
	 * @param field
	 *            The field that the value should be returned for.
	 * @return The value that is assigned to the given field.
	 */
	// Checkstyle - disable: MethodLength
	private static Object getAccessibleFieldValue(final Object object, final Field field) {
		Object result = null;

		try {
			result = getCloneSafeValue(field.get(object));
		} catch (final IllegalArgumentException exception) {
			// LOG.error(exception.getMessage(), exception);
		} catch (final IllegalAccessException exception) {
			// LOG.error(exception.getMessage(), exception);
		}

		return result;
	}

	/**
	 * Restore the accessibility of the field.
	 * 
	 * @param field
	 *            the field to restore the accessibility of.
	 * @param mustBeAccessible
	 *            whether the field must be accessible.
	 */
	private static void restoreFieldAccessibility(final Field field, final boolean mustBeAccessible) {
		if (!mustBeAccessible) {
			field.setAccessible(false);
		}
	}

	/**
	 * <p>
	 * Returns the "clone safe value" of the given object.
	 * </p>
	 * 
	 * <p>
	 * This means that it's either the given object itself, or a
	 * <code>clone()</code> of it when the given object is one of the following
	 * types:
	 * </p>
	 * 
	 * <ul>
	 * <li>{@link java.util.Date}</li>
	 * <li>{@link java.sql.Timestamp}</li>
	 * </ul>
	 * 
	 * @param o
	 *            The object to (possibly) clone.
	 * @return The given object, or a <code>clone()</code> of it when the type
	 *         of object is one of the previously mentioned ones.
	 */
	private static Object getCloneSafeValue(final Object o) {
		Object result = o;

		if (o instanceof Date) {
			result = ((Date) o).clone();
		} else if (o instanceof Timestamp) {
			result = ((Timestamp) o).clone();
		}

		return result;
	}
}
