package com.apeironsol.need.core.portal;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;


@Named
@Scope(value = "session")
public class LeftMenuBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long	serialVersionUID	= -8588341505132904194L;

	@Resource
	AccessBean					accessBean;

	private Integer				activeTabIndex		= new Integer(0);

	public Integer getActiveTabIndex() {
		return this.activeTabIndex;
	}

	public void setActiveTabIndex(final Integer activeTabIndex) {
		this.activeTabIndex = activeTabIndex;
	}

}
