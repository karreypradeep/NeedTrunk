/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.model;

/**
 * class for relation
 * 
 * @author Pradeep
 */
import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ADMISSION_RELATION")
public class AdmissionRelation implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= -6086032620330352387L;

	@EmbeddedId
	private AdmissionRelationPK	admissionRelationPK;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.getAdmissionRelationPK() == null ? 0 : this.getAdmissionRelationPK().hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		AdmissionRelation other = (AdmissionRelation) obj;
		if (this.getAdmissionRelationPK() == null) {
			if (other.getAdmissionRelationPK() != null) {
				return false;
			}
		} else if (!this.getAdmissionRelationPK().equals(other.getAdmissionRelationPK())) {
			return false;
		}
		return true;
	}

	public AdmissionRelationPK getAdmissionRelationPK() {
		return this.admissionRelationPK;
	}

	public void setAdmissionRelationPK(final AdmissionRelationPK admissionRelationPK) {
		this.admissionRelationPK = admissionRelationPK;
	}
}
