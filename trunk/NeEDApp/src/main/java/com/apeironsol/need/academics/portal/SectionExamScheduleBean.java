package com.apeironsol.need.academics.portal;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.primefaces.event.DateSelectEvent;
import org.springframework.context.annotation.Scope;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.academics.model.Exam;
import com.apeironsol.need.academics.model.SectionExam;
import com.apeironsol.need.academics.model.SectionExamSubject;
import com.apeironsol.need.academics.service.ExamService;
import com.apeironsol.need.academics.service.SectionExamService;
import com.apeironsol.need.academics.service.SectionExamSubjectService;
import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.SectionSubject;
import com.apeironsol.need.core.service.SectionService;
import com.apeironsol.need.core.service.SectionSubjectService;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;

@Named
@Scope(value = "session")
public class SectionExamScheduleBean extends AbstractExamBean {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long				serialVersionUID				= -3254580279620415693L;

	@Resource
	private ExamBean						examBean;

	@Resource
	private ExamService						examService;

	@Resource
	private SectionService					sectionService;

	@Resource
	private SectionExamService				sectionExamService;

	@Resource
	private SectionExamSubjectService		sectionExamSubjectService;

	@Resource
	private SectionSubjectService			sectionSubjectService;

	private Klass							schedulingKlass;

	private Section							schedulingSection;

	private Exam							schedulingExam;

	private AcademicYear					schedulingAcademicYear;

	private boolean							loadActiveSectionsForKlassAndAcademicYearFlag;

	private Collection<Section>				activeSectionsForKlassAndAcademicYear;

	private SectionExam						sectionExam;

	private Collection<SectionExamSubject>	sectionExamSubjects;

	private Collection<SectionSubject>		sessionSubjects;

	private String							sectionExamScheduleWizardStep	= SectionExamScheduleWizard.ALL_SCHEDULED_EXAMS.getKey();

	public enum SectionExamScheduleWizard {

		SCHEDULE_EXAM("schedule_exam"), RESCHEDULE_EXAM("reschedule_exam"), ALL_SCHEDULED_EXAMS("all_scheduled_exams");

		private String	key;

		SectionExamScheduleWizard(final String key) {
			this.key = key;
		}

		public String getKey() {
			return this.key;
		}

		public void setKey(final String key) {
			this.key = key;
		}
	};

	public void reset() {
		this.schedulingAcademicYear = null;
		this.schedulingKlass = null;
		this.schedulingSection = null;
		this.sectionExam = new SectionExam();
	}

	public void viewScheduleExam() {
		this.schedulingAcademicYear = this.sectionExam.getSection().getAcademicYear();
		this.schedulingKlass = this.sectionExam.getSection().getKlass();
		this.schedulingSection = this.sectionExam.getSection();
		this.sectionExamSubjects = this.sectionExamSubjectService.findSectionExamSubjectsBySubjectExamId(this.sectionExam.getId());

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

		loadActiveSectionsForKlassAndAcademicYear();

	}

	public void onSectionSelect() {

		this.sessionSubjects = this.sectionSubjectService.findSectionSubjectsByScetionId(this.schedulingSection.getId());

		populateSectionExamSubjects();

	}

	private void populateSectionExamSubjects() {

		this.sectionExamSubjects = new ArrayList<SectionExamSubject>();

		for (final SectionSubject sectionSubject : this.sessionSubjects) {

			final SectionExamSubject sectionExamSubject = new SectionExamSubject();
			sectionExamSubject.setSectionSubject(sectionSubject);
			this.sectionExamSubjects.add(sectionExamSubject);

		}
	}

	public void onStartDateSelect(final DateSelectEvent event) {

		this.sectionExam.setEndDate(null);

	}

	public void onEndDateSelect(final DateSelectEvent event) {

		populateSectionExamSubjects();
	}

	public void loadActiveSectionsForKlassAndAcademicYear() {
		if (this.loadActiveSectionsForKlassAndAcademicYearFlag) {
			try {

				if ((this.schedulingKlass != null) && (this.schedulingAcademicYear != null)) {
					this.activeSectionsForKlassAndAcademicYear = this.sectionService.findActiveSectionsByKlassIdAndAcademicYearId(this.schedulingKlass.getId(),
							this.schedulingAcademicYear.getId());

					final Collection<Section> sections = new ArrayList<Section>();
					for (final Section section : this.activeSectionsForKlassAndAcademicYear) {

						final SectionExam sectionExam = this.sectionExamService.findSectionExamsBySectionIdAndExamId(section.getId(),
								this.schedulingExam.getId());

						if (sectionExam == null) {
							sections.add(section);
						}

					}

					this.activeSectionsForKlassAndAcademicYear = sections;

				}
				this.loadActiveSectionsForKlassAndAcademicYearFlag = false;
			} catch (final ApplicationException e) {
				ViewExceptionHandler.handle(e);
			} catch (final Exception e) {
				ViewExceptionHandler.handle(e);
			}

		}

	}

	public void scheduleExam() {

		try {

			this.sectionExam.setSection(this.schedulingSection);

			this.sectionExam.setExam(this.schedulingExam);

			this.examService.scheduleExam(this.sectionExam, this.sectionExamSubjects);

			this.schedulingExam = this.examService.findExamById(this.schedulingExam.getId());

			ViewUtil.addMessage("Exam has bean scheduled sucessfully.", FacesMessage.SEVERITY_INFO);

			this.examBean.setLoadSectionExamsFlag(true);

			this.sectionExamScheduleWizardStep = SectionExamScheduleWizard.ALL_SCHEDULED_EXAMS.getKey();

		} catch (final ApplicationException e) {
			ViewExceptionHandler.handle(e);
		} catch (final Exception e) {
			ViewExceptionHandler.handle(e);
		}

	}

	public void unScheduleExam() {
		try {

			this.examService.unScheduleExam(this.sectionExam);

			this.examBean.setLoadSectionExamsFlag(true);
			reset();
		} catch (final ApplicationException e) {
			ViewExceptionHandler.handle(e);
		} catch (final Exception e) {
			ViewExceptionHandler.handle(e);
		}

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

	/**
	 * @return the sectionExamScheduleWizardStep
	 */
	public String getSectionExamScheduleWizardStep() {
		return this.sectionExamScheduleWizardStep;
	}

	/**
	 * @param sectionExamScheduleWizardStep
	 *            the sectionExamScheduleWizardStep to set
	 */
	public void setSectionExamScheduleWizardStep(final String sectionExamScheduleWizardStep) {
		this.sectionExamScheduleWizardStep = sectionExamScheduleWizardStep;
	}

}
