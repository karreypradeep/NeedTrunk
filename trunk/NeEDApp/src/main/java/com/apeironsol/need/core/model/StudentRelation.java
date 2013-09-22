package com.apeironsol.need.core.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "STUDENT_RELATION")
public class StudentRelation implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= -6086032620330352387L;

	@EmbeddedId
	private StudentRelationPK	studentRelationPK;

	public StudentRelationPK getStudentRelationPK() {
		return this.studentRelationPK;
	}

	public void setStudentRelationPK(final StudentRelationPK studentRelationPK) {
		this.studentRelationPK = studentRelationPK;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.studentRelationPK == null ? 0 : this.studentRelationPK.hashCode());
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
		StudentRelation other = (StudentRelation) obj;
		if (this.studentRelationPK == null) {
			if (other.studentRelationPK != null) {
				return false;
			}
		} else if (!this.studentRelationPK.equals(other.studentRelationPK)) {
			return false;
		}
		return true;
	}

}
