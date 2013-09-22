package com.apeironsol.need.core.dao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.ProfilePicture;
import com.apeironsol.framework.BaseDaoImpl;

@Repository("profilePictureDao")
public class ProfilePictureDaoImpl extends BaseDaoImpl<ProfilePicture> implements ProfilePictureDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfilePicture findStudentProfilePicture(final String referenceId) {

		try {
			TypedQuery<ProfilePicture> query = this.getEntityManager().createQuery("select p from ProfilePicture p where p.referenceId = :referenceId",
					ProfilePicture.class);
			query.setParameter("referenceId", referenceId);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
