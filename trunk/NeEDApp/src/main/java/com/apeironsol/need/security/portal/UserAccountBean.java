/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.security.portal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.model.DualListModel;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.portal.AbstractPortalBean;
import com.apeironsol.need.core.portal.SessionBean;
import com.apeironsol.need.security.model.AccessControl;
import com.apeironsol.need.security.model.UserAccount;
import com.apeironsol.need.security.model.UserGroup;
import com.apeironsol.need.security.service.AccessControlService;
import com.apeironsol.need.security.service.UserAccountService;
import com.apeironsol.need.security.service.UserGroupService;
import com.apeironsol.need.util.constants.AccessControlTypeConstant;
import com.apeironsol.need.util.portal.ViewUtil;

@Named
@Scope(value = "session")
public class UserAccountBean extends AbstractPortalBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long			serialVersionUID			= -2533711124469566630L;

	private UserAccount					userAccount;

	@Resource
	private UserGroupBean				userGroupBean;

	@Resource
	private UserAccountService			userAccountService;

	@Resource
	private SessionBean					sesionBean;

	@Resource
	private UserGroupService			userGroupService;

	@Resource
	private AccessControlService		accessControlService;

	private DualListModel<UserGroup>	userGroupsDualListModel;

	private DualListModel<String>		accessControlNamesDualListModel;

	List<UserGroup>						userGroupSource				= new ArrayList<UserGroup>();

	List<UserGroup>						userGroupTarget				= new ArrayList<UserGroup>();

	List<String>						accessControlNamesSource	= new ArrayList<String>();

	List<String>						accessControlNamesTarget	= new ArrayList<String>();

	private boolean						renderNewUserButton;

	private boolean						creatingNewUser;

	Collection<UserAccount>				allUserAccounts				= new ArrayList<UserAccount>();

	Collection<UserGroup>				allUserGroups				= new ArrayList<UserGroup>();

	Map<String, Branch>					allBranchMap				= new HashMap<String, Branch>();

	private AccessControlTypeConstant	selectedAccessControlType	= AccessControlTypeConstant.BRANCH;

	public Collection<UserGroup> getAllUserGroups() {
		return this.allUserGroups;
	}

	public void setAllUserGroups(final Collection<UserGroup> allUserGroups) {
		this.allUserGroups = allUserGroups;
	}

	public Map<String, Branch> getAllBranchMap() {
		return this.allBranchMap;
	}

	public void setAllBranchMap(final Map<String, Branch> allBranchMap) {
		this.allBranchMap = allBranchMap;
	}

	public UserAccountService getUserAccountService() {
		return this.userAccountService;
	}

	public void setUserAccountService(final UserAccountService userAccountService) {
		this.userAccountService = userAccountService;
	}

	public AccessControlService getAccessControlService() {
		return this.accessControlService;
	}

	public void setAccessControlService(final AccessControlService accessControlService) {
		this.accessControlService = accessControlService;
	}

	public AccessControlTypeConstant getSelectedAccessControlType() {
		return this.selectedAccessControlType;
	}

	public void setSelectedAccessControlType(final AccessControlTypeConstant selectedAccessControlType) {
		this.selectedAccessControlType = selectedAccessControlType;
	}

	public DualListModel<String> getAccessControlNamesDualListModel() {
		return this.accessControlNamesDualListModel;
	}

	public void setAccessControlNamesDualListModel(final DualListModel<String> accessControlNamesDualListModel) {
		this.accessControlNamesDualListModel = accessControlNamesDualListModel;
	}

	public List<String> getAccessControlNamesSource() {
		return this.accessControlNamesSource;
	}

	public void setAccessControlNamesSource(final List<String> accessControlNamesSource) {
		this.accessControlNamesSource = accessControlNamesSource;
	}

	public List<String> getAccessControlNamesTarget() {
		return this.accessControlNamesTarget;
	}

	public void setAccessControlNamesTarget(final List<String> accessControlNamesTarget) {
		this.accessControlNamesTarget = accessControlNamesTarget;
	}

	public void setUserGroupSource(final List<UserGroup> userGroupSource) {
		this.userGroupSource = userGroupSource;
	}

	public void setUserGroupTarget(final List<UserGroup> userGroupTarget) {
		this.userGroupTarget = userGroupTarget;
	}

	public boolean isCreatingNewUser() {
		return this.creatingNewUser;
	}

	public void setCreatingNewUser(final boolean creatingNewUser) {
		this.creatingNewUser = creatingNewUser;
	}

	public boolean isRenderNewUserButton() {
		return this.renderNewUserButton;
	}

	public void setRenderNewUserButton(final boolean renderNewUserButton) {
		this.renderNewUserButton = renderNewUserButton;
	}

	public DualListModel<UserGroup> getUserGroupsDualListModel() {
		return this.userGroupsDualListModel;
	}

	public void setUserGroupsDualListModel(final DualListModel<UserGroup> userGroupsDualListModel) {
		this.userGroupsDualListModel = userGroupsDualListModel;
	}

	public List<UserGroup> getUserGroupSource() {
		return this.userGroupSource;
	}

	public List<UserGroup> getUserGroupTarget() {
		return this.userGroupTarget;
	}

	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public Collection<UserAccount> getUserAccounts() {
		return this.allUserAccounts;
	}

	public AccessControl getAccessControlById(final Long id) {
		return this.accessControlService.getAccessControlById(id);
	}

	/**
	 * Load all user groups and branches.
	 */
	public void loadStartUpData() {
		try {
			this.loadUserAccountsData();

			this.allUserGroups = this.allUserGroups == null ? this.allUserGroups = new ArrayList<UserGroup>() : this.allUserGroups;

			this.allUserGroups.clear();
			for (UserGroup userGroup : this.userGroupService.getUserGroups()) {
				this.allUserGroups.add(userGroup);
			}

			this.allBranchMap.clear();
			for (Branch branch : this.getAllBranchs()) {
				this.allBranchMap.put(branch.getName(), branch);
			}
			this.allBranchMap = this.allBranchMap == null ? this.allBranchMap = new HashMap<String, Branch>() : this.allBranchMap;
		} catch (Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}

	/**
	 * Load all user accounts.
	 */
	private void loadUserAccountsData() {
		try {
			this.allUserAccounts.clear();
			for (UserAccount userAccount : this.userAccountService.findAllUserAccounts()) {
				if (this.sesionBean.getCurrentUserAccount() != null && !this.sesionBean.getCurrentUserAccount().getId().equals(userAccount.getId())) {
					this.allUserAccounts.add(userAccount);
				}
			}
		} catch (Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}

	}

	/**
	 * Creates a new user account, populates user group and access control pick
	 * list.
	 */
	public void addNewUser() {
		this.userAccount = new UserAccount();

		this.userGroupSource.clear();
		this.userGroupTarget.clear();
		this.userGroupSource.addAll(this.allUserGroups);
		this.userGroupsDualListModel = new DualListModel<UserGroup>(this.userGroupSource, this.userGroupTarget);

		this.accessControlNamesSource.clear();
		this.accessControlNamesTarget.clear();
		this.selectedAccessControlType = AccessControlTypeConstant.BRANCH;
		this.accessControlNamesSource.addAll(this.allBranchMap.keySet());
		this.accessControlNamesDualListModel = new DualListModel<String>(this.accessControlNamesSource, this.accessControlNamesTarget);

		this.renderNewUserButton = false;
		this.creatingNewUser = true;
	}

	/**
	 * Populates access control pick list based on selection.
	 */
	public void updateAccessControlPickList() {
		this.accessControlNamesSource.clear();
		this.accessControlNamesTarget.clear();
		if (this.selectedAccessControlType.equals(AccessControlTypeConstant.BRANCH)) {
			this.accessControlNamesSource.addAll(this.allBranchMap.keySet());
		} else if (this.selectedAccessControlType.equals(AccessControlTypeConstant.ORGANIZATION)) {
			this.accessControlNamesSource.add(this.getOrganization().getName());
		}
		this.accessControlNamesDualListModel = new DualListModel<String>(this.accessControlNamesSource, this.accessControlNamesTarget);
		FacesContext context = FacesContext.getCurrentInstance();
		context.renderResponse();
	}

	/**
	 * Saves user account with given details.
	 */
	public String saveUserAccount() {
		try {

			this.userAccount.setUserGroups(this.userGroupsDualListModel.getTarget());

			this.userAccount.setAccessControls(this.createAccessControlForUserAccount());
			// Save password only for first time i.e. when user account is being
			// created first time.
			this.userAccount = this.userAccountService.saveUserAccount(this.getUserAccount(), this.getUserAccount().getId() == null);

			this.renderNewUserButton = true;
			this.loadUserAccountsData();

			if (this.creatingNewUser) {
				ViewUtil.addMessage("User account created successfully.", FacesMessage.SEVERITY_INFO);
			} else {
				ViewUtil.addMessage("User account modified successfully.", FacesMessage.SEVERITY_INFO);
			}
		} catch (Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		return null;
	}

	/**
	 * Creates access controls for user account.
	 * 
	 * @return Collection<AccessControl>
	 */
	private Collection<AccessControl> createAccessControlForUserAccount() {
		List<String> accessControlNames = new ArrayList<String>();
		for (String accessControlName : this.accessControlNamesDualListModel.getTarget()) {
			accessControlNames.add(accessControlName);
		}
		List<AccessControl> accessControls = new ArrayList<AccessControl>();
		if (accessControlNames.size() > 0) {
			if (this.selectedAccessControlType.equals(AccessControlTypeConstant.ORGANIZATION)) {
				if (accessControlNames.get(0).equals(this.getOrganization().getName())) {
					AccessControl accessControl = new AccessControl();
					accessControl.setAccessControlType(this.selectedAccessControlType);
					accessControl.setRefId(this.getOrganization().getId());
					accessControl.setUserAccount(this.userAccount);
					accessControls.add(accessControl);
				}
			} else if (this.selectedAccessControlType.equals(AccessControlTypeConstant.BRANCH)) {
				for (String accessControlName : accessControlNames) {
					Branch branch = this.allBranchMap.get(accessControlName);
					if (branch != null) {
						AccessControl accessControl = new AccessControl();
						accessControl.setAccessControlType(this.selectedAccessControlType);
						accessControl.setRefId(branch.getId());
						accessControl.setUserAccount(this.userAccount);
						accessControls.add(accessControl);
					}
				}
			}
		}
		return accessControls;
	}

	/**
	 * Shows selected user account details.
	 * 
	 * @param userAccount
	 *            user account
	 */
	public String showUserDetails() {
		try {
			this.userAccount = this.userAccountService.findUserAccountById(this.userAccount.getId(), true);

			this.populateUserGroupPickList();
			this.populateAccessControlPickList();

			this.creatingNewUser = false;
			this.renderNewUserButton = false;
		} catch (Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		return null;
	}

	/**
	 * Populates user group pick list.
	 */
	private void populateUserGroupPickList() {
		this.userGroupTarget.clear();
		this.userGroupSource.clear();
		this.userGroupTarget.addAll(this.userAccount.getUserGroups());

		Collection<Long> userGroupIds = new ArrayList<Long>();
		for (UserGroup userGroup : this.userAccount.getUserGroups()) {
			userGroupIds.add(userGroup.getId());
		}
		for (UserGroup userGroup : this.allUserGroups) {
			if (!userGroupIds.contains(userGroup.getId())) {
				this.userGroupSource.add(userGroup);
			}
		}
		this.userGroupsDualListModel = new DualListModel<UserGroup>(this.userGroupSource, this.userGroupTarget);
	}

	/**
	 * Populates access control pick list.
	 */
	private void populateAccessControlPickList() {
		this.accessControlNamesSource.clear();
		this.accessControlNamesTarget.clear();
		Map<Long, AccessControl> accessControlsMap = new TreeMap<Long, AccessControl>();
		for (AccessControl accessControl : this.accessControlService.findAccessControlsByUser(this.userAccount)) {
			accessControlsMap.put(accessControl.getRefId(), accessControl);
		}
		if (accessControlsMap.size() > 0) {
			if (accessControlsMap.size() == 1
					&& accessControlsMap.values().iterator().next().getAccessControlType().equals(AccessControlTypeConstant.ORGANIZATION)) {
				this.selectedAccessControlType = AccessControlTypeConstant.ORGANIZATION;
				this.accessControlNamesTarget.add(this.getOrganization().getName());
			} else {
				this.selectedAccessControlType = AccessControlTypeConstant.BRANCH;
				for (Branch branch : this.getAllBranchs()) {
					if (accessControlsMap.containsKey(branch.getId())) {
						this.accessControlNamesTarget.add(branch.getName());
					} else {
						this.accessControlNamesSource.add(branch.getName());
					}
				}
			}
			this.accessControlNamesDualListModel = new DualListModel<String>(this.accessControlNamesSource, this.accessControlNamesTarget);
		} else {
			this.selectedAccessControlType = AccessControlTypeConstant.BRANCH;
			this.accessControlNamesSource.addAll(this.allBranchMap.keySet());
			this.accessControlNamesDualListModel = new DualListModel<String>(this.accessControlNamesSource, this.accessControlNamesTarget);
		}
	}

	/**
	 * Removes user account.
	 * 
	 * @param userAccount
	 *            user account.
	 */
	public String removeUserAccount() {
		try {
			this.userAccountService.deleteUserAccount(this.userAccount.getId());

			this.renderNewUserButton = true;
			this.loadUserAccountsData();

			ViewUtil.addMessage("User account deleted successfully.", FacesMessage.SEVERITY_INFO);
		} catch (Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		return null;
	}

	/**
	 * Displays all user accounts.
	 */
	public String cancelUserTask() {
		this.renderNewUserButton = true;
		return null;
	}

	/**
	 * Gets user group for given id.
	 * 
	 * @param userId
	 *            user Id
	 * @return User group
	 */
	public UserGroup getUserGroupById(final Long userId) {
		return this.userGroupBean.getUserGroupById(userId);
	}

}
