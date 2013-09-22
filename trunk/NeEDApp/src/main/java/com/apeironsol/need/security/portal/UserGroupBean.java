package com.apeironsol.need.security.portal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.security.model.UserGroup;
import com.apeironsol.need.security.model.UserGroupAuthority;
import com.apeironsol.need.security.service.UserAccountService;
import com.apeironsol.need.security.service.UserGroupAuthorityService;
import com.apeironsol.need.security.service.UserGroupService;
import com.apeironsol.need.util.constants.AuthorityConstant;
import com.apeironsol.need.util.portal.AuthorityDataModel;
import com.apeironsol.need.util.portal.ViewUtil;

@Named
@Scope(value = "session")
public class UserGroupBean extends AbstractSecurityBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long			serialVersionUID	= -7909674298329724346L;

	private UserGroup					userGroup;

	@Resource
	private UserAccountService			userAccountService;

	@Resource
	private UserGroupService			userGroupService;

	@Resource
	private UserGroupAuthorityService	userGroupAuthorityService;

	private boolean						renderNewUserGroupButton;

	private boolean						creatingNewUserGroup;

	Collection<UserGroup>				allUserGroups		= new ArrayList<UserGroup>();

	private AuthorityConstant[]			grantedAuthorities	= new AuthorityConstant[] {};

	public AuthorityDataModel getAuthorities() {
		return new AuthorityDataModel(AuthorityConstant.getAllAuthorities());
	}

	public AuthorityConstant[] getGrantedAuthorities() {
		return this.grantedAuthorities;
	}

	public void setGrantedAuthorities(final AuthorityConstant[] grantedAuthorities) {
		this.grantedAuthorities = grantedAuthorities;
	}

	public Collection<UserGroup> getAllUserGroups() {
		return this.allUserGroups;
	}

	public void setAllUserGroups(final Collection<UserGroup> allUserGroups) {
		this.allUserGroups = allUserGroups;
	}

	public boolean isCreatingNewUserGroup() {
		return this.creatingNewUserGroup;
	}

	public void setCreatingNewUserGroup(final boolean creatingNewUserGroup) {
		this.creatingNewUserGroup = creatingNewUserGroup;
	}

	public UserGroup getUserGroup() {
		return this.userGroup;
	}

	public void setUserGroup(final UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public boolean isRenderNewUserGroupButton() {
		return this.renderNewUserGroupButton;
	}

	public void setRenderNewUserGroupButton(final boolean renderNewUserGroupButton) {
		this.renderNewUserGroupButton = renderNewUserGroupButton;
	}

	/**
	 * Load all user groups.
	 */
	public void loadUserGroups() {
		try {
			this.allUserGroups = this.userGroupService.getUserGroups();
		} catch (Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}

	/**
	 * Gets user group for given id.
	 * 
	 * @param userId
	 *            user Id
	 * @return User group
	 */
	public UserGroup getUserGroupById(final Long userId) {
		UserGroup result = null;
		try {
			if (this.allUserGroups == null || this.allUserGroups.size() == 0) {
				this.loadUserGroups();
			}
			for (UserGroup userGroup : this.allUserGroups) {
				if (userGroup.getId().equals(userId)) {
					result = userGroup;
					break;
				}
			}
		} catch (Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		return result;
	}

	/**
	 * Creates a new user group, populates user authority pick list.
	 */
	public void addNewUserGroup() {
		this.creatingNewUserGroup = true;
		this.renderNewUserGroupButton = false;
		this.userGroup = new UserGroup();

		this.grantedAuthorities = new AuthorityConstant[] {};
	}

	/**
	 * Saves user group with given details.
	 */
	public String saveUserGroup() {
		try {

			Collection<UserGroupAuthority> userGroupAuthorities = new ArrayList<UserGroupAuthority>();

			for (AuthorityConstant authoritie : this.grantedAuthorities) {
				UserGroupAuthority userGroupAuthority = new UserGroupAuthority();
				userGroupAuthority.setAuthority(authoritie);
				userGroupAuthorities.add(userGroupAuthority);
			}

			this.userGroup = this.userGroupService.saveUserGroup(this.userGroup, userGroupAuthorities);

			this.setViewOrNewAction(false);
			this.loadUserGroups();

			if (this.creatingNewUserGroup) {
				ViewUtil.addMessage("User group created successfully.", FacesMessage.SEVERITY_INFO);
			} else {
				ViewUtil.addMessage("User group modified successfully.", FacesMessage.SEVERITY_INFO);
			}
		} catch (Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		return null;
	}

	/**
	 * Displays all user groups.
	 */
	public String cancelNewUserGroup() {
		this.setRenderNewUserGroupButton(true);
		return null;
	}

	/**
	 * Shows selected user group details.
	 * 
	 * @param userGroup
	 *            user group
	 */
	public void showUserGroupDetails() {

		Collection<AuthorityConstant> grantedAuthorities = new ArrayList<AuthorityConstant>();

		Collection<UserGroupAuthority> userGroupAuthorities = this.userGroupAuthorityService.findUserGroupAuthoritiesByUserGroup(this.userGroup);

		for (UserGroupAuthority userGroupAuthority : userGroupAuthorities) {

			grantedAuthorities.add(userGroupAuthority.getAuthority());

		}

		this.grantedAuthorities = new AuthorityConstant[grantedAuthorities.size()];
		grantedAuthorities.toArray(this.grantedAuthorities);

	}

	/**
	 * Removes user group.
	 * 
	 * @param userGroup
	 *            user group.
	 */
	public String removeUserGroup() {
		try {

			if (!this.userAccountService.isUserGroupAssignedToUser(this.userGroup)) {
				this.userGroupAuthorityService.removeUserGroupAuthoritiesByUserGroup(this.userGroup);
				this.userGroupService.deleteUserGroup(this.userGroup);

				this.renderNewUserGroupButton = true;
				this.loadUserGroups();

				ViewUtil.addMessage("User group deleted successfully.", FacesMessage.SEVERITY_INFO);
			} else {
				ViewUtil.addMessage("User group " + this.userGroup.getName() + " cannot be removed, because it is associated to with user accounts.",
						FacesMessage.SEVERITY_WARN);
			}
		} catch (Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		return null;
	}
}
