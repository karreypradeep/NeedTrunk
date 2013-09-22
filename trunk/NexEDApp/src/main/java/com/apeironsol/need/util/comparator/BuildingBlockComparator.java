/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.comparator;

import java.io.Serializable;
import java.util.Comparator;

import com.apeironsol.need.core.model.BuildingBlock;

/**
 * Comparator for BuildingBlock objects.
 * 
 * @author Pradeep.
 */
public class BuildingBlockComparator implements Comparator<BuildingBlock>, Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1054777258492582017L;

	/**
	 * Property to compare by: name.
	 */
	public static final String	NAME				= "Name";

	/**
	 * Property to compare by: code.
	 */
	public static final String	CODE				= "Code";

	private final String		orderString;

	/**
	 * Constructor to compare BuildingBlock objects, using the supplied
	 * order.
	 * 
	 * @param orderBy
	 *            The field used to order the collection.
	 */
	public BuildingBlockComparator(final String orderBy) {
		this.orderString = orderBy;
	}

	/**
	 * Compares the supplied BuildingBlock objects.
	 * 
	 * @param buildingBlock1
	 *            The first BuildingBlock object.
	 * @param buildingBlock2
	 *            The second BuildingBlock object.
	 * @return Negative value when objectOne is before objectTwo, zero if both
	 *         objects are equal and a positive value if
	 *         objectOne is after objectTwo.
	 */
	@Override
	public int compare(final BuildingBlock buildingBlock1, final BuildingBlock buildingBlock2) {
		int result = 0;
		if (NAME.equals(this.orderString)) {
			result = buildingBlock1.getName().compareTo(buildingBlock2.getName());
		} else if (CODE.equals(this.orderString)) {
			result = buildingBlock1.getCode().compareTo(buildingBlock2.getCode());
		}
		return result;
	}
}
