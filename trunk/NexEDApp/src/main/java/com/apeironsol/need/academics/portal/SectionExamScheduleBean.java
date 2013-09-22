package com.apeironsol.need.academics.portal;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.primefaces.event.DateSelectEvent;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.academics.model.Exam;
import com.apeironsol.need.academics.model.SectionExam;
import com.apeironsol.need.academics.model.SectionExamSubject;
import com.apeironsol.need.academics.service.ExamService;
import com.apeironsol.need.academics.service.SectionExamService;
import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.SectionSubject;
import com.apeironsol.need.core.service.SectionService;
import com.apeironsol.need.core.service.SectionSubjectService;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.framework.exception.InvalidArgumentException;

@Named
@Scope(value = "session")
public class SectionExamScheduleBean extends AbstractExamBean {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long				serialVersionUID	= -3254580279620415693L;

	@Resource
	private ExamBean						examBean;

	@Resource
	private ExamService						examService;

	@Resource
	private SectionService					sectionService;

	@Resource
	private SectionExamService				sectionExamService;

	@Resource
	private SectionSubjectService			sectionSubjectService;

	private boolean							scheduleExamFlag;

	private Klass							schedulingKlass;

	private Section							schedulingSection;

	private Exam							schedulingExam;

	private AcademicYear					schedulingAcademicYear;

	private boolean							loadActiveSectionsForKlassAndAcademicYearFlag;

	private Collection<Section>				activeSectionsForKlassAndAcademicYear;

	private SectionExam						sectionExam;

	private Collection<SectionExamSubject>	sectionExamSubjects;

	private Collection<SectionSubject>		sessionSubjects;

	public void reset() {
		this.schedulingAcademicYear = null;
		this.schedulingKlass = null;
		this.schedulingSection = null;
		this.sectionExam = new SectionExam();
	}

	public void onAcademicYearSelect() {

		this.loadActiveSectionsForKlassAndAcademicYearFlag = true;

		this.schedulingKlass = null;

		this.schedulingSection = null;

		this.sectionExam.setStartDate(null);

		this.sectionExam.setEndDate(null);

		this.sectionExamSubjects = new ArrayList<SectionExamSubject>();
	}

	public void onKlassesSelect() {

		this.loadActiveSectionsForKlassAndAcademicYearFlag = true;

		this.schedulingSection = null;

		this.sectionExamSubjects = new ArrayList<SectionExamSubject>();

		this.loadActiveSectionsForKlassAndAcademicYear();

	}

	public void onSectionSelect() {

		this.sessionSubjects = this.sectionSubjectService.findSectionSubjectsByScetionId(this.schedulingSection.getId());

		this.populateSectionExamSubjects();

	}

	private void populateSectionExamSubjects() {

		this.sectionExamSubjects = new ArrayList<SectionExamSubject>();

		for (SectionSubject sectionSubject : this.sessionSubjects) {

			SectionExamSubject sectionExamSubject = new SectionExamSubject();
			sectionExamSubject.setSectionSubject(sectionSubject);
			this.sectionExamSubjects.add(sectionExamSubject);

		}
	}

	public void onStartDateSelect(final DateSelectEvent event) {

		this.sectionExam.setEndDate(null);

	}

	public void onEndDateSelect(final DateSelectEvent event) {

		this.populateSectionExamSubjects();
	}

	public void loadActiveSectionsForKlassAndAcademicYear() {
		if (this.loadActiveSectionsForKlassAndAcademicYearFlag) {
			try {

				if (this.schedulingKlass != null && this.schedulingAcademicYear != null) {
					this.activeSectionsForKlassAndAcademicYear = this.sectionService.findActiveSectionsByKlassIdAndAcademicYearId(this.schedulingKlass.getId(),
							this.schedulingAcademicYear.getId());

					Collection<Section> sections = new ArrayList<Section>();
					for (Section section : this.activeSectionsForKlassAndAcademicYear) {

						SectionExam sectionExam = this.sectionExamService.findSectionExamsBySectionIdAndExamId(section.getId(), this.schedulingExam.getId());

						if (sectionExam == null) {
							sections.add(section);
						}

					}

					this.activeSectionsForKlassAndAcademicYear = sections;

				}
				this.loadActiveSectionsForKlassAndAcademicYearFlag = false;
			} catch (ApplicationException e) {
				ViewExceptionHandler.handle(e);
			} catch (Exception e) {
				ViewExceptionHandler.handle(e);
			}

		}

	}

	public void scheduleExam() {

		try {

			this.sectionExam.setSection(this.schedulingSection);

			this.sectionExam.setExam(this.schedulingExam);

			Collection<SectionExamSubject> sectionExamSubjects = new ArrayList<SectionExamSubject>();

			for (SectionExamSubject sectionExamSubject : this.sectionExamSubjects) {

				if (sectionExamSubject.getScheduledDate() != null || sectionExamSubject.getStartTime() != null || sectionExamSubject.getEndTime() != null
						|| sectionExamSubject.getPassMarks() != null || sectionExamSubject.getMaximumMarks() != null) {

					this.validateSectionExamSubject(sectionExamSubject);

					sectionExamSubjects.add(sectionExamSubject);
				}

			}

			if (sectionExamSubjects.isEmpty()) {

				ViewUtil.addMessage("Scheduling details are requried for atleast one subject.", FacesMessage.SEVERITY_ERROR);
				return;

			}

			this.examService.scheduleExam(this.sectionExam, sectionExamSubjects);

			this.schedulingExam = this.examService.findExamById(this.schedulingExam.getId());

			ViewUtil.addMessage("Exam has bean scheduled sucessfully.", FacesMessage.SEVERITY_INFO);

			this.examBean.setLoadSectionExamsFlag(true);

			this.scheduleExamFlag = false;

		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		} catch (Exception e) {
			ViewExceptionHandler.handle(e);
		}

	}

	private void validateSectionExamSubject(final SectionExamSubject sectionExamSubject) {
		if (sectionExamSubject.getScheduledDate() == null) {
			throw new InvalidArgumentException("Exam scheduled date should not be empty.");
		}

		if (sectionExamSubject.getStartTime() == null) {
			throw new InvalidArgumentException("Exam start time should not be empty.");
		}

		if (sectionExamSubject.getEndTime() == null) {
			throw new InvalidArgumentException("Exam end time should not be empty.");
		}

		if (sectionExamSubject.getEndTime().before(sectionExamSubject.getStartTime())) {
			throw new InvalidArgumentException("Exam start time  should be before end time.");
		}

		if (sectionExamSubject.getPassMarks() == null) {
			throw new InvalidArgumentException("Exam pass marks should not be empty.");
		}

		if (sectionExamSubject.getMaximumMarks() == null) {
			throw new InvalidArgumentException("Exam maximum marks  should not be empty.");
		}

		if (sectionExamSubject.getMaximumMarks() < sectionExamSubject.getPassMarks()) {
			throw new InvalidArgumentException("Exam pass marks should not be grater then maximum marks.");
		}
	}

	public void unScheduleExam() {
		try {

			this.examService.unScheduleExam(this.sectionExam);

			this.examBean.setLoadSectionExamsFlag(true);
			this.reset();
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		} catch (Exception e) {
			ViewExceptionHandler.handle(e);
		}

	}

	public boolean isScheduleExamFlag() {
		return this.scheduleExamFlag;
	}

	public void setScheduleExamFlag(final boolean scheduleExamFlag) {
		this.scheduleExamFlag = scheduleExamFlag;
	}

	public Klass getSchedulingKlass() {
		return this.schedulingKlass;
	}

	public void setSchedulingKlass(final Klass schedulingKlass) {
		this.schedulingKlass = schedulingKlass;
	}

	public Section getSchedulingSection() {
		return this.schedulingSection;
	}

	public void setSchedulingSection(final Section schedulingSection) {
		this.schedulingSection = schedulingSection;
	}

	public Exam getSchedulingExam() {
		return this.schedulingExam;
	}

	public void setSchedulingExam(final Exam schedulingExam) {
		this.schedulingExam = schedulingExam;
	}

	public AcademicYear getSchedulingAcademicYear() {
		return this.schedulingAcademicYear;
	}

	public void setSchedulingAcademicYear(final AcademicYear schedulingAcademicYear) {
		this.schedulingAcademicYear = schedulingAcademicYear;
	}

	public boolean isLoadActiveSectionsForKlassAndAcademicYearFlag() {
		return this.loadActiveSectionsForKlassAndAcademicYearFlag;
	}

	public void setLoadActiveSectionsForKlassAndAcademicYearFlag(final boolean loadActiveSectionsForKlassAndAcademicYearFlag) {
		this.loadActiveSectionsForKlassAndAcademicYearFlag = loadActiveSectionsForKlassAndAcademicYearFlag;
	}

	public Collection<Section> getActiveSectionsForKlassAndAcademicYear() {
		return this.activeSectionsForKlassAndAcademicYear;
	}

	public void setActiveSectionsForKlassAndAcademicYear(final Collection<Section> activeSectionsForKlassAndAcademicYear) {
		this.activeSectionsForKlassAndAcademicYear = activeSectionsForKlassAndAcademicYear;
	}

	public SectionExam getSectionExam() {
		return this.sectionExam;
	}

	public void setSectionExam(final SectionExam sectionExam) {
		this.sectionExam = sectionExam;
	}

	public Collection<SectionExamSubject> getSectionExamSubjects() {
		return this.sectionExamSubjects;
	}

	public void setSectionExamSubjects(final Collection<SectionExamSubject> sectionExamSubjects) {
		this.sectionExamSubjects = sectionExamSubjects;
	}

	public Collection<SectionSubject> getSessionSubjects() {
		return this.sessionSubjects;
	}

	public void setSessionSubjects(final Collection<SectionSubject> sessionSubjects) {
		this.sessionSubjects = sessionSubjects;
	}

}
