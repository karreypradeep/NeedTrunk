package com.apeironsol.nexed.util.portal;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.apeironsol.nexed.util.constants.AuthorityCategoryConstant;
import com.apeironsol.nexed.util.constants.AuthorityConstant;
import com.apeironsol.nexed.util.constants.AuthoritySubCategoryConstant;

public class AuthorityDataModel extends ListDataModel<AuthorityConstant> implements SelectableDataModel<AuthorityConstant> {

	public AuthorityDataModel() {
	}

	public AuthorityDataModel(final List<AuthorityConstant> data) {
		super(data);
	}

	@Override
	public Object getRowKey(final AuthorityConstant authority) {
		if (authority != null) {
			return authority.toString();
		} else {

			return null;
		}
	}

	@Override
	public AuthorityConstant getRowData(final String rowKey) {
		if (rowKey != null) {
			return AuthorityConstant.valueOf(rowKey);
		} else {
			return null;
		}

	}

	public String getLabel() {
		return "";
	}

	public AuthorityCategoryConstant getAuthorityCategory() {
		return null;
	}

	public AuthoritySubCategoryConstant getAuthoritySubCategory() {
		return null;
	}

}
