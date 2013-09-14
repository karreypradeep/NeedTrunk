/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.nexed.core.dao;

import org.springframework.stereotype.Repository;

import com.apeironsol.nexed.core.model.Organization;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for organization entity implementation.
 * 
 * @author Pradeep
 */
@Repository("organizationDao")
public class OrganizationDaoImpl extends BaseDaoImpl<Organization> implements OrganizationDao {

}
