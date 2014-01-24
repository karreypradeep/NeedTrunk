/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.academics.dao;

import java.util.Collection;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.need.academics.model.ReportCard;
import com.apeironsol.need.util.NonNull;

/**
 * Data access interface for employee salary entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("reportCardDao")
public class ReportCardDaoImpl extends BaseDaoImpl<ReportCard> implements ReportCardDao {

	@Override
	public Collection<ReportCard> findAllReportCards(@NonNull final Long branchId) {
		TypedQuery<ReportCard> query = this.getEntityManager().createQuery("select gs from ReportCard gs where gs.branch.id  = :branchId", ReportCard.class);
		query.setParameter("branchId", branchId);
		return query.getResultList();
	}

}
