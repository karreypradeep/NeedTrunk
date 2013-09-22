/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.security.dao;

import java.util.Collection;

import com.apeironsol.need.security.model.UserGroup;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for user group entity.
 * 
 * @author Pradeep
 */
public interface UserGroupDao extends BaseDao<UserGroup> {

    /**
     * Returns user groups by user account id.
     * 
     * @param userAccountId
     *            user account id.
     * @return user groups by user account id.
     */
    Collection<UserGroup> findUserGroupsByUserAccountId( Long userAccountId );

    /**
     * Returns user group by user name.
     * 
     * @param username
     *            user name.
     * @return user group by user name.
     */
    UserGroup findUserGroupByName( final String name );

}
