package com.apeironsol.need.core.service;

import com.apeironsol.need.core.model.ProfilePicture;

public interface ProfilePictureService {

	/**
	 * Save student profile picture.
	 * 
	 * @param admissionNr
	 *            admission number of student.
	 * @param profilePictureAsBytes
	 *            profile picture as byte array.
	 * @return
	 */
	ProfilePicture saveStudentProfilePicture(String admissionNr, byte[] profilePictureAsBytes);

	/**
	 * Find the student profile picture by student admission number.
	 * 
	 * @param referenceId
	 *            : Reference id can be student admission number or employee
	 *            number.
	 * @return
	 */
	ProfilePicture findStudentProfilePicture(String referenceId);

}
