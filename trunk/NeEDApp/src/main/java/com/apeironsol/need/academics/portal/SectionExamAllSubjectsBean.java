/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.academics.portal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
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

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.academics.dataobject.StudentExamAllSubjectsDO;
import com.apeironsol.need.academics.service.StudentExamSubjectService;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.core.service.ImportSectionExamMarksService;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;

/**
 * JSF managed for financial income.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class SectionExamAllSubjectsBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long						serialVersionUID				= -2766565870083602301L;

	@Resource
	private SectionExamsBean						sectionExamsBean;

	@Resource
	private StudentExamSubjectService				studentExamSubjectService;

	private Collection<StudentExamAllSubjectsDO>	studentExamAllSubjectsDOs		= new ArrayList<StudentExamAllSubjectsDO>();

	private boolean									loadStudentExamAllSubjectsDOs	= false;

	private UploadedFile							file;

	@Resource
	private ImportSectionExamMarksService			importSectionExamMarksService;

	private List<String>							importStatus					= null;

	private String									importStatusString				= "";

	private boolean									displayStatusTextArea;

	/**
	 * @return the importStatus
	 */
	public List<String> getImportStatus() {
		return this.importStatus;
	}

	/**
	 * @param importStatus
	 *            the importStatus to set
	 */
	public void setImportStatus(final List<String> importStatus) {
		this.importStatus = importStatus;
	}

	@Override
	public void onTabChange() {

	}

	/**
	 * @return the studentExamAllSubjectsDOs
	 */
	public Collection<StudentExamAllSubjectsDO> getStudentExamAllSubjectsDOs() {
		return this.studentExamAllSubjectsDOs;
	}

	/**
	 * @return the loadStudentExamAllSubjectsDOs
	 */
	public boolean isLoadStudentExamAllSubjectsDOs() {
		return this.loadStudentExamAllSubjectsDOs;
	}

	/**
	 * @param loadStudentExamAllSubjectsDOs
	 *            the loadStudentExamAllSubjectsDOs to set
	 */
	public void setLoadStudentExamAllSubjectsDOs(final boolean loadStudentExamAllSubjectsDOs) {
		this.loadStudentExamAllSubjectsDOs = loadStudentExamAllSubjectsDOs;
	}

	public void loadStudentExamAllSubjectsDOsFromDB() {
		if (this.isLoadStudentExamAllSubjectsDOs()) {
			this.studentExamAllSubjectsDOs = this.studentExamSubjectService.findStudentExamAllSubjectsDOsBySubjectExamId(this.sectionExamsBean.getSectionExam()
					.getId());
			this.loadStudentExamAllSubjectsDOs = false;
		}
	}

	public void submitMarksObtained() {
		try {
			this.studentExamAllSubjectsDOs = this.studentExamSubjectService.saveStudentExamAllSubjectsDOs(this.studentExamAllSubjectsDOs);
			ViewUtil.addMessage("Markes saved successfully.", FacesMessage.SEVERITY_INFO);
		} catch (final ApplicationException applicationException) {
			ViewExceptionHandler.handle(applicationException);
		}
	}

	public StreamedContent getExcelFormatfile() {
		StreamedContent excelFormatfile = null;
		try {
			final InputStream stream = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext())
					.getResourceAsStream("/importdocs/ImportExamMarks.xls");
			final byte[] workbook = this.importSectionExamMarksService.getSectionMarksExcelForImport(stream, this.sectionExamsBean.getSectionExam());
			final InputStream inputStream = new ByteArrayInputStream(workbook);
			excelFormatfile = new DefaultStreamedContent(inputStream, "application/xls", this.sectionExamsBean.getSectionExam().getSection().getName() + "-"
					+ this.sectionExamsBean.getSectionExam().getExam().getName() + "-" + "ImportExamMarks.xls");
		} catch (final ApplicationException e) {
			ViewUtil.addMessage(e.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		return excelFormatfile;
	}

	public StreamedContent getExcelAfterImport() {
		StreamedContent streamedContent = null;
		try {
			if (this.file != null) {
				final byte[] workbook = this.importSectionExamMarksService.getExcelAterImportOfSectionMarks(this.file.getInputstream(),
						this.sectionExamsBean.getSectionExam(), this.importStatus);
				final InputStream stream = new ByteArrayInputStream(workbook);
				streamedContent = new DefaultStreamedContent(stream, "application/xls", this.sectionExamsBean.getSectionExam().getSection().getName() + "-"
						+ this.sectionExamsBean.getSectionExam().getExam().getName() + "-" + "ImportExamMarksResults.xls");
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

	public void importMarks(final FileUploadEvent event) {
		try {

			if ((event.getFile() == null) || (event.getFile().getFileName() == null) || (event.getFile().getFileName().trim().length() == 0)) {
				ViewUtil.addMessage("Please select file to upload.", FacesMessage.SEVERITY_ERROR);
				return;
			}
			this.importStatus = this.importSectionExamMarksService.importSectionMarksFromExcel(event.getFile().getInputstream(),
					this.sectionExamsBean.getSectionExam());

			this.file = event.getFile();
			final StringBuffer importStatusSB = new StringBuffer();
			for (final String string : this.importStatus) {
				importStatusSB.append(string).append("\n");
			}
			this.setImportStatusString(importStatusSB.toString());
			this.displayStatusTextArea = true;
			this.loadStudentExamAllSubjectsDOs = true;
			this.loadStudentExamAllSubjectsDOsFromDB();
			ViewUtil.addMessage("Import process completed successfully.", FacesMessage.SEVERITY_INFO);
		} catch (final Exception ex) {
			ViewExceptionHandler.handle(ex);
		}
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
}
