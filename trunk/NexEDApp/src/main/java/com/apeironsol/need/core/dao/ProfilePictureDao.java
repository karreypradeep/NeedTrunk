package com.apeironsol.need.core.dao;

import com.apeironsol.need.core.model.ProfilePicture;
import com.apeironsol.framework.BaseDao;

public interface ProfilePictureDao extends BaseDao<ProfilePicture> {

	/**
	 * Retrieve student profile picture by admission number.
	 * 
	 * @param admissionNr
	 *            admission number.
	 * @return student profile picture by admission number.
	 */
	ProfilePicture findStudentProfilePicture(String admissionNr);

}
