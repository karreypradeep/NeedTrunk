/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.core.portal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.SectionSubject;
import com.apeironsol.need.core.model.SectionTeacher;
import com.apeironsol.need.core.model.SectionTimetable;
import com.apeironsol.need.core.service.SectionSubjectService;
import com.apeironsol.need.core.service.SectionTeacherService;
import com.apeironsol.need.core.service.SectionTimetableService;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.WeekDayConstant;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Managed bean for section timetable.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class SectionTimetableBean extends AbstractTabbedBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long				serialVersionUID	= 8326296517125166209L;

	@Resource
	private SectionTeacherService			sectionTeacherService;

	@Resource
	private SectionSubjectService			sectionSubjectService;

	private Date							scheduleStartDate;

	private Date							scheduleStartTime;

	private Date							scheduleEndDate;

	private Date							scheduleEndTime;

	private List<WeekDayConstant>			selectedWeekDays;

	private ScheduleModel					eventModel;

	private SectionTeacher					sectionTeacher;

	private SectionSubject					sectionSubject;

	private Collection<SectionTeacher>		sectionTeachers;

	private boolean							loadSectionTeachers;

	@Resource
	private SectionBean						sectionBean;

	private Collection<SectionSubject>		sectionSubjects;

	private boolean							loadSectionSubjects;

	@Resource
	private SectionTimetableService			sectionTimetableService;

	private Collection<SectionTimetable>	sectionTimetables	= new ArrayList<SectionTimetable>();

	/**
	 * Boolean indicating if events are loaded from database.
	 */
	private boolean							eventsLoaded		= false;

	public ScheduleModel getEventModel() {
		return this.eventModel;
	}

	private ScheduleEvent	event	= new DefaultScheduleEvent();

	public SectionTimetableBean() {

	}

	@Override
	public void onTabChange() {
		if (this.getActiveTabIndex() == 5) {
			this.loadSectionSubjects = true;
			this.loadSectionTeachers = true;
			this.loadSectionSubjects();
			this.loadSectionTeachers();
		}
	}

	public ScheduleEvent getEvent() {
		return this.event;
	}

	public void setEvent(final ScheduleEvent event) {
		this.event = event;
	}

	/**
	 * Adds or updates event to the database and also to event model. Before
	 * adding the event is validated to see for overlapping of time for section
	 * and also teacher.
	 * 
	 * @param actionEvent
	 *            action event.
	 */
	public void addEvent(final ActionEvent actionEvent) {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		try {
			if (this.event.getId() == null) {
				this.saveEvents();
			} else {
				this.updateEvent(this.event);
			}
			requestContext.addCallbackParam("isValid", true);
		} catch (BusinessException e) {
			requestContext.addCallbackParam("isValid", false);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
			this.addMessage(message);
		}

	}

	private void addMessage(final FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	/**
	 * Deletes event from the database and also from event model.
	 * 
	 * @param actionEvent
	 *            action event.
	 */
	public void deleteEvent(final ActionEvent actionEvent) {
		SectionTimetable sectionTimetable = this.getSectionTimetableByEvent(this.event);
		this.sectionTimetableService.removeSectionTimetable(sectionTimetable);
		Iterator<SectionTimetable> ite = this.sectionTimetables.iterator();
		while (ite.hasNext()) {
			SectionTimetable object = ite.next();
			if (object.getId().equals(sectionTimetable.getId())) {
				ite.remove();
				break;
			}
		}
		this.eventModel.deleteEvent(this.event);
	}

	/**
	 * This method is called when already existing event is selected in UI.
	 * 
	 * @param selectEvent
	 *            selected event.
	 */
	public void onEventSelect(final ScheduleEntrySelectEvent selectEvent) {
		ScheduleEvent updateEvent = selectEvent.getScheduleEvent();
		this.event = new DefaultScheduleEvent();
		this.copyEventData((DefaultScheduleEvent) updateEvent, (DefaultScheduleEvent) this.event);
		this.setEventDialogParameters(this.event);
		this.scheduleEventBeingUpdated = true;
	}

	/**
	 * Sets all the properties used in schedule dialog.
	 * 
	 * @param event
	 *            event selected in UI.
	 */
	private void setEventDialogParameters(final ScheduleEvent event) {
		SectionTimetable sectionTimetable = this.getSectionTimetableByEvent(event);
		this.scheduleStartDate = sectionTimetable.getScheduleDate();
		this.scheduleEndDate = sectionTimetable.getScheduleDate();
		this.scheduleStartTime = sectionTimetable.getStartTime();
		this.scheduleEndTime = sectionTimetable.getEndTime();
		this.selectedWeekDays = new ArrayList<WeekDayConstant>();
		this.selectedWeekDays.add(WeekDayConstant.getWeekDay(DateUtil
				.getDayOfWeekForSuppliedDate(this.scheduleStartDate)));
		this.sectionTeacher = sectionTimetable.getSectionTeacher();
		this.sectionSubject = sectionTimetable.getSectionSubject();
	}

	/**
	 * Copies event date from source event to destination event.
	 * 
	 * @param fromEvent
	 *            from event.
	 * @param toEvent
	 *            to event.
	 */
	private void copyEventData(final DefaultScheduleEvent fromEvent, final DefaultScheduleEvent toEvent) {
		toEvent.setAllDay(fromEvent.isAllDay());
		toEvent.setData(fromEvent.getData());
		toEvent.setEditable(fromEvent.isEditable());
		toEvent.setEndDate(fromEvent.getEndDate());
		toEvent.setId(fromEvent.getId());
		toEvent.setStartDate(fromEvent.getStartDate());
		toEvent.setStyleClass(fromEvent.getStyleClass());
		toEvent.setTitle(fromEvent.getTitle());
	}

	/**
	 * If date is selected in schedule for entering timetable.
	 * 
	 * @param selectEvent
	 */
	public void onDateSelect(final DateSelectEvent selectEvent) {
		this.scheduleStartDate = selectEvent.getDate();
		this.scheduleEndDate = selectEvent.getDate();
		this.scheduleStartTime = null;
		this.scheduleEndTime = null;
		this.event = new DefaultScheduleEvent("", selectEvent.getDate(), selectEvent.getDate(), Math.random());
		this.selectedWeekDays = new ArrayList<WeekDayConstant>();
		this.selectedWeekDays.add(WeekDayConstant.getWeekDay(DateUtil
				.getDayOfWeekForSuppliedDate(this.scheduleStartDate)));
		this.scheduleEventBeingUpdated = false;
	}

	/**
	 * Loads section teachers from database.
	 */
	public void loadSectionTeachers() {
		if (this.loadSectionTeachers) {
			this.sectionTeachers = this.sectionTeacherService.findSectionTeachersByScetionId(this.sectionBean
					.getSection().getId());
			this.loadSectionTeachers = false;
		}
	}

	/**
	 * @return the sectionTeachers
	 */
	public Collection<SectionTeacher> getSectionTeachers() {
		return this.sectionTeachers;
	}

	/**
	 * @param sectionTeachers
	 *            the sectionTeachers to set
	 */
	public void setSectionTeachers(final Collection<SectionTeacher> sectionTeachers) {
		this.sectionTeachers = sectionTeachers;
	}

	/**
	 * Loads section subjects from database.
	 */
	public void loadSectionSubjects() {
		if (this.loadSectionSubjects) {
			this.sectionSubjects = this.sectionSubjectService.findSectionSubjectsByScetionId(this.sectionBean
					.getSection().getId());
			this.loadSectionSubjects = false;
		}
	}

	/**
	 * @return the sectionSubjects
	 */
	public Collection<SectionSubject> getSectionSubjects() {
		return this.sectionSubjects;
	}

	/**
	 * @param sectionSubjects
	 *            the sectionSubjects to set
	 */
	public void setSectionSubjects(final Collection<SectionSubject> sectionSubjects) {
		this.sectionSubjects = sectionSubjects;
	}

	/**
	 * @return the sectionTeacher
	 */
	public SectionTeacher getSectionTeacher() {
		return this.sectionTeacher;
	}

	/**
	 * @param sectionTeacher
	 *            the sectionTeacher to set
	 */
	public void setSectionTeacher(final SectionTeacher sectionTeacher) {
		this.sectionTeacher = sectionTeacher;
	}

	/**
	 * @return the sectionSubject
	 */
	public SectionSubject getSectionSubject() {
		return this.sectionSubject;
	}

	/**
	 * @param sectionSubject
	 *            the sectionSubject to set
	 */
	public void setSectionSubject(final SectionSubject sectionSubject) {
		this.sectionSubject = sectionSubject;
	}

	/**
	 * @return the startDate
	 */
	public Date getScheduleStartDate() {
		return this.scheduleStartDate;
	}

	/**
	 * @param scheduleStartDate
	 *            the startDate to set
	 */
	public void setScheduleStartDate(final Date scheduleStartDate) {
		this.scheduleStartDate = scheduleStartDate;
	}

	/**
	 * @return the scheduleStartTime
	 */
	public Date getScheduleStartTime() {
		return this.scheduleStartTime;
	}

	/**
	 * @param scheduleStartTime
	 *            the scheduleStartTime to set
	 */
	public void setScheduleStartTime(final Date scheduleStartTime) {
		this.scheduleStartTime = scheduleStartTime;
	}

	/**
	 * @return the schedueEndDate
	 */
	public Date getScheduleEndDate() {
		return this.scheduleEndDate;
	}

	/**
	 * @param schedueEndDate
	 *            the schedueEndDate to set
	 */
	public void setScheduleEndDate(final Date schedueEndDate) {
		this.scheduleEndDate = schedueEndDate;
	}

	/**
	 * @return the schedueEndTime
	 */
	public Date getScheduleEndTime() {
		return this.scheduleEndTime;
	}

	/**
	 * @param schedueEndTime
	 *            the schedueEndTime to set
	 */
	public void setScheduleEndTime(final Date schedueEndTime) {
		this.scheduleEndTime = schedueEndTime;
	}

	public List<WeekDayConstant> getSelectedWeekDays() {
		return this.selectedWeekDays;
	}

	public void setSelectedWeekDays(final List<WeekDayConstant> selectedWeekDays) {
		this.selectedWeekDays = selectedWeekDays;
	}

	/**
	 * Saves events to database and also updates event model with created
	 * events.
	 */
	private void saveEvents() {
		SectionTimetable sectionTimetable = new SectionTimetable();
		sectionTimetable.setEndTime(this.scheduleEndTime);
		sectionTimetable.setSectionSubject(this.getSectionSubject());
		sectionTimetable.setSectionTeacher(this.getSectionTeacher());
		sectionTimetable.setStartTime(this.scheduleStartTime);
		this.sectionTimetables = this.sectionTimetableService.createSectionTimetables(this.scheduleStartDate,
				this.scheduleEndDate, sectionTimetable, this.getSelectedWeekDays());
	}

	/**
	 * Updates selected event to database.
	 * 
	 * @param updateEvent
	 *            updated event.
	 */
	private void updateEvent(final ScheduleEvent updateEvent) {
		SectionTimetable sectionTimetable = this.getSectionTimetableByEvent(updateEvent);
		sectionTimetable.setStartTime(this.scheduleStartTime);
		sectionTimetable.setEndTime(this.scheduleEndTime);
		sectionTimetable = this.sectionTimetableService.saveSectionTimetable(sectionTimetable);
		Iterator<SectionTimetable> ite = this.sectionTimetables.iterator();
		while (ite.hasNext()) {
			SectionTimetable object = ite.next();
			if (object.getId().equals(sectionTimetable.getId())) {
				ite.remove();
				break;
			}
		}
		this.sectionTimetables.add(sectionTimetable);
		this.eventModel.updateEvent(updateEvent);
	}

	/**
	 * 
	 * @param event
	 * @return
	 */
	private SectionTimetable getSectionTimetableByEvent(final ScheduleEvent event) {
		SectionTimetable sectionTimetable = null;
		Long eventId = (Long) event.getData();
		for (SectionTimetable st : this.sectionTimetables) {
			if (st.getId().equals(eventId)) {
				sectionTimetable = st;
				break;
			}
		}
		if (sectionTimetable == null) {
			sectionTimetable = this.sectionTimetableService.findSectionTimetableById(eventId);
		}
		return sectionTimetable;
	}

	/**
	 * Loads scheduled time table for the current month being displayed in
	 * schedule component.
	 */
	@SuppressWarnings("serial")
	public void loadEventsForMonth() {
		ScheduleModel oldEventModel = this.eventModel;
		this.eventsLoaded = false;
		this.eventModel = new LazyScheduleModel() {

			@Override
			public void loadEvents(final Date start, final Date end) {
				SectionTimetableBean.this.eventsLoaded = true;
				SectionTimetableBean.this.sectionTimetables.clear();
				SectionTimetableBean.this.sectionTimetables = SectionTimetableBean.this.sectionTimetableService
						.findSectionTimetablesByScetionIdBetweenDates(SectionTimetableBean.this.sectionBean
								.getSection().getId(), start, end);
				for (SectionTimetable object : SectionTimetableBean.this.sectionTimetables) {
					this.addEvent(new DefaultScheduleEvent("-"
							+ new SimpleDateFormat("HH:mm").format(object.getEndTime()) + " "
							+ object.getSectionSubject().getSubject().getName() + "/"
							+ object.getSectionTeacher().getEmployee().getDisplayName(), DateUtil.changeTimeOfDate(
							object.getScheduleDate(), object.getStartTime()), DateUtil.changeTimeOfDate(
							object.getScheduleDate(), object.getEndTime()), object.getId()));
				}
			}
		};

		if (!this.eventsLoaded && oldEventModel != null) {
			this.eventModel = oldEventModel;
		}

	}

	private boolean	scheduleEventBeingUpdated	= false;

	public boolean getScheduleEventBeingUpdated() {
		return this.scheduleEventBeingUpdated;
	}

	public void getScheduleEventBeingUpdated(final boolean scheduleEventBeingUpdated) {
		this.scheduleEventBeingUpdated = scheduleEventBeingUpdated;
	}

}
