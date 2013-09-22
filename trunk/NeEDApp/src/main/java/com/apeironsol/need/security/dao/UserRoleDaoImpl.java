/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.security.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.apeironsol.need.security.model.UserRole;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface implementation for user group authority entity.
 * 
 * @author Pradeep
 */
@Repository
public class UserRoleDaoImpl extends BaseDaoImpl<UserRole> implements UserRoleDao {

	static final Logger	log	= Logger.getLogger(UserRoleDaoImpl.class);

}
