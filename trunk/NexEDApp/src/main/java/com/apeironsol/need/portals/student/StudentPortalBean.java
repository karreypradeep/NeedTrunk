/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.portals.student;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.portal.StudentBean;

/**
 * Java bean class for student portal information.
 * 
 * @author Pradeep
 */
@Named
@Scope("session")
public class StudentPortalBean extends StudentBean{

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= 358174617380740906L;

	
	
	
	public void init() {
		
		this.student = this.studentService.findStudentByUsername(this.sessionBean.getPrinipal());
		
		this.setLoadProfilePictureFlag(true);
		
	}


	
	
	

}
