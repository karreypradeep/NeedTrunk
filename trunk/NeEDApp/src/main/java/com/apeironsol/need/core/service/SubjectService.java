/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */

package com.apeironsol.need.core.service;

import java.util.Collection;

import com.apeironsol.need.core.model.Subject;

/**
 * Service interface for course.
 * 
 * @author pradeep
 * 
 */
public interface SubjectService {

	Subject saveSubject(Subject course);

	void removeSubject(Subject course);

	Subject findSubjectById(Long id);

	Collection<Subject> findAllSubjects();

	Collection<Subject> findAllSubjectsByKlassId(Long id);

	Collection<Subject> findElectiveSubjectsByKlassId(Long id);

}
