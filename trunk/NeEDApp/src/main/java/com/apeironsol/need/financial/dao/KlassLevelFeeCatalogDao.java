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

import com.apeironsol.need.financial.model.KlassLevelFee;
import com.apeironsol.need.financial.model.KlassLevelFeeCatalog;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for class fee payment implementation.
 * 
 * @author Pradeep
 * 
 */
public interface KlassLevelFeeCatalogDao extends BaseDao<KlassLevelFeeCatalog> {

	/**
	 * Find class fee payments by class fee id.
	 * 
	 * @param klassFeeId
	 *            class fee id.
	 * @return collection of class fee payments by class fee id.
	 */
	Collection<KlassLevelFeeCatalog> findKlassFeePaymentsByKlassFeeId(Long klassFeeId);

	/**
	 * Remove class fee payments by class fee.
	 * 
	 * @param klassLevelFee
	 *            class fee.
	 */
	void removeKlassFeePaymentsByKlassFeeId(final KlassLevelFee klassLevelFee);

	/**
	 * Find class fee payments by class fee id and due date.
	 * 
	 * @param klassFeeId
	 *            class fee id.
	 * @param dueDate
	 *            due date
	 * @return
	 */
	Collection<KlassLevelFeeCatalog> findKlassFeePaymentsByKlassFeeIdAndDueDate(final Long klassFeeId,
			final Date dueDate);
}
