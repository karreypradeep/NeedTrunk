package com.apeironsol.need.financial.portal;

import java.util.Collection;

import javax.annotation.Resource;

import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.core.service.BuildingBlockService;
import com.apeironsol.need.util.constants.FeeClassificationLevelConstant;

public abstract class AbstractFinancialBean extends AbstractTabbedBean {

	/**
	 * 
	 */
	private static final long			serialVersionUID	= -7953607575386427617L;

	@Resource
	private BuildingBlockService		buildingBlockService;

	private boolean						loadBranchLevelFeeTypeBuildingBlocksFlag;

	private Collection<BuildingBlock>	branchLevelFeeTypes;

	private boolean						loadStudentLevelFeeTypeBuildingBlocksFlag;

	private Collection<BuildingBlock>	studentLevelFeeTypes;

	public void loadBranchLevelFeeTypeBuildingBlocks() {

		if (this.loadBranchLevelFeeTypeBuildingBlocksFlag) {

			this.branchLevelFeeTypes = this.buildingBlockService.findFeeTypeBuildingBlocksbyBranchIdAndFeeClassificationLevel(this.sessionBean
					.getCurrentBranch().getId(), FeeClassificationLevelConstant.BRANCH_LEVEL);

			this.loadBranchLevelFeeTypeBuildingBlocksFlag = false;

		}

	}

	public boolean isLoadBranchLevelFeeTypeBuildingBlocksFlag() {
		return this.loadBranchLevelFeeTypeBuildingBlocksFlag;
	}

	public void setLoadBranchLevelFeeTypeBuildingBlocksFlag(final boolean loadBranchLevelFeeTypeBuildingBlocksFlag) {
		this.loadBranchLevelFeeTypeBuildingBlocksFlag = loadBranchLevelFeeTypeBuildingBlocksFlag;
	}

	public Collection<BuildingBlock> getBranchLevelFeeTypes() {
		return this.branchLevelFeeTypes;
	}

	public void setBranchLevelFeeTypes(final Collection<BuildingBlock> branchLevelFeeTypes) {
		this.branchLevelFeeTypes = branchLevelFeeTypes;
	}

	public boolean isLoadStudentLevelFeeTypeBuildingBlocksFlag() {
		return this.loadStudentLevelFeeTypeBuildingBlocksFlag;
	}

	public void setLoadStudentLevelFeeTypeBuildingBlocksFlag(final boolean loadStudentLevelFeeTypeBuildingBlocksFlag) {
		this.loadStudentLevelFeeTypeBuildingBlocksFlag = loadStudentLevelFeeTypeBuildingBlocksFlag;
	}

	public Collection<BuildingBlock> getStudentLevelFeeTypes() {
		return this.studentLevelFeeTypes;
	}

	public void setStudentLevelFeeTypes(final Collection<BuildingBlock> studentLevelFeeTypes) {
		this.studentLevelFeeTypes = studentLevelFeeTypes;
	}

	public void loadStudentLevelFeeTypeBuildingBlocks() {
		if (this.loadStudentLevelFeeTypeBuildingBlocksFlag) {

			this.studentLevelFeeTypes = this.buildingBlockService.findFeeTypeBuildingBlocksbyBranchIdAndFeeClassificationLevel(this.sessionBean
					.getCurrentBranch().getId(), FeeClassificationLevelConstant.STUDENT_LEVEL);

			this.loadStudentLevelFeeTypeBuildingBlocksFlag = false;
		}
	}

}
