/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.portal;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;

import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Batch;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.service.BranchExportService;
import com.apeironsol.need.util.constants.AdmissionStatusConstant;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.framework.exception.ApplicationException;

@Named
@Scope(value = "session")
public class ExportAdmissionsBean extends AbstractPortalBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long			serialVersionUID	= -3066378894334965144L;

	/**
	 * Boolean indicator to load all active batches for the branch.
	 */
	private boolean						loadActiveBatchesForBranchFlag;

	/**
	 * Collection of active batches for the branch.
	 */
	private Collection<Batch>			activeBatchesForBranch;

	private Batch						selectedBatch;

	private AcademicYear				selectedAcademicYear;

	private Collection<AcademicYear>	academicYearsForBatch;

	private Klass						selectedClass;

	private Collection<Section>			sectionsByClassAndAcademicYear;

	private Section						selectedSection;

	@Resource
	private BranchExportService			branchExportService;

	/**
	 * Student status.
	 */
	private AdmissionStatusConstant		admissionStatus;

	@Override
	public boolean isLoadActiveBatchesForBranchFlag() {
		return this.loadActiveBatchesForBranchFlag;
	}

	@Override
	public void setLoadActiveBatchesForBranchFlag(final boolean loadActiveBatchesForBranchFlag) {
		this.loadActiveBatchesForBranchFlag = loadActiveBatchesForBranchFlag;
	}

	@Override
	public Collection<Batch> getActiveBatchesForBranch() {
		return this.activeBatchesForBranch;
	}

	@Override
	public void setActiveBatchesForBranch(final Collection<Batch> activeBatchesForBranch) {
		this.activeBatchesForBranch = activeBatchesForBranch;
	}

	@Override
	public void loadActiveBatchesForBranch() {
		if (this.loadActiveBatchesForBranchFlag) {
			this.activeBatchesForBranch = this.batchService.findActiveBatchesByBranchId(this.sessionBean.getCurrentBranch().getId());

			this.loadActiveBatchesForBranchFlag = false;
		}
	}

	public void loadAcademicYearsForAdmission() {
		try {
			if (!this.sessionBean.getCurrentBranchRule().isBatchRequiredIndicator()) {
				this.loadAcademicYearsForAdmissionsOpened();
				this.academicYearsForBatch = this.getAcademicYearsWithAdmissionOpen();
			}
		} catch (final ApplicationException e) {
			ViewExceptionHandler.handle(e);
		} catch (final Exception e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public Collection<AcademicYear> getAcademicYearsForBatch() {
		return this.academicYearsForBatch;
	}

	public void setAcademicYearsForBatch(final Collection<AcademicYear> academicYearsForBatch) {
		this.academicYearsForBatch = academicYearsForBatch;
	}

	public void loadAcademicYearsForBatch() {
		try {
			this.academicYearsForBatch = this.academicYearService.findAcademicYearsForBatchId(this.getSelectedBatch().getId(), this.sessionBean
					.getCurrentBranch().getId());
		} catch (final ApplicationException e) {
			ViewExceptionHandler.handle(e);
		} catch (final Exception e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public StreamedContent getExcelFormatfile() {
		final InputStream stream = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext())
				.getResourceAsStream("/importdocs/ImportAdmissions.xls");
		final StreamedContent excelFormatfile = new DefaultStreamedContent(stream, "application/xls", "ImportAdmissions.xls");
		return excelFormatfile;
	}

	public StreamedContent getExcelAfterImport() {
		StreamedContent streamedContent = null;
		try {
			final byte[] workbook = this.branchExportService.exportAdmissions(this.selectedAcademicYear, this.selectedClass, this.admissionStatus);
			final InputStream stream = new ByteArrayInputStream(workbook);
			streamedContent = new DefaultStreamedContent(stream, "application/xls", "ExportAdmissions.xls");
		} catch (final ApplicationException e) {
			ViewExceptionHandler.handle(e);
		}
		return streamedContent;
	}

	public void handleBatchChangeEvent() {
		this.selectedAcademicYear = null;
		this.loadAcademicYearsForBatch();
		this.handleAcademicYearChangeEvent();
	}

	public void handleAcademicYearChangeEvent() {
		this.setSelectedClass(null);
		this.handleClassChangeEvent();
		this.loadSectionsByAcademicYearAndClass();
	}

	public void handleClassChangeEvent() {
		this.setSelectedSection(null);
		this.loadSectionsByAcademicYearAndClass();
	}

	/**
	 * @return the sectionsByClassAndAcademicYear
	 */
	public Collection<Section> getSectionsByClassAndAcademicYear() {
		return this.sectionsByClassAndAcademicYear;
	}

	/**
	 * @param sectionsByClassAndAcademicYear
	 *            the sectionsByClassAndAcademicYear to set
	 */
	public void setSectionsByClassAndAcademicYear(final Collection<Section> sectionsByClassAndAcademicYear) {
		this.sectionsByClassAndAcademicYear = sectionsByClassAndAcademicYear;
	}

	/**
	 * @return the selectedSection
	 */
	public Section getSelectedSection() {
		return this.selectedSection;
	}

	/**
	 * @param selectedSection
	 *            the selectedSection to set
	 */
	public void setSelectedSection(final Section selectedSection) {
		this.selectedSection = selectedSection;
	}

	/**
	 * @return the selectedClass
	 */
	public Klass getSelectedClass() {
		return this.selectedClass;
	}

	/**
	 * @param selectedClass
	 *            the selectedClass to set
	 */
	public void setSelectedClass(final Klass selectedClass) {
		this.selectedClass = selectedClass;
	}

	/**
	 * @return the selectedBatch
	 */
	public Batch getSelectedBatch() {
		return this.selectedBatch;
	}

	/**
	 * @param selectedBatch
	 *            the selectedBatch to set
	 */
	public void setSelectedBatch(final Batch selectedBatch) {
		this.selectedBatch = selectedBatch;
	}

	/**
	 * @return the selectedAcademicYear
	 */
	public AcademicYear getSelectedAcademicYear() {
		return this.selectedAcademicYear;
	}

	/**
	 * @param selectedAcademicYear
	 *            the selectedAcademicYear to set
	 */
	public void setSelectedAcademicYear(final AcademicYear selectedAcademicYear) {
		this.selectedAcademicYear = selectedAcademicYear;
	}

	public void loadSectionsByAcademicYearAndClass() {
		try {
			if (this.selectedClass != null && this.selectedAcademicYear != null) {
				this.sectionsByClassAndAcademicYear = this.sectionService.findAllSectionsByKlassIdAndAcademicYearId(this.selectedClass.getId(),
						this.selectedAcademicYear.getId());
			}
		} catch (final ApplicationException e) {
			ViewExceptionHandler.handle(e);
		} catch (final Exception e) {
			ViewExceptionHandler.handle(e);
		}
	}

	/**
	 * @return the admissionStatus
	 */
	public AdmissionStatusConstant getAdmissionStatus() {
		return this.admissionStatus;
	}

	/**
	 * @param admissionStatus
	 *            the admissionStatus to set
	 */
	public void setAdmissionStatus(final AdmissionStatusConstant admissionStatus) {
		this.admissionStatus = admissionStatus;
	}
}