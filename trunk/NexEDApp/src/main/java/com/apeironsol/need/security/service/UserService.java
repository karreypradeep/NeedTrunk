/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.security.service;

/**
 * Service interface for user.
 * 
 * @author pradeep
 * 
 */
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.apeironsol.need.security.model.UserAccount;

public interface UserService extends UserDetailsService {

	UserAccount createNewUserAccount(UserAccount user);

	void deleteUserAccount(UserAccount user);

	UserAccount findUserAccountByUsername(String username);

	void creteUserAccountForEmployee(String username, String password, String firstName, String lastName,
			Date dateOfBirth, String employeeNumber);

	void creteUserAccountForStudent(String username, String password, String firstName, String lastName,
			Date dateOfBirth, String admissionNumber);

	void creteUserAccountForParent(String username, String password, String firstName, String lastName,
			Date dateOfBirth, String admissionNumber);

	UserAccount updateUserAccount(UserAccount userAccount);
}
