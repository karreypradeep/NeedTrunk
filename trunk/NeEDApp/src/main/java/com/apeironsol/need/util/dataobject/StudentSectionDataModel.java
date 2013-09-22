package com.apeironsol.need.util.dataobject;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.apeironsol.need.core.model.StudentSection;

public class StudentSectionDataModel extends ListDataModel<StudentSection> implements
		SelectableDataModel<StudentSection> {

	public StudentSectionDataModel() {
	}

	public StudentSectionDataModel(final List<StudentSection> data) {
		super(data);
	}

	@Override
	public StudentSection getRowData(final String rowKey) {

		@SuppressWarnings("unchecked")
		List<StudentSection> studentSectionDOs = (List<StudentSection>) getWrappedData();
		for (StudentSection studentSectionDO : studentSectionDOs) {
			if (rowKey.equals(studentSectionDO.getId().toString())) {
				return studentSectionDO;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(final StudentSection studentSectionDO) {
		return studentSectionDO.getId().toString();
	}
}
