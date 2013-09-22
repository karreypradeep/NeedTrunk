/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.portal;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.EducationHistory;

@Named
@Scope(value = "session")
public class EducationHistoryBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long	serialVersionUID	= -4537153586280669071L;
	private EducationHistory	educationHistory;

	@PostConstruct
	public void initialize() {
		this.educationHistory = new EducationHistory();

	}

	public EducationHistory getEducationHistory() {
		return this.educationHistory;
	}

	public void setEducationHistory(final EducationHistory educationHistory) {
		this.educationHistory = educationHistory;
	}

}
