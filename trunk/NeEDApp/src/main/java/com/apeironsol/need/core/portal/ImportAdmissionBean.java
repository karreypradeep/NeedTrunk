/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.portal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Batch;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.service.ImportAdmissionService;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.framework.exception.ApplicationException;

@Named
@Scope(value = "session")
public class ImportAdmissionBean extends AbstractPortalBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long			serialVersionUID	= -3066378894334965144L;

	private UploadedFile				file;

	/**
	 * Boolean indicator to load all active batches for the branch.
	 */
	private boolean						loadActiveBatchesForBranchFlag;

	/**
	 * Collection of active batches for the branch.
	 */
	private Collection<Batch>			activeBatchesForBranch;

	private Batch						appliedForBatch;

	private AcademicYear				appliedForAcademicYear;

	private Collection<AcademicYear>	academicYearsForBatch;

	private Klass						applyingForClass;

	private String						importStatusString	= "";

	private boolean						displayStatusTextArea;

	@Resource
	private ImportAdmissionService		importAdmissionService;

	private List<String>				importStatus		= null;

	public UploadedFile getFile() {
		return this.file;
	}

	public void setFile(final UploadedFile file) {
		this.file = file;
	}

	public void importAdmissions(final FileUploadEvent event) {
		try {
			if (this.appliedForBatch == null && this.sessionBean.getCurrentBranchRule().isBatchRequiredIndicator()) {
				ViewUtil.addMessage("Please select batch.", FacesMessage.SEVERITY_ERROR);
				return;
			}
			if (this.appliedForAcademicYear == null) {
				ViewUtil.addMessage("Please select academic year.", FacesMessage.SEVERITY_ERROR);
				return;
			}

			if (this.applyingForClass == null) {
				ViewUtil.addMessage("Please select applying class.", FacesMessage.SEVERITY_ERROR);
				return;
			}

			if (event.getFile() == null || event.getFile().getFileName() == null || event.getFile().getFileName().trim().length() == 0) {
				ViewUtil.addMessage("Please select file to upload.", FacesMessage.SEVERITY_ERROR);
				return;
			}
			this.importStatus = this.importAdmissionService.importAdmissionsFromExcel(event.getFile().getInputstream(), this.sessionBean.getCurrentBranch(),
					this.applyingForClass, this.appliedForBatch, this.appliedForAcademicYear);

			this.file = event.getFile();
			final StringBuffer importStatusSB = new StringBuffer();
			for (final String string : this.importStatus) {
				importStatusSB.append(string).append("\n");
			}
			this.setImportStatusString(importStatusSB.toString());
			this.displayStatusTextArea = true;
			ViewUtil.addMessage("Import process completed successfully.", FacesMessage.SEVERITY_INFO);
		} catch (final Exception ex) {
			ViewExceptionHandler.handle(ex);
		}
	}

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

	public Batch getAppliedForBatch() {
		return this.appliedForBatch;
	}

	public void setAppliedForBatch(final Batch appliedForBatch) {
		this.appliedForBatch = appliedForBatch;
	}

	public Collection<AcademicYear> getAcademicYearsForBatch() {
		return this.academicYearsForBatch;
	}

	public void setAcademicYearsForBatch(final Collection<AcademicYear> academicYearsForBatch) {
		this.academicYearsForBatch = academicYearsForBatch;
	}

	public void loadAcademicYearsForBatch() {
		try {
			this.academicYearsForBatch = this.academicYearService.findAcademicYearsForBatchId(this.appliedForBatch.getId(), this.sessionBean.getCurrentBranch()
					.getId());
		} catch (final ApplicationException e) {
			ViewExceptionHandler.handle(e);
		} catch (final Exception e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public AcademicYear getAppliedForAcademicYear() {
		return this.appliedForAcademicYear;
	}

	public void setAppliedForAcademicYear(final AcademicYear appliedForAcademicYear) {
		this.appliedForAcademicYear = appliedForAcademicYear;
	}

	public Klass getApplyingForClass() {
		return this.applyingForClass;
	}

	public void setApplyingForClass(final Klass applyingForClass) {
		this.applyingForClass = applyingForClass;
	}

	/**
	 * @return the importStatusString
	 */
	public String getImportStatusString() {
		return this.importStatusString;
	}

	/**
	 * @param importStatusString
	 *            the importStatusString to set
	 */
	public void setImportStatusString(final String importStatusString) {
		this.importStatusString = importStatusString;
	}

	/**
	 * @return the displayStatusTextArea
	 */
	public boolean isDisplayStatusTextArea() {
		return this.displayStatusTextArea;
	}

	/**
	 * @param displayStatusTextArea
	 *            the displayStatusTextArea to set
	 */
	public void setDisplayStatusTextArea(final boolean displayStatusTextArea) {
		this.displayStatusTextArea = displayStatusTextArea;
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
			if (this.file != null) {
				final byte[] workbook = this.importAdmissionService.getExcelAterImportOfAdmissions(this.file.getInputstream(), this.importStatus);
				final InputStream stream = new ByteArrayInputStream(workbook);
				streamedContent = new DefaultStreamedContent(stream, "application/xls", "ImportAdmissionsResult.xls");
			}
		} catch (final ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return streamedContent;
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
}