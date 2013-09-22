/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.dao;

import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.StudentTransportation;
import com.apeironsol.need.util.constants.StudentTransportationStatusConstant;
import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;

/**
 * @author Sunny
 * 
 *         Data access interface for student transportation entity
 *         implementation.
 */
@Repository("studentTransportationDao")
public class StudentTransportationDaoImpl extends BaseDaoImpl<StudentTransportation> implements StudentTransportationDao {

	@Override
	public Collection<StudentTransportation> findAllStudentTransportationsByStudentAcadmicYearId(final Long studentAcademicYearId) throws BusinessException {
		TypedQuery<StudentTransportation> query = this.getEntityManager().createQuery(
				"select st from StudentTransportation st where st.studentAcademicYear.id = :studentAcademicYearId", StudentTransportation.class);
		query.setParameter("studentAcademicYearId", studentAcademicYearId);
		return query.getResultList();
	}

	@Override
	public StudentTransportation findStudentTransportationsByStudentAcadmicYearIdAndStatus(
			final Long studentAcademicYearId,final StudentTransportationStatusConstant studentTransportationStatus) throws BusinessException {
		try {
		TypedQuery<StudentTransportation> query = this.getEntityManager().createQuery(
				"select st from StudentTransportation st where st.studentAcademicYear.id = :studentAcademicYearId and st.studentTransportationStatus = :studentTransportationStatus",
				StudentTransportation.class);
		query.setParameter("studentAcademicYearId", studentAcademicYearId);
		query.setParameter("studentTransportationStatus", studentTransportationStatus);
		query.setMaxResults(1);
		return query.getSingleResult();
		}catch(NoResultException e) {
			return null;
		}
	}

	@Override
	public Collection<StudentTransportation> findStudentTransportationByPickupPointId(Long pickupPointId) {
		
		TypedQuery<StudentTransportation> query = this.getEntityManager().createQuery(
				"select st from StudentTransportation st where st.pickUpPoint.id = :pickupPointId",
				StudentTransportation.class);
		query.setParameter("pickupPointId", pickupPointId);
		return query.getResultList();
		
	}
}
