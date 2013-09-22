package com.apeironsol.need.core.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AdmissionRelationPK implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= -6843754019070711485L;

	@Basic(optional = false)
	@Column(name = "ADMISSION_ID")
	private Long				admissionId;

	@Basic(optional = false)
	@Column(name = "RELATION")
	private Long				relationId;

	@Override
	public int hashCode() {
		Long hash = new Long(0);
		hash += this.admissionId;
		hash += this.relationId;
		return hash.hashCode();
	}

	@Override
	public boolean equals(final Object object) {
		if (object == null) {
			return false;
		}

		if (object == this) {
			return true;
		}

		if (!(object instanceof AdmissionRelationPK)) {
			return false;
		}

		AdmissionRelationPK other = (AdmissionRelationPK) object;
		if (this.admissionId.longValue() != other.admissionId.longValue()) {
			return false;
		}
		if (this.relationId.longValue() != other.relationId.longValue()) {
			return false;
		}
		return true;

	}

}
