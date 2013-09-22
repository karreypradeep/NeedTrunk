package com.apeironsol.need.util.dataobject;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class StudentFinancialDataModel extends ListDataModel<StudentFinancialDO> implements
		SelectableDataModel<StudentFinancialDO> {

	public StudentFinancialDataModel() {
	}

	public StudentFinancialDataModel(final List<StudentFinancialDO> data) {
		super(data);
	}

	@Override
	public StudentFinancialDO getRowData(final String rowKey) {

		@SuppressWarnings("unchecked")
		List<StudentFinancialDO> studentFinancialDOs = (List<StudentFinancialDO>) this.getWrappedData();

		for (StudentFinancialDO studentFinancialDO : studentFinancialDOs) {
			if (rowKey.equals(studentFinancialDO.getUniqueId())) {
				return studentFinancialDO;
			}
		}

		return null;
	}

	@Override
	public Object getRowKey(final StudentFinancialDO studentFinancialDO) {

		return studentFinancialDO.getUniqueId();

	}
}
