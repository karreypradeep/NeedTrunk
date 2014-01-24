/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.academics.portal;

/**
 * View courses class.
 * 
 * @author Pradeep
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.academics.model.GradeSystem;
import com.apeironsol.need.academics.model.GradeSystemRange;
import com.apeironsol.need.academics.service.GradeSystemRangeService;
import com.apeironsol.need.academics.service.GradeSystemService;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.util.comparator.GradeSystemRangeComparator;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;

@Named
@Scope("session")
public class GradeSystemBean extends AbstractTabbedBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long			serialVersionUID	= -3568016756234711151L;

	private GradeSystem					gradeSystem;

	private Collection<GradeSystem>		gradeSystems;

	private boolean						loadGradeSystemsFlag;

	@Resource
	private GradeSystemService			gradeSystemService;

	@Resource
	private GradeSystemRangeService		gradeSystemRangeService;

	private ArrayList<GradeSystemRange>	gradeSystemRanges;

	private GradeSystemRange			currentGradeSystemRange;

	@Override
	public void onTabChange() {
		// TODO Auto-generated method stub

	}

	public void newGradeSystem() {
		this.gradeSystem = new GradeSystem();
		this.gradeSystem.setBranch(this.sessionBean.getCurrentBranch());
		if (this.gradeSystemRanges == null) {
			this.gradeSystemRanges = new ArrayList<GradeSystemRange>();
		} else {
			this.gradeSystemRanges.clear();
		}
		GradeSystemRange newGradeSystemRange = new GradeSystemRange();
		newGradeSystemRange.setMinimumRange(0);
		this.gradeSystemRanges.add(newGradeSystemRange);
	}

	public void saveGradeSystem() {
		try {
			this.gradeSystem.setBranch(this.sessionBean.getCurrentBranch());
			this.gradeSystem.setGradeSystemRange(this.gradeSystemRanges);
			this.gradeSystem = this.gradeSystemService.saveGradeSystem(this.gradeSystem);
			if (this.gradeSystemRanges == null) {
				this.gradeSystemRanges = new ArrayList<GradeSystemRange>();
			} else {
				this.gradeSystemRanges.clear();
			}
			this.gradeSystemRanges.addAll(this.gradeSystemRangeService.findAllGradingSystemRangesByGradeSystem(this.gradeSystem.getId()));
			ViewUtil.addMessage("Grading system saved sucessfully.", FacesMessage.SEVERITY_INFO);
			this.setLoadGradeSystemsFlag(true);
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void addGradeSystemRange() {
		try {
			Collections.sort(this.gradeSystemRanges, new GradeSystemRangeComparator(GradeSystemRangeComparator.Order.MIN_PERCENTAGE));
			int maxValue = 0;
			for (GradeSystemRange gradeSystemRange : this.gradeSystemRanges) {
				maxValue = gradeSystemRange.getMaximumRange();
			}
			GradeSystemRange newGradeSystemRange = new GradeSystemRange();
			newGradeSystemRange.setMinimumRange(maxValue + 1);
			this.gradeSystemRanges.add(newGradeSystemRange);
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void removeGradeSystemRange() {
		try {
			Iterator<GradeSystemRange> iterator = this.gradeSystemRanges.iterator();
			while (iterator.hasNext()) {
				GradeSystemRange gradeSystemRange = iterator.next();
				if (gradeSystemRange == this.currentGradeSystemRange) {
					iterator.remove();
					break;
				}
			}
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void viewGradeSystem() {
		if (this.gradeSystemRanges == null) {
			this.gradeSystemRanges = new ArrayList<GradeSystemRange>();
		} else {
			this.gradeSystemRanges.clear();
		}
		this.gradeSystemRanges.addAll(this.gradeSystemRangeService.findAllGradingSystemRangesByGradeSystem(this.gradeSystem.getId()));
	}

	public void removeGradeSystem() {
		try {
			this.gradeSystem.setBranch(this.sessionBean.getCurrentBranch());
			this.gradeSystemService.removeGradeSystem(this.gradeSystem);
			this.gradeSystem = new GradeSystem();
			if (this.gradeSystemRanges == null) {
				this.gradeSystemRanges = new ArrayList<GradeSystemRange>();
			} else {
				this.gradeSystemRanges.clear();
			}
			this.setLoadGradeSystemsFlag(true);
			ViewUtil.addMessage("Hostel room removed sucessfully.", FacesMessage.SEVERITY_INFO);
		} catch (BusinessException e) {
			ViewExceptionHandler.handle(e);
		} catch (Throwable e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void resetGradeSystem() {
		this.gradeSystem = new GradeSystem();
	}

	public void loadGradeSystems() {
		if (this.isLoadGradeSystemsFlag()) {
			this.gradeSystems = this.gradeSystemService.findAllGradeSystems(this.sessionBean.getCurrentBranch().getId());
			this.setLoadGradeSystemsFlag(false);
		}
	}

	/**
	 * @return the gradeSystem
	 */
	public GradeSystem getGradeSystem() {
		return this.gradeSystem;
	}

	/**
	 * @param gradeSystem
	 *            the gradeSystem to set
	 */
	public void setGradeSystem(final GradeSystem gradeSystem) {
		this.gradeSystem = gradeSystem;
	}

	/**
	 * @return the gradeSystems
	 */
	public Collection<GradeSystem> getGradeSystems() {
		return this.gradeSystems;
	}

	/**
	 * @param gradeSystems
	 *            the gradeSystems to set
	 */
	public void setGradeSystems(final Collection<GradeSystem> gradeSystems) {
		this.gradeSystems = gradeSystems;
	}

	/**
	 * @return the loadGradeSystemsFlag
	 */
	public boolean isLoadGradeSystemsFlag() {
		return this.loadGradeSystemsFlag;
	}

	/**
	 * @param loadGradeSystemsFlag
	 *            the loadGradeSystemsFlag to set
	 */
	public void setLoadGradeSystemsFlag(final boolean loadGradeSystemsFlag) {
		this.loadGradeSystemsFlag = loadGradeSystemsFlag;
	}

	/**
	 * @return the gradeSystemRanges
	 */
	public Collection<GradeSystemRange> getGradeSystemRanges() {
		return this.gradeSystemRanges;
	}

	/**
	 * @param gradeSystemRanges
	 *            the gradeSystemRanges to set
	 */
	public void setGradeSystemRanges(final ArrayList<GradeSystemRange> gradeSystemRanges) {
		this.gradeSystemRanges = gradeSystemRanges;
	}

	/**
	 * @return the currentGradeSystemRange
	 */
	public GradeSystemRange getCurrentGradeSystemRange() {
		return this.currentGradeSystemRange;
	}

	/**
	 * @param currentGradeSystemRange
	 *            the currentGradeSystemRange to set
	 */
	public void setCurrentGradeSystemRange(final GradeSystemRange currentGradeSystemRange) {
		this.currentGradeSystemRange = currentGradeSystemRange;
	}

}