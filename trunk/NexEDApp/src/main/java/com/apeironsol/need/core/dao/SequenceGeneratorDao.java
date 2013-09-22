/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import com.apeironsol.need.core.model.SequenceGenerator;
import com.apeironsol.need.util.constants.SequenceTypeConstant;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for batch entity.
 * 
 * @author Pradeep
 * 
 */
public interface SequenceGeneratorDao extends BaseDao<SequenceGenerator> {

	/**
	 * Find sequential generator by sequence type and sequence sub type.
	 * 
	 * @param sequenceType
	 *            sequence type.
	 * @param sequenceSubType
	 *            sequence sub type.
	 * @return sequential generator by sequence type and sequence sub type.
	 */
	SequenceGenerator findSequentialGeneratorBySequenceTypeAndSequenceSubType(final SequenceTypeConstant sequenceType, final String sequenceSubType);

}
