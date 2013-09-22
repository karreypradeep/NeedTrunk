/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.core.portal;

/**
 * View grades class.
 * 
 * @author Pradeep
 */
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.Batch;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Subject;
import com.apeironsol.need.core.service.BatchService;
import com.apeironsol.need.core.service.KlassService;
import com.apeironsol.need.core.service.SubjectService;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.framework.exception.BusinessException;

@Named
@Scope("session")
public class KlassBean extends AbstractKlassBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long	serialVersionUID	= -6678368996429630061L;

	private static final Logger	log					= Logger.getLogger(KlassBean.class);

	@Resource
	private KlassService		klassService;

	@Resource
	private SubjectService		subjectService;

	@Resource
	private BatchService		batchService;

	private Klass				klass;

	private Long				batchId;

	private Collection<Batch>	batches;

	@PostConstruct
	public void init() {
		this.setKlass(new Klass());
	}

	public Klass getKlass() {
		return this.klass;
	}

	public void setKlass(final Klass klass) {
		this.klass = klass;
	}

	public boolean isDisableSubTab() {
		if (this.klass != null && this.klass.getId() != null) {
			return false;
		}
		return true;
	}

	public void newKlass() {
		this.klass = new Klass();
	}

	public void saveKlass() {
		try {
			if (this.klass.getId() == null) {
				KlassTreeBean klassTreeBean = (KlassTreeBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("klassTreeBean");
				klassTreeBean.setLoadKlassTreeFromDatabase(true);
			}
			Branch branch = this.sessionBean.getCurrentBranch();
			this.klass.setBranch(branch);
			this.klass = this.klassService.saveKlass(this.klass);
			this.sessionBean.setCurrentKlass(this.klass);
			this.loadKlassesFlag = true;
			this.sessionBean.setLoadActiveKlassesFlag(true);
			this.sessionBean.loadActiveKlasses();
			ViewUtil.addMessage("Class saved sucessfully.", FacesMessage.SEVERITY_INFO);
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		} catch (Exception e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void removeKlass() {
		try {
			this.klassService.removeKlass(this.klass);
			this.setLoadKlassesFlag(true);
			this.sessionBean.setLoadActiveKlassesFlag(true);
			this.sessionBean.loadActiveKlasses();

		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void viewKlass() {
		this.sessionBean.setCurrentKlass(this.klass);
	}

	public void resetKlass() {
		this.klass = new Klass();
	}

	public boolean isDisableActivate() {
		if (this.klass != null && !this.klass.isActive()) {
			return false;
		}
		return true;
	}

	public void performSanityCheck() {
		boolean sanityCheckPassed = true;

		Collection<Subject> subjects = this.subjectService.findAllSubjectsByKlassId(this.klass.getId());

		Collection<Subject> sections = this.subjectService.findAllSubjectsByKlassId(this.klass.getId());

		if (subjects == null || subjects.isEmpty()) {
			sanityCheckPassed = false;
			ViewUtil.addMessage("Subject are not added for this class.", FacesMessage.SEVERITY_ERROR);
		}

		if (sections == null || sections.isEmpty()) {
			sanityCheckPassed = false;
			ViewUtil.addMessage("Subject are not added for this class.", FacesMessage.SEVERITY_ERROR);
		}

		if (sanityCheckPassed) {
			ViewUtil.addMessage("Sanity check passed on this class.", FacesMessage.SEVERITY_INFO);
		}

	}

	public void activeKlass() {
		try {
			this.klass = this.klassService.activateKlass(this.klass);
			this.loadKlassesFlag = true;
			this.sessionBean.setLoadActiveKlassesFlag(true);
			this.sessionBean.loadActiveKlasses();
			ViewUtil.addMessage("Class has bean activated sucessfully.", FacesMessage.SEVERITY_INFO);
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
			this.klass = this.klassService.findKlassById(this.klass.getId());
		} catch (Throwable e) {
			ViewExceptionHandler.handle(e);
			this.klass = this.klassService.findKlassById(this.klass.getId());
		}

	}

	public void deactivateKlass() {
		try {
			this.klass = this.klassService.deactivateKlass(this.klass);
			this.loadKlassesFlag = true;
			this.sessionBean.setLoadActiveKlassesFlag(true);
			this.sessionBean.loadActiveKlasses();
			ViewUtil.addMessage("Class is set to development sucessfully.", FacesMessage.SEVERITY_INFO);
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
			this.klass = this.klassService.findKlassById(this.klass.getId());
		} catch (Throwable e) {
			log.info(e.getMessage());
			this.klass = this.klassService.findKlassById(this.klass.getId());
		}

	}

	public void loadBatches() {
		if (this.klass != null && this.klass.getId() != null && this.getActiveTabIndex() == 3) {
			try {
				Branch branch = this.sessionBean.getCurrentBranch();
				this.batches = this.batchService.findBatchesByBranchId(branch.getId());
			} catch (BusinessException e) {
				ViewExceptionHandler.handle(e);
			}
		}
	}

	public Collection<Batch> getBatches() {
		return this.batches;
	}

	public void setBatches(final Collection<Batch> batches) {
		this.batches = batches;
	}

	@NotNull(message = "Batch is mandatory.")
	public Long getBatchId() {
		return this.batchId;
	}

	public void setBatchId(final Long batchId) {
		this.batchId = batchId;
	}

}
