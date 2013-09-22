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

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.financial.model.StudentLevelFeeCatalog;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for class fee
 * 
 * @author Pradeep
 * 
 */
@Repository
public class StudentLevelFeeCatalogDaoImpl extends BaseDaoImpl<StudentLevelFeeCatalog> implements StudentLevelFeeCatalogDao {

	@Override
	public void removeStudentLevelFeeCatalogsByStudentLevelFeeId(final Long studentLevelFeeId) {
		Query query = this.getEntityManager().createQuery("delete from StudentLevelFeeCatalog slfc where slfc.studentLevelFee.id = :studentLevelFeeId");
		query.setParameter("studentLevelFeeId", studentLevelFeeId);
		query.executeUpdate();
	}

	@Override
	public Collection<StudentLevelFeeCatalog> findStudentLevelFeeCatalogsByStudentLevelFeeId(final Long studentLevelFeeId) {

		TypedQuery<StudentLevelFeeCatalog> query = this.getEntityManager().createQuery(
				"select e from StudentLevelFeeCatalog e where e.studentLevelFee.id = :studentLevelFeeId", StudentLevelFeeCatalog.class);
		query.setParameter("studentLevelFeeId", studentLevelFeeId);
		return query.getResultList();

	}

	@Override
	public Collection<StudentLevelFeeCatalog> findStudentLevelFeeCatalogsByStudentLevelFeeIdAndDueDate(final Long studentLevelFeeId, final Date dueDate) {
		TypedQuery<StudentLevelFeeCatalog> query = this.getEntityManager().createQuery(
				"select kfp from StudentLevelFeeCatalog kfp where kfp.studentLevelFee.id= :studentLevelFeeId and kfp.dueDate <= :dueDate)",
				StudentLevelFeeCatalog.class);
		query.setParameter("studentLevelFeeId", studentLevelFeeId);
		query.setParameter("dueDate", dueDate);
		return query.getResultList();
	}

}
