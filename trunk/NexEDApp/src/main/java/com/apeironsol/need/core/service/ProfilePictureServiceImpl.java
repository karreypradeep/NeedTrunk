package com.apeironsol.need.core.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.ProfilePictureDao;
import com.apeironsol.need.core.model.ProfilePicture;

@Service("profilePictureService")
@Transactional
public class ProfilePictureServiceImpl implements ProfilePictureService {

	@Resource
	ProfilePictureDao	profilePictureDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfilePicture saveStudentProfilePicture(final String referenceId, final byte[] profilePictureAsBytes) {

		ProfilePicture profilePicture = this.findStudentProfilePicture(referenceId);

		if (profilePicture == null) {
			profilePicture = new ProfilePicture();
			profilePicture.setReferenceId(referenceId);
			profilePicture.setProfilePicture(profilePictureAsBytes);

		} else {
			profilePicture.setProfilePicture(profilePictureAsBytes);
		}

		return this.profilePictureDao.persist(profilePicture);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfilePicture findStudentProfilePicture(final String referenceId) {

		return this.profilePictureDao.findStudentProfilePicture(referenceId);
	}

}
