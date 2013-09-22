package com.apeironsol.need.core.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.apeironsol.framework.BaseEntity;

@Entity
@Table(name = "PROFILE_PICTURE")
public class ProfilePicture extends BaseEntity {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= -3912781344008270154L;

	@NotEmpty(message = "model.profile_picture_is_requried")
	@Column(name = "REFERENCE_ID", nullable = false, length = 100, unique = true)
	private String				referenceId;

	@NotEmpty(message = "model.profile_picture_is_requried")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "PROFILE_PICTURE", nullable = false)
	private byte[]				profilePicture;

	public String getReferenceId() {
		return this.referenceId;
	}

	public void setReferenceId(final String referenceId) {
		this.referenceId = referenceId;
	}

	public byte[] getProfilePicture() {
		return this.profilePicture;
	}

	public void setProfilePicture(final byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}

}
