package com.apeironsol.need.core.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.apeironsol.need.util.constants.SequenceTypeConstant;

@Entity
@Table(name = "SEQUENCE_GENERATOR")
public class SequenceGenerator implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long		serialVersionUID	= 543942485098456354L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long					id;

	@NotNull(message = "model.sequence_type_mandatory")
	@Basic
	@Column(name = "SEQUENCE_TYPE", length = 100, nullable = false)
	@Enumerated(EnumType.STRING)
	private SequenceTypeConstant	sequenceType;

	@NotEmpty(message = "model.sequential_sub_type_mandatory")
	@Column(name = "SEQUENCE_SUB_TYPE", nullable = false, length = 20)
	private String					sequenceSubType;

	@Column(name = "CURRENT_SEQUENCE", nullable = false)
	private Long					currentSequence;

	@Column(name = "NEXT_SEQUENCE", nullable = false)
	private Long					nextSequence;

	public SequenceTypeConstant getSequenceType() {
		return this.sequenceType;
	}

	public void setSequenceType(final SequenceTypeConstant sequenceType) {
		this.sequenceType = sequenceType;
	}

	public String getSequenceSubType() {
		return this.sequenceSubType;
	}

	public void setSequenceSubType(final String sequenceSubType) {
		this.sequenceSubType = sequenceSubType;
	}

	public Long getCurrentSequence() {
		return this.currentSequence;
	}

	public void setCurrentSequence(final Long currentSequence) {
		this.currentSequence = currentSequence;
	}

	public Long getNextSequence() {
		return this.nextSequence;
	}

	public void setNextSequence(final Long nextSequence) {
		this.nextSequence = nextSequence;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

}
