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
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.OrganizationDao;
import com.apeironsol.need.core.model.Organization;
import com.apeironsol.need.security.dao.AccessControlDao;
import com.apeironsol.need.security.model.AccessControl;
import com.apeironsol.need.security.model.UserAccount;
import com.apeironsol.need.security.service.UserService;
import com.apeironsol.need.util.constants.AccessControlTypeConstant;
import com.apeironsol.framework.exception.BusinessException;

@Service("organizationService")
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

	@Resource
	private OrganizationDao		organizationDao;

	@Resource
	private UserService			userService;

	@Resource
	private AccessControlDao	organizationAccessDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Organization saveOrganization(final Organization organization) {
		return organizationDao.persist(organization);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Organization getOrginazation() {
		Collection<Organization> organizations = organizationDao.findAll();
		if (organizations == null || organizations.isEmpty()) {
			return null;
		}
		return organizations.iterator().next();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasAccess(final String username, final Long refId,
			final AccessControlTypeConstant organizationAccessType) {

		UserAccount userAccount = userService.findUserAccountByUsername(username);
		if (userAccount != null) {
			AccessControl organizationAccess = organizationAccessDao.findAccessControlByType(userAccount, refId,
					organizationAccessType);
			return organizationAccess != null;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasAccess(final String username, final AccessControlTypeConstant organizationAccessType) {

		UserAccount userAccount = userService.findUserAccountByUsername(username);
		if (userAccount != null) {
			AccessControl organizationAccess = organizationAccessDao.findAccessControlByType(userAccount,
					organizationAccessType);
			return organizationAccess != null ? true : false;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Organization lockUnlockOrganization(final Organization organization) throws BusinessException {
		organization.setLocked(organization.isLocked() ? false : true);
		return organizationDao.persist(organization);
	}

}
