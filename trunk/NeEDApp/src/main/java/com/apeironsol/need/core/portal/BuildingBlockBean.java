package com.apeironsol.need.core.portal;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Named;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.annotation.Scope;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.service.BuildingBlockService;
import com.apeironsol.need.util.constants.BuildingBlockConstant;
import com.apeironsol.need.util.portal.ViewExceptionHandler;

@Named
@Scope(value = "session")
public class BuildingBlockBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long			serialVersionUID	= -7994839957676126689L;

	@Resource
	private BuildingBlockService		buildingBlockService;

	private BuildingBlock				buildingBlock;

	private BuildingBlockConstant		buildingBlockType;

	private Collection<BuildingBlock>	currentBuildingBlocks;

	private boolean						loadAction;

	private boolean						renderNewBuildingBlockPanel;

	@PostConstruct
	public void init() {
	}

	public Collection<BuildingBlock> getCurrentBuildingBlocks() {
		return this.currentBuildingBlocks;
	}

	public String removeBuildingBlock() {
		this.renderNewBuildingBlockPanel = false;
		try {
			this.buildingBlockService.removeBuildingBlock(this.buildingBlock);
		} catch (final ApplicationException ex) {
			ViewExceptionHandler.handle(ex);
		}
		this.loadAction = true;
		return null;
	}

	public String reset() {
		this.setBuildingBlock(new BuildingBlock());
		return null;
	}

	public BuildingBlock getBuildingBlock() {
		return this.buildingBlock;
	}

	public void setBuildingBlock(final BuildingBlock buildingBlock) {
		this.buildingBlock = buildingBlock;
	}

	public void setBuildingBlockType(final BuildingBlockConstant buildingBlockConstant) {
		this.buildingBlockType = buildingBlockConstant;
	}

	public BuildingBlockConstant getBuildingBlockType() {
		return this.buildingBlockType;
	}

	public BuildingBlockConstant getBuildingBlockTypeFee() {
		return BuildingBlockConstant.FEE_TYPE;
	}

	public BuildingBlockConstant getBuildingBlockTypeInvestmentType() {
		return BuildingBlockConstant.INVESTMENT_TYPE;
	}

	public BuildingBlockConstant getBuildingBlockTypeDesignation() {
		return BuildingBlockConstant.DESIGNATION;
	}

	public BuildingBlockConstant getBuildingBlockTypeDepartment() {
		return BuildingBlockConstant.DEPARTMENT;
	}

	public BuildingBlockConstant getBuildingBlockTypeExpense() {
		return BuildingBlockConstant.EXPENSE_TYPE;
	}

	public BuildingBlockConstant getBuildingBlockTypeAssert() {
		return BuildingBlockConstant.ASSERT_TYPE;
	}

	public BuildingBlockConstant getBuildingBlockTypeLiability() {
		return BuildingBlockConstant.LIABILITY_TYPE;
	}

	public BuildingBlockConstant getBuildingBlockTypeExamType() {
		return BuildingBlockConstant.EXAM_TYPE;
	}

	public BuildingBlockConstant getBuildingBlockTypeDocumentsRequiredForAdmission() {
		return BuildingBlockConstant.DOCUMENTS_REQUIRD_FOR_ADMISSION;
	}

	public BuildingBlockConstant getStudentClassification() {
		return BuildingBlockConstant.STUDENT_CLASSIFICATION;
	}

	public BuildingBlockConstant getHostelRoomType() {
		return BuildingBlockConstant.HOSTEL_ROOM_TYPE;
	}

	public boolean isRenderFeeTypeSelection() {
		return BuildingBlockConstant.FEE_TYPE.equals(this.getBuildingBlockType());
	}

	public boolean isRenderEmployeeCTCDefinitionTypeSelection() {
		return BuildingBlockConstant.EMPLOYEE_CTC_DEFINITION_TYPE.equals(this.getBuildingBlockType());
	}

	public boolean isDocumentsRequirdForAdmissionSelection() {
		return BuildingBlockConstant.DOCUMENTS_REQUIRD_FOR_ADMISSION.equals(this.getBuildingBlockType());
	}

	public boolean isRenderSalaryDeductionSelection() {
		return BuildingBlockConstant.SALARY_DEDUCTION.equals(this.getBuildingBlockType());
	}

	public BuildingBlockConstant getBuildingBlockTypeEmployeeCTCDefinition() {
		return BuildingBlockConstant.EMPLOYEE_CTC_DEFINITION_TYPE;
	}

	public BuildingBlockConstant getBuildingBlockTypeSalaryDeduction() {
		return BuildingBlockConstant.SALARY_DEDUCTION;
	}

	public boolean isRenderNewBuildingBlockPanel() {
		return this.renderNewBuildingBlockPanel;
	}

	public void setRenderNewBuildingBlockPanel(final boolean renderNewBuildingBlockPanel) {
		this.renderNewBuildingBlockPanel = renderNewBuildingBlockPanel;
	}

	public String addFeeType() {
		this.renderNewBuildingBlockPanel = true;
		this.setBuildingBlock(new BuildingBlock());
		return null;
	}

	public String saveBuildingBlock() {
		try {
			this.buildingBlock.setType(this.getBuildingBlockType());
			this.buildingBlock = this.buildingBlockService.saveBuildingBlock(this.buildingBlock);
			this.buildingBlock = new BuildingBlock();
			this.buildingBlock.setType(this.getBuildingBlockType());

			this.renderNewBuildingBlockPanel = false;
		} catch (final ApplicationException ex) {
			ViewExceptionHandler.handle(ex);
		} catch (final Exception e) {

			if (e.getCause().getCause() instanceof ConstraintViolationException) {
				ViewExceptionHandler.handle(e.getCause().getCause());
				return null;
			}

		}
		return null;
	}

	public String viewBuildingBlock() {
		this.renderNewBuildingBlockPanel = true;
		this.setBuildingBlock(this.buildingBlock);
		this.loadAction = false;
		return null;
	}

	public void loadCurrentBuildingBlocks() {
		if (this.isLoadAction()) {
			try {
				this.currentBuildingBlocks = this.buildingBlockService.findBuildingBlocksByType(this.buildingBlockType);
				this.loadAction = false;
			} catch (final ApplicationException ex) {
				ViewExceptionHandler.handle(ex);
			}
		}
	}

	public boolean isLoadAction() {
		return this.loadAction;
	}

	public void setLoadAction(final boolean loadAction) {
		this.loadAction = loadAction;
	}

}
