/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

/**
 * Service interface for organization.
 * 
 * @author pradeep
 * 
 */
import com.apeironsol.need.core.model.Organization;
import com.apeironsol.need.util.constants.AccessControlTypeConstant;
import com.apeironsol.framework.exception.BusinessException;

public interface OrganizationService {

	/**
	 * Save organization.
	 * 
	 * @param organization
	 *            organization to be saved.
	 * @return
	 * @throws BusinessException
	 */
	Organization saveOrganization(Organization organization) throws BusinessException;

	/**
	 * Locks or unlocks the organization.
	 * 
	 * @param organization
	 *            organization.
	 * @return
	 * @throws BusinessException
	 */
	Organization lockUnlockOrganization(Organization organization) throws BusinessException;

	/**
	 * Get organization.
	 * 
	 * @return
	 */
	Organization getOrginazation();

	/**
	 * Returns true if user has access to organization.
	 * 
	 * @param username
	 * @param refId
	 * @param organizationAccessType
	 * @return
	 */
	boolean hasAccess(String username, Long refId, AccessControlTypeConstant organizationAccessType);

	/**
	 * Returns true if user has access to organization.
	 * 
	 * @param username
	 * @param organizationAccessType
	 * @return
	 */
	boolean hasAccess(String username, AccessControlTypeConstant organizationAccessType);
}
