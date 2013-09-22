/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.constants;

import java.util.ArrayList;
import java.util.List;

import com.apeironsol.need.util.EnumUtil;
import com.apeironsol.need.util.portal.ViewUtil;

/**
 * Enums for BuildingBlock constants
 * 
 * @author Pradeep
 */
public enum BuildingBlockConstant implements Labeled {

	FEE_TYPE("fee_type"),
	DEPARTMENT("department"),
	EXPENSE_TYPE("expense_type"),
	DESIGNATION("designation"),
	INVESTMENT_TYPE("investment_type"),
	ASSERT_TYPE("assert_type"),
	LIABILITY_TYPE("liability_type"),
	DOCUMENTS_REQUIRD_FOR_ADMISSION("documents_requird_for_admission"),
	STUDENT_CLASSIFICATION("student_classification"),
	EMPLOYEE_CTC_DEFINITION_TYPE("employee_ctc_definition_type"),
	SALARY_DEDUCTION("salary_deduction"),
	EXAM_TYPE("exam_type");

	private String								label;
	private static List<BuildingBlockConstant>	allBuildingBlocks						= new ArrayList<BuildingBlockConstant>();
	private static List<BuildingBlockConstant>	allBuildingBlocksForBranchAssemblies	= new ArrayList<BuildingBlockConstant>();

	static {
		allBuildingBlocks.add(FEE_TYPE);
		allBuildingBlocks.add(DEPARTMENT);
		allBuildingBlocks.add(EXPENSE_TYPE);
		allBuildingBlocks.add(DESIGNATION);
		allBuildingBlocks.add(INVESTMENT_TYPE);
		allBuildingBlocks.add(ASSERT_TYPE);
		allBuildingBlocks.add(LIABILITY_TYPE);
		allBuildingBlocks.add(DOCUMENTS_REQUIRD_FOR_ADMISSION);
		allBuildingBlocks.add(STUDENT_CLASSIFICATION);
		allBuildingBlocks.add(EXAM_TYPE);
		allBuildingBlocks.add(EMPLOYEE_CTC_DEFINITION_TYPE);
		allBuildingBlocks.add(SALARY_DEDUCTION);

		allBuildingBlocksForBranchAssemblies.add(FEE_TYPE);
		allBuildingBlocksForBranchAssemblies.add(DEPARTMENT);
		allBuildingBlocksForBranchAssemblies.add(EXPENSE_TYPE);
		allBuildingBlocksForBranchAssemblies.add(DESIGNATION);
		allBuildingBlocksForBranchAssemblies.add(INVESTMENT_TYPE);
		allBuildingBlocksForBranchAssemblies.add(ASSERT_TYPE);
		allBuildingBlocksForBranchAssemblies.add(LIABILITY_TYPE);
		allBuildingBlocksForBranchAssemblies.add(DOCUMENTS_REQUIRD_FOR_ADMISSION);
		allBuildingBlocksForBranchAssemblies.add(STUDENT_CLASSIFICATION);
		allBuildingBlocksForBranchAssemblies.add(EXAM_TYPE);
		allBuildingBlocksForBranchAssemblies.add(EMPLOYEE_CTC_DEFINITION_TYPE);
		allBuildingBlocksForBranchAssemblies.add(SALARY_DEDUCTION);

	}

	BuildingBlockConstant(final String label) {
		this.setLabel(label);
	}

	@Override
	public String getLabel() {
		return ViewUtil.getEnumLabel(this.label);
	}

	@Override
	public void setLabel(final String label) {
		this.label = label;
	}

	public static List<BuildingBlockConstant> getAllBuildingBlocks() {
		return allBuildingBlocks;
	}

	public static List<BuildingBlockConstant> getAllSortedBuildingBlocksForBranchAssemblies() {
		List<BuildingBlockConstant> sortedConstants = EnumUtil.getEnumsSortedByLabels(BuildingBlockConstant.class);
		List<BuildingBlockConstant> sortedBuildingBlocksForBranchAssembliesConstants = new ArrayList<BuildingBlockConstant>();
		for (BuildingBlockConstant buildingBlockConstant : sortedConstants) {
			if (allBuildingBlocksForBranchAssemblies.contains(buildingBlockConstant)) {
				sortedBuildingBlocksForBranchAssembliesConstants.add(buildingBlockConstant);
			}
		}
		return sortedBuildingBlocksForBranchAssembliesConstants;
	}
}
