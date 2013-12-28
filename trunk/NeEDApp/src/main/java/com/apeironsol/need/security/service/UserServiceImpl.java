/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.security.service;

/**
 * Service implementation interface for user.
 * 
 * @author pradeep
 * 
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.jasypt.spring.security3.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.core.dao.RelationDao;
import com.apeironsol.need.core.dao.StudentDao;
import com.apeironsol.need.core.model.Relation;
import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.service.StudentService;
import com.apeironsol.need.hrms.dao.EmployeeDao;
import com.apeironsol.need.hrms.model.Employee;
import com.apeironsol.need.hrms.service.EmployeeService;
import com.apeironsol.need.security.dao.UserAccountDao;
import com.apeironsol.need.security.dao.UserRoleDao;
import com.apeironsol.need.security.model.UserAccount;
import com.apeironsol.need.security.model.UserGroup;
import com.apeironsol.need.security.model.UserGroupAuthority;
import com.apeironsol.need.security.model.UserRole;
import com.apeironsol.need.util.constants.AuthorityConstant;
import com.apeironsol.need.util.constants.UserAccountTypeConstant;

@Service("userService")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService, UserDetailsService {

	@Resource
	private UserAccountDao				userAccountDao;

	@Resource
	private StudentDao					studentDao;

	@Resource
	private RelationDao					relationDao;

	@Resource
	private EmployeeDao					employeeDao;

	@Resource
	private UserGroupAuthorityService	userGroupAuthorityService;

	@Resource
	private StudentService				studentService;

	@Resource
	private EmployeeService				employeeService;

	@Resource
	private UserRoleDao					userRoleDao;

	@Override
	public UserAccount createNewUserAccount(final UserAccount userAccount) {
		UserAccount deplicate = this.userAccountDao.findUserAccountByUsername(userAccount.getUsername());

		if (deplicate != null) {
			throw new BusinessException("Username unavailable.");
		}

		PasswordEncoder encoder = new PasswordEncoder();
		String encodedPassword = encoder.encodePassword(userAccount.getPassword(), null);
		userAccount.setPassword(encodedPassword);
		return this.userAccountDao.persist(userAccount);
	}

	@Override
	public UserAccount updateUserAccount(final UserAccount userAccount) {
		return this.userAccountDao.persist(userAccount);
	}

	@Override
	public void deleteUserAccount(final UserAccount userAccount) {
		this.userAccountDao.remove(userAccount);

	}

	@Override
	public UserAccount findUserAccountByUsername(final String username) {
		return this.userAccountDao.findUserAccountByUsername(username);
	}

	/**
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		UserAccount userAccount = null;

		try {
			userAccount = this.userAccountDao.findUserAccountByUsername(username);
		} catch (RuntimeException exception) {
			throw new UsernameNotFoundException(exception.getMessage());
		}

		if (userAccount == null) {
			throw new UsernameNotFoundException("User not found by username");
		}

		Collection<UserRole> userRoles = userAccount.getUserRoles();

		if (userRoles == null || userRoles.isEmpty()) {
			throw new UsernameNotFoundException("User roles are not defined for this account.");
		}

		Collection<UserGroup> userGroups = userAccount.getUserGroups();

		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

		for (UserRole userRole : userRoles) {

			GrantedAuthority grantedAuthority = new GrantedAuthorityImpl(userRole.getUserRole().name());
			grantedAuthorities.add(grantedAuthority);
		}

		if (userGroups != null && !userGroups.isEmpty()) {
			for (UserGroup userGroup : userGroups) {
				Collection<UserGroupAuthority> userGroupAuthorities = this.userGroupAuthorityService.findUserGroupAuthoritiesByUserGroup(userGroup);
				if (userGroupAuthorities != null) {

					for (UserGroupAuthority userGroupAuthority : userGroupAuthorities) {
						GrantedAuthority grantedAuthority = new GrantedAuthorityImpl(userGroupAuthority.getAuthority().name());
						if (!grantedAuthorities.contains(grantedAuthority)) {
							grantedAuthorities.add(grantedAuthority);
						}
					}

				}
			}
		}

		if (grantedAuthorities.isEmpty()) {
			throw new UsernameNotFoundException("User does not have granted authorities");
		}

		String password = userAccount.getPassword();

		boolean isActive = userAccount.isActive();

		boolean isAccountNotExpried = !userAccount.isAccountExpired();

		boolean isCredentialsNotExpired = !userAccount.isCredentialsExpired();

		boolean isAccountNotLocked = !userAccount.isAccountLocked();

		UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, password, isActive, isAccountNotExpried,
				isCredentialsNotExpired, isAccountNotLocked, grantedAuthorities);

		return userDetails;
	}

	public Collection<UserGroupAuthority> getAuthoritiesByGroupId(final Long id) {

		return null;
	}

	@Override
	public void creteUserAccountForEmployee(final String username, final String password, final String firstName, final String lastName,
			final Date dateOfBirth, final String employeeNumber) {

		Employee employee = this.employeeDao.findAllEmployeesByEmployeeNumber(employeeNumber);

		if (employee == null) {
			throw new BusinessException("Employee not found with empoyee number : " + employeeNumber + ".");
		}

		if (!firstName.equals(employee.getFirstName()) || !lastName.equals(employee.getLastName()) || !dateOfBirth.equals(employee.getDateOfBirth())) {
			throw new BusinessException("Employee details does not match our records.");
		}

		UserAccount userAccount = new UserAccount();

		userAccount.setUsername(username);
		userAccount.setPassword(password);
		userAccount.setUserAccountType(UserAccountTypeConstant.EMPLOYEE);
		userAccount.setActive(true);

		userAccount = this.createNewUserAccount(userAccount);

		UserRole role = new UserRole();
		role.setUserRole(AuthorityConstant.ROLE_EMPLOYEE);
		role.setUserAccount(userAccount);
		this.userRoleDao.persist(role);

		employee.setUserAccount(userAccount);

		this.employeeService.saveEmployee(employee);
	}

	@Override
	public void creteUserAccountForStudent(final String username, final String password, final String firstName, final String lastName, final Date dateOfBirth,
			final String admissionNumber) {

		Student student = this.studentDao.findActiveStudentByAdmissionNumber(admissionNumber);

		if (student == null) {
			throw new BusinessException("Student not found with admission number '" + admissionNumber + "' or not active.");
		}

		if (!firstName.equals(student.getFirstName()) || !lastName.equals(student.getLastName()) || !dateOfBirth.equals(student.getDateOfBirth())) {
			throw new BusinessException("Student details does not match our records.");
		}

		UserAccount userAccount = new UserAccount();

		userAccount.setUsername(username);
		userAccount.setPassword(password);
		userAccount.setUserAccountType(UserAccountTypeConstant.STUDENT);
		userAccount.setActive(true);

		userAccount = this.createNewUserAccount(userAccount);

		UserRole role = new UserRole();
		role.setUserRole(AuthorityConstant.ROLE_STUDENT);
		role.setUserAccount(userAccount);

		this.userRoleDao.persist(role);

		student.setUserAccount(userAccount);

		this.studentService.saveStudent(student);

	}

	@Override
	public void creteUserAccountForParent(final String username, final String password, final String firstName, final String lastName, final Date dateOfBirth,
			final String admissionNumber) {

		Student student = this.studentDao.findActiveStudentByAdmissionNumber(admissionNumber);

		if (student == null) {
			throw new BusinessException("Student not found with admission number or not active. " + admissionNumber + ".");
		}

		Collection<Relation> relations = this.relationDao.findRelationsByStudentId(student.getId());

		if (relations == null || relations.isEmpty()) {
			throw new BusinessException("No relations found for student with admission number " + admissionNumber + ".");
		}

		Relation relationMatch = null;

		for (Relation relation : relations) {

			if (firstName.equals(relation.getFirstName()) && lastName.equals(relation.getLastName()) && dateOfBirth.equals(relation.getDateOfBirth())) {
				relationMatch = relation;
				break;
			}

		}

		if (relationMatch == null) {
			throw new BusinessException("Relation details does not match our records.");
		}

		UserAccount userAccount = new UserAccount();

		userAccount.setUsername(username);
		userAccount.setPassword(password);

		userAccount.setUserAccountType(UserAccountTypeConstant.PARENT);
		userAccount.setActive(true);

		userAccount = this.createNewUserAccount(userAccount);

		UserRole role = new UserRole();
		role.setUserRole(AuthorityConstant.ROLE_PARENT);
		role.setUserAccount(userAccount);
		this.userRoleDao.persist(role);

		relationMatch.setUserAccount(userAccount);

		this.relationDao.persist(relationMatch);

	}
}
