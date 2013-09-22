/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 *
 */
package com.apeironsol.need.core.dao;

import com.apeironsol.need.core.model.AdmissionReservationFee;
import com.apeironsol.framework.BaseDao;

/**
 *
 * @author sunny
 *         Data access interface for admission reservation fee entity.
 *
 */
public interface AdmissionReservationFeeDao extends BaseDao<AdmissionReservationFee> {

	/**
	 * Find admission reservation fee by student id.
	 *
	 * @param sectionId
	 *            sectionId
	 * @return admission reservation fee by student id.
	 */
	AdmissionReservationFee findAdmissionReservationFeeByStudentID(Long studentId);


}
