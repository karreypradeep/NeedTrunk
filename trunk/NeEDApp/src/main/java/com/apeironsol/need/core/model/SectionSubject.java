package com.apeironsol.need.core.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.apeironsol.framework.BaseEntity;


@Entity
@Table(name = "SECTION_SUBJECT", uniqueConstraints = { @UniqueConstraint(columnNames = { "SECTION_ID", "SUBJECT_ID" }) })
public class SectionSubject extends BaseEntity {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= -4879846476642525886L;

	@NotNull(message = "model.section_mandatory")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SECTION_ID", nullable = false)
	private Section				section;

	@NotNull(message = "model.subject_mandatory")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUBJECT_ID", nullable = false)
	private Subject				subject;

	public Section getSection() {
		return section;
	}

	public void setSection(final Section section) {
		this.section = section;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(final Subject subject) {
		this.subject = subject;
	}

}
