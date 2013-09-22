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
import java.io.Serializable;
import java.util.Collection;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.Batch;
import com.apeironsol.need.core.service.BatchService;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.framework.exception.BusinessException;

@Named
@Scope("session")
public class BatchBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long	serialVersionUID	= 2923712578377815552L;

	private static final Logger	log					= Logger.getLogger(BatchBean.class);

	@Resource
	BatchService				batchService;

	private Batch				batch;

	private Collection<Batch>	batches;

	@Resource
	private SessionBean			sessionBean;

	private boolean				loadBatchesIndicator;

	public Batch getBatch() {
		return this.batch;
	}

	public void setBatch(final Batch batch) {
		this.batch = batch;
	}

	public Collection<Batch> getBatches() {
		return this.batches;
	}

	public boolean isDisableSubTab() {
		if (this.batch != null && this.batch.getId() != null) {
			return false;
		}
		return true;
	}

	public String newBatch() {
		this.batch = new Batch();
		this.batch.setBranch(this.sessionBean.getCurrentBranch());
		return null;
	}

	public void saveBatch() {
		try {
			if (this.batch.getBranch() == null) {
				this.batch.setBranch(this.sessionBean.getCurrentBranch());
			}
			this.batch = this.batchService.saveBatch(this.batch);
			this.loadBatchesIndicator = true;
			ViewUtil.addMessage("Batch saved sucessfully.", FacesMessage.SEVERITY_INFO);
		} catch (ApplicationException ex) {
			log.info(ex.getMessage());
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}

	public String removeBatch() {
		try {
			this.batchService.removeBatch(this.batch);
			ViewUtil.addMessage("Batch removed sucessfully.", FacesMessage.SEVERITY_INFO);
			this.loadBatchesIndicator = true;
		} catch (ApplicationException ex) {
			log.info(ex.getMessage());
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		return null;

	}

	public String resetBatch() {
		this.batch = new Batch();
		this.batch.setBranch(this.sessionBean.getCurrentBranch());
		return null;
	}

	public void loadBatches() {
		try {
			if (this.loadBatchesIndicator) {
				this.batches = this.batchService.findBatchesByBranchId(this.sessionBean.getCurrentBranch().getId());
				this.loadBatchesIndicator = false;
			}
		} catch (ApplicationException ex) {
			log.info(ex.getMessage());
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}

	@Override
	public void onTabChange() {

	}

	/**
	 * @return the loadBatchesIndicator
	 */
	public boolean isLoadBatchesIndicator() {
		return this.loadBatchesIndicator;
	}

	/**
	 * @param loadBatchesIndicator
	 *            the loadBatchesIndicator to set
	 */
	public void setLoadBatchesIndicator(final boolean loadBatchesIndicator) {
		this.loadBatchesIndicator = loadBatchesIndicator;
	}

	public void lockUnlockBatch() {
		try {
			this.batch.setLocked(this.batch.isLocked() ? false : true);
			this.batch = this.batchService.saveBatch(this.batch);
			if (this.batch.isLocked()) {
				ViewUtil.addMessage("Batch locked sucessfully.", FacesMessage.SEVERITY_INFO);
			} else {
				ViewUtil.addMessage("Batch unlocked sucessfully.", FacesMessage.SEVERITY_INFO);
			}
		} catch (BusinessException e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public boolean isDisableBatch() {
		return this.batch != null && this.batch.getId() != null;
	}
}
