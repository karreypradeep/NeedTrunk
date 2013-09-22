/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

import com.apeironsol.need.util.constants.SequenceTypeConstant;

/**
 * <p>
 * Helper class to fetch entity lockables that are to be used in
 * {@code synchronized} blocks.
 * 
 * @author Pradeep
 */
public final class SequancialKeyLockableFactory {

	/**
	 * The map containing all the entity lockables, stored by type and object
	 * id.
	 */
	private final Map<String, Map<String, EntityLockable>>	store;

	/**
	 * The singleton instance.
	 */
	private static SequancialKeyLockableFactory				instance;

	/**
	 * Private constructor for singleton pattern.
	 */
	private SequancialKeyLockableFactory() {
		this.store = new HashMap<String, Map<String, EntityLockable>>();
	}

	/**
	 * Returns the one and only entity lockable factory.
	 * 
	 * @return The one and only entity lockable factory.
	 */
	public static synchronized SequancialKeyLockableFactory getInstance() {

		if (instance == null) {
			instance = new SequancialKeyLockableFactory();
		}

		return instance;
	}

	/**
	 * Gets a synchronizable entity from the map.
	 * 
	 * @param entityType
	 *            The type of entity that an entity lockable should be returned
	 *            for.
	 * @param sequenceKey
	 *            The object id of the entity.
	 * @return An entity lockable.
	 */
	public synchronized EntityLockable getSynchronizableEntity(final Class<?> entityType, final SequenceTypeConstant sequenceType, final String sequenceSubType) {

		Assert.notNull(entityType);
		Assert.notNull(sequenceType);
		Assert.notNull(sequenceSubType);

		String key = sequenceType.toString() + sequenceSubType;

		final String qualifiedClassName = entityType.getName();

		Map<String, EntityLockable> objectIdToSynchronizableEntity = this.store.get(qualifiedClassName);

		if (objectIdToSynchronizableEntity == null) {
			objectIdToSynchronizableEntity = new HashMap<String, EntityLockable>();
			this.store.put(qualifiedClassName, objectIdToSynchronizableEntity);
		}

		EntityLockable result = objectIdToSynchronizableEntity.get(key);

		if (result == null) {
			result = new EntityLockable(qualifiedClassName, key);
			objectIdToSynchronizableEntity.put(key, result);
		}

		return result;
	}

	/**
	 * A lockable object that is entity and object id based.
	 * 
	 */
	public final class EntityLockable {

		/**
		 * The fully qualified class name of the entity that this lock is based
		 * on.
		 */
		private final String	qualifiedClassName;

		/**
		 * The sequenceKey.
		 */
		private final String	sequenceKey;

		/**
		 * Private constructor, so that these kind of objects can only be
		 * instantiated from within the class it was declared in.
		 * 
		 * @param qualifiedClassName
		 *            The fully qualified name of the Java class that represents
		 *            the entity.
		 * @param sequenceKey
		 *            The sequenceKey.
		 */
		private EntityLockable(final String qualifiedClassName, final String sequenceKey) {
			this.qualifiedClassName = qualifiedClassName;
			this.sequenceKey = sequenceKey;
		}

		/**
		 * {@inheritDoc} Overridden to get useful information when debugging.
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			final StringBuilder buffer = new StringBuilder();

			buffer.append("[[").append(EntityLockable.class.getName()).append("@").append(this.hashCode()).append("]:");
			buffer.append(" qualifiedClassName=").append(this.qualifiedClassName);
			buffer.append(", sequenceKey=").append(this.sequenceKey);
			buffer.append("]");

			return buffer.toString();
		}
	}
}