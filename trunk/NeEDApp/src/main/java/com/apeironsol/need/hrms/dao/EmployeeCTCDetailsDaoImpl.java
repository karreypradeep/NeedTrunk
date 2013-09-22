/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.hrms.dao;

import java.util.Collection;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.hrms.model.EmployeeCTCDetails;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for employee salary entity implementation.
 * 
 * @author Sunny
 * 
 */
@Repository("employeeCTCDetailsDao")
public class EmployeeCTCDetailsDaoImpl extends BaseDaoImpl<EmployeeCTCDetails> implements EmployeeCTCDetailsDao {

	@Override
	public void removeAllEmployeeCTCsByEmployeeCTCID(final Long employeeCTCId) {
		Query query = this.getEntityManager().createQuery("delete from EmployeeCTCDetails es where es.employeeCTC.id = :employeeCTCId");
		query.setParameter("employeeCTCId", employeeCTCId);
		query.executeUpdate();
	}

	@Override
	public Collection<EmployeeCTCDetails> findAllEmployeeCTCDetailsByEmployeeCTCID(final Long employeeCTCId) {
		TypedQuery<EmployeeCTCDetails> query = this.getEntityManager().createQuery(
				"select es from EmployeeCTCDetails es where es.employeeCTC.id = :employeeCTCId", EmployeeCTCDetails.class);
		query.setParameter("employeeCTCId", employeeCTCId);
		return query.getResultList();
	}

}
