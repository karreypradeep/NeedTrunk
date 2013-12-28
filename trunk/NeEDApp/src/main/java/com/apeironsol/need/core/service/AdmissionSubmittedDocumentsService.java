/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import com.apeironsol.need.core.model.AdmissionSubmittedDocuments;

/**
 * Service Interface forStudentSection.
 * 
 * @author Pradeep
 * 
 */
public interface AdmissionSubmittedDocumentsService {

	AdmissionSubmittedDocuments saveAdmissionSubmittedDocuments(final AdmissionSubmittedDocuments admissionSubmittedDocuments);

	void removeAdmissionSubmittedDocuments(final AdmissionSubmittedDocuments admissionSubmittedDocuments);

}
