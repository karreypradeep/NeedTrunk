/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.SequenceGenerator;
import com.apeironsol.need.util.constants.SequenceTypeConstant;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for batch implementation entity.
 * 
 * @author Pradeep
 * 
 */
@Repository("sequenceGeneratorDao")
public class SequenceGeneratorDaoImpl extends BaseDaoImpl<SequenceGenerator> implements SequenceGeneratorDao {
	private static final Logger	log	= Logger.getLogger(SequenceGeneratorDaoImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SequenceGenerator findSequentialGeneratorBySequenceTypeAndSequenceSubType(final SequenceTypeConstant sequenceType, final String sequenceSubType) {
		try {
			TypedQuery<SequenceGenerator> query = this.getEntityManager().createQuery(
					"select sg from SequenceGenerator sg where sg.sequenceType = :sequenceType and sg.sequenceSubType = :sequenceSubType",
					SequenceGenerator.class);
			query.setParameter("sequenceType", sequenceType);
			query.setParameter("sequenceSubType", sequenceSubType);
			return query.getSingleResult();
		} catch (NoResultException e) {
			log.info(e.getMessage());
			return null;
		}
	}

}
