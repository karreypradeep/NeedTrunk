package com.apeironsol.nexed.academics.portal;

import java.util.Collection;

import javax.annotation.Resource;

import com.apeironsol.nexed.core.model.BuildingBlock;
import com.apeironsol.nexed.core.portal.AbstractPortalBean;
import com.apeironsol.nexed.core.service.BuildingBlockService;
import com.apeironsol.nexed.util.constants.BuildingBlockConstant;

public abstract class AbstractExamBean extends AbstractPortalBean {

	/**
	 * 
	 */
	private static final long			serialVersionUID	= 7080956009257370024L;

	@Resource
	private BuildingBlockService		buildingBlockService;

	private boolean						loadExamTypeBuildingBlockFalg;

	private Collection<BuildingBlock>	examTypeBuildingBlocks;

	public void loadExamTypeBuildingBlocks() {

		if (this.isLoadExamTypeBuildingBlockFalg()) {

			this.setExamTypeBuildingBlocks(this.buildingBlockService.findBuildingBlocksbyBranchIdAndBuildingBlockType(this.sessionBean.getCurrentBranch()
					.getId(), BuildingBlockConstant.EXAM_TYPE));

			this.setLoadExamTypeBuildingBlockFalg(false);

		}

	}

	public boolean isLoadExamTypeBuildingBlockFalg() {
		return this.loadExamTypeBuildingBlockFalg;
	}

	public void setLoadExamTypeBuildingBlockFalg(final boolean loadExamTypeBuildingBlockFalg) {
		this.loadExamTypeBuildingBlockFalg = loadExamTypeBuildingBlockFalg;
	}

	public Collection<BuildingBlock> getExamTypeBuildingBlocks() {
		return this.examTypeBuildingBlocks;
	}

	public void setExamTypeBuildingBlocks(final Collection<BuildingBlock> examTypeBuildingBlocks) {
		this.examTypeBuildingBlocks = examTypeBuildingBlocks;
	}

}
