/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.portal;

import java.io.Serializable;
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

import com.apeironsol.need.core.model.AcademicYearWeekEnd;
import com.apeironsol.need.core.service.AcademicYearWeekEndService;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.WeekDayConstant;
import com.apeironsol.framework.exception.BusinessException;

/**
 * JSF managed for academic year week end.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class AcademicYearWeekEndBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long				serialVersionUID		= -7135129067999436668L;

	@Resource
	private AcademicYearWeekEndService		academicYearWeekEndService;

	@Resource
	private AcademicYearBean				academicYearBean;

	private Collection<AcademicYearWeekEnd>	academicYearWeekEnds	= new ArrayList<AcademicYearWeekEnd>();

	private AcademicYearWeekEnd				academicYearWeekEnd;

	private Date							scheduleStartDate;

	private Date							scheduleEndDate;

	private List<WeekDayConstant>			selectedWeekDays;

	private ScheduleModel					eventModel;

	/**
	 * Boolean indicating if events are loaded from database.
	 */
	private boolean							eventsLoaded			= false;

	private boolean							weekEndAlreadyDefined;

	public ScheduleModel getEventModel() {
		return this.eventModel;
	}

	private ScheduleEvent	event	= new DefaultScheduleEvent();

	public AcademicYearWeekEndBean() {

	}

	@Override
	public void onTabChange() {
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
		AcademicYearWeekEnd acaYearWeekEnd = this.getAcademicYearWeekEndByEvent(this.event);
		this.academicYearWeekEndService.removeAcademicYearWeekEnd(acaYearWeekEnd);
		Iterator<AcademicYearWeekEnd> ite = this.academicYearWeekEnds.iterator();
		while (ite.hasNext()) {
			AcademicYearWeekEnd object = ite.next();
			if (object.getId().equals(acaYearWeekEnd.getId())) {
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
		AcademicYearWeekEnd acaYearWeekEnd = this.getAcademicYearWeekEndByEvent(event);
		this.scheduleStartDate = acaYearWeekEnd.getWeekEndDate();
		this.scheduleEndDate = acaYearWeekEnd.getWeekEndDate();
		this.selectedWeekDays = new ArrayList<WeekDayConstant>();
		this.selectedWeekDays.add(WeekDayConstant.getWeekDay(DateUtil.getDayOfWeekForSuppliedDate(this.scheduleStartDate)));
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
		this.event = new DefaultScheduleEvent("Week End", selectEvent.getDate(), selectEvent.getDate(), Math.random());
		this.selectedWeekDays = new ArrayList<WeekDayConstant>();
		this.selectedWeekDays.add(WeekDayConstant.getWeekDay(DateUtil.getDayOfWeekForSuppliedDate(this.scheduleStartDate)));
		this.scheduleEventBeingUpdated = false;

		AcademicYearWeekEnd acadYearWeekEnd = this.academicYearWeekEndService.findAcademicYearWeekEndByAcademicYearIdAndWeekEndDate(this.academicYearBean
				.getAcademicYear().getId(), selectEvent.getDate());
		if (acadYearWeekEnd != null) {
			this.weekEndAlreadyDefined = true;
		} else {
			this.weekEndAlreadyDefined = false;
		}
	}

	/**
	 * If date is selected in schedule for entering timetable.
	 * 
	 * @param selectEvent
	 */
	public boolean isWeekEndAlreadyDefined() {
		return this.weekEndAlreadyDefined;
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
		if (this.weekEndAlreadyDefined) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Week end already defined for selected date.",
					"Week end already defined for selected date.");
			this.addMessage(message);
		} else {
			this.academicYearWeekEndService.createAcademicYearWeekEnds(this.scheduleStartDate, this.scheduleEndDate, this.academicYearBean.getAcademicYear(),
					this.getSelectedWeekDays());
		}
	}

	/**
	 * 
	 * @param event
	 * @return
	 */
	private AcademicYearWeekEnd getAcademicYearWeekEndByEvent(final ScheduleEvent event) {
		AcademicYearWeekEnd academicYearWeekEnd = null;
		Long eventId = (Long) event.getData();
		for (AcademicYearWeekEnd acaYearWeekEnd : this.academicYearWeekEnds) {
			if (acaYearWeekEnd.getId().equals(eventId)) {
				academicYearWeekEnd = acaYearWeekEnd;
				break;
			}
		}
		if (academicYearWeekEnd == null) {
			academicYearWeekEnd = this.academicYearWeekEndService.findAcademicYearWeekEndById(eventId);
		}
		return academicYearWeekEnd;
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
				AcademicYearWeekEndBean.this.eventsLoaded = true;
				AcademicYearWeekEndBean.this.academicYearWeekEnds.clear();
				AcademicYearWeekEndBean.this.academicYearWeekEnds = AcademicYearWeekEndBean.this.academicYearWeekEndService
						.findAcademicYearWeekEndsByAcademicYearIdBetweenDates(AcademicYearWeekEndBean.this.academicYearBean.getAcademicYear().getId(), start,
								end);
				for (AcademicYearWeekEnd object : AcademicYearWeekEndBean.this.academicYearWeekEnds) {
					this.addEvent(new DefaultScheduleEvent("Week End", object.getWeekEndDate(), object.getWeekEndDate(), object.getId()));
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

	/**
	 * @return the academicYearWeekEnd
	 */
	public AcademicYearWeekEnd getAcademicYearWeekEnd() {
		return this.academicYearWeekEnd;
	}

	/**
	 * @param academicYearWeekEnd
	 *            the academicYearWeekEnd to set
	 */
	public void setAcademicYearWeekEnd(final AcademicYearWeekEnd academicYearWeekEnd) {
		this.academicYearWeekEnd = academicYearWeekEnd;
	}

	/**
	 * @return the academicYearWeekEndsByAcademisYearId
	 */
	public Collection<AcademicYearWeekEnd> getAcademicYearWeekEnds() {
		return this.academicYearWeekEnds;
	}

	/**
	 * @param academicYearWeekEndsByAcademisYearId
	 *            the academicYearWeekEndsByAcademisYearId to set
	 */
	public void setAcademicYearWeekEnds(final Collection<AcademicYearWeekEnd> academicYearWeekEnds) {
		this.academicYearWeekEnds = academicYearWeekEnds;
	}

}
