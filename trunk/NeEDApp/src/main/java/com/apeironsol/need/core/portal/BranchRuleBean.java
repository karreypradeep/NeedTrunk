package com.apeironsol.need.core.portal;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.core.model.BranchRule;
import com.apeironsol.need.core.service.BranchRuleService;
import com.apeironsol.need.util.constants.BranchTabConstant;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;

@Named
@Scope(value = "session")
public class BranchRuleBean extends AbstractBranchBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long	serialVersionUID	= 5472947270072862047L;

	/**
	 * Logger for the class.
	 */
	private static final Logger	log					= Logger.getLogger(BranchRuleBean.class);

	@Resource
	SessionBean					sessionBean;

	@Resource
	BranchRuleService			branchRuleService;

	@Resource
	BranchBean					branchBean;

	private BranchRule			branchRule;

	/**
	 * Returns branch rule.
	 * 
	 * @return the branchRule
	 */
	public BranchRule getBranchRule() {
		return this.branchRule;
	}

	/**
	 * Sets branch rule.
	 * 
	 * @param branchRule
	 *            the branchRule to set
	 */
	public void setBranchRule(final BranchRule branchRule) {
		this.branchRule = branchRule;
	}

	/**
	 * Loads branch rule from database.
	 */
	public void loadBranchRule() {
		if (this.branchBean.getActiveTabIndex() == BranchTabConstant.RULES.getTabIndex()) {
			if ((this.branchBean.getBranch() != null) && (this.branchBean.getBranch().getId() != null)) {
				this.branchRule = this.branchRuleService.findBranchRuleByBranchId(this.branchBean.getBranch().getId());
			}
		}
		this.branchRule = this.branchRule == null ? new BranchRule() : this.branchRule;
	}

	/**
	 * Saves the branch in database.
	 * 
	 * @return
	 */
	public String saveBranchRule() {
		try {
			this.branchRule.setBranch(this.branchBean.getBranch());
			this.branchRule = this.branchRuleService.saveBranchRule(this.branchRule);
		} catch (final ApplicationException exception) {
			log.info(exception.getMessage());
			ViewExceptionHandler.handle(exception);
		}
		ViewUtil.addMessage("Branch rules saved successfully.", FacesMessage.SEVERITY_INFO);
		return null;
	}
}
