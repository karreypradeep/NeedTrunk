package com.apeironsol.need.core.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class StudentRelationPK implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= -6843754019070711485L;

	@Basic(optional = false)
	@Column(name = "students_ID")
	private Long				studentsId;

	@Basic(optional = false)
	@Column(name = "relations_ID")
	private Long				relationsId;

	public Long getStudentsId() {
		return this.studentsId;
	}

	public void setStudentsId(final Long studentsId) {
		this.studentsId = studentsId;
	}

	public Long getRelationsId() {
		return this.relationsId;
	}

	public void setRelationsId(final Long relationsId) {
		this.relationsId = relationsId;
	}

	@Override
	public int hashCode() {
		Long hash = new Long(0);
		hash += this.studentsId;
		hash += this.relationsId;
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

		if (!(object instanceof StudentRelationPK)) {
			return false;
		}

		StudentRelationPK other = (StudentRelationPK) object;
		if (this.studentsId.longValue() != other.studentsId.longValue()) {
			return false;
		}
		if (this.relationsId.longValue() != other.relationsId.longValue()) {
			return false;
		}
		return true;

	}
}
