package com.apeironsol.need.core.portal;

import javax.annotation.Resource;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.service.BranchDepartmentService;
import com.apeironsol.need.core.service.BranchExpenseTypePeriodicalService;
import com.apeironsol.need.core.service.BranchFeeTypePeriodicalService;
import com.apeironsol.need.core.service.BranchReservationCategoryService;

@Named
@Scope(value = "session")
public abstract class AbstractBranchBean extends AbstractTabbedBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long					serialVersionUID	= 2833228908930765689L;

	/**
	 * Branch fee type service.
	 */
	@Resource
	private BranchFeeTypePeriodicalService		branchFeeTypeService;

	/**
	 * Branch expense type service.
	 */
	@Resource
	private BranchExpenseTypePeriodicalService	branchExpenseTypeService;

	/**
	 * Branch expense type service.
	 */
	@Resource
	private BranchDepartmentService				branchDepartmentService;

	/**
	 * Branch expense type service.
	 */
	@Resource
	private BranchReservationCategoryService	branchReservationCategoryService;

	/**
	 * Returns branch fee type service.
	 * 
	 * @return branch fee type service.
	 */
	public BranchFeeTypePeriodicalService getBranchFeeTypeService() {
		return this.branchFeeTypeService;
	}

	/**
	 * Returns branch expense type service.
	 * 
	 * @return branch expense type service.
	 */
	public BranchExpenseTypePeriodicalService getBranchExpenseTypeService() {
		return this.branchExpenseTypeService;
	}

	/**
	 * Returns branch department service.
	 * 
	 * @return branch department service.
	 */
	public BranchDepartmentService getBranchDepartmentService() {
		return this.branchDepartmentService;
	}

	/**
	 * Returns branch reservation category service.
	 * 
	 * @return branch reservation category service.
	 */
	public BranchReservationCategoryService getBranchReservationCategoryService() {
		return this.branchReservationCategoryService;
	}

	@Override
	public void onTabChange() {

	}

}
