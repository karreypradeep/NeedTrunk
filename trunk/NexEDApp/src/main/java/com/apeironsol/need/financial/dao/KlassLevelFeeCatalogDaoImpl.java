/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.dao;

import java.util.Collection;
import java.util.Date;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.apeironsol.need.financial.model.KlassLevelFee;
import com.apeironsol.need.financial.model.KlassLevelFeeCatalog;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for class fee
 * 
 * @author Pradeep
 * 
 */
@Repository
public class KlassLevelFeeCatalogDaoImpl extends BaseDaoImpl<KlassLevelFeeCatalog> implements KlassLevelFeeCatalogDao {

	private static final Logger	log	= Logger.getLogger(KlassLevelFeeCatalogDaoImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<KlassLevelFeeCatalog> findKlassFeePaymentsByKlassFeeId(final Long klassLevelFeeId) {
		try {
			TypedQuery<KlassLevelFeeCatalog> query = this.getEntityManager().createQuery(
					"select kfp from KlassLevelFeeCatalog kfp where kfp.klassLevelFee.id= :klassLevelFeeId)", KlassLevelFeeCatalog.class);
			query.setParameter("klassLevelFeeId", klassLevelFeeId);
			return query.getResultList();
		} catch (NoResultException e) {
			log.info(e.getMessage());
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeKlassFeePaymentsByKlassFeeId(final KlassLevelFee klassLevelFee) {

		Query query = this.getEntityManager().createQuery("delete from KlassLevelFeeCatalog kfp where kfp.klassLevelFee = :klassFee");
		query.setParameter("klassFee", klassLevelFee);
		query.executeUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<KlassLevelFeeCatalog> findKlassFeePaymentsByKlassFeeIdAndDueDate(final Long klassLevelFeeId, final Date dueDate) {

		TypedQuery<KlassLevelFeeCatalog> query = this.getEntityManager().createQuery(
				"select kfp from KlassLevelFeeCatalog kfp where kfp.klassLevelFee.id= :klassLevelFeeId and kfp.dueDate <= :dueDate)",
				KlassLevelFeeCatalog.class);
		query.setParameter("klassLevelFeeId", klassLevelFeeId);
		query.setParameter("dueDate", dueDate);
		return query.getResultList();
	}

}
