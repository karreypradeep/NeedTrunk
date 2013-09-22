package com.apeironsol.need.util.dataobject;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class StudentFinancialAcademicYearDataModel extends ListDataModel<StudentFinancialAcademicYearDO> implements
		SelectableDataModel<StudentFinancialAcademicYearDO> {

	public StudentFinancialAcademicYearDataModel() {
	}

	public StudentFinancialAcademicYearDataModel(final List<StudentFinancialAcademicYearDO> data) {
		super(data);
	}

	@Override
	public StudentFinancialAcademicYearDO getRowData(final String rowKey) {

		@SuppressWarnings("unchecked")
		List<StudentFinancialAcademicYearDO> studentFinancialAcademicYearDOs = (List<StudentFinancialAcademicYearDO>) getWrappedData();
		for (StudentFinancialAcademicYearDO studentFinancialAcademicYearDO : studentFinancialAcademicYearDOs) {
			if (rowKey.equals(studentFinancialAcademicYearDO.getUniqueId())) {
				return studentFinancialAcademicYearDO;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(final StudentFinancialAcademicYearDO studentFinancialAcademicYearDO) {
		return studentFinancialAcademicYearDO.getUniqueId();
	}
}
