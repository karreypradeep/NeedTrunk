package com.apeironsol.need.financial.service;

import java.util.Collection;

import com.apeironsol.need.core.model.Transaction;
import com.apeironsol.need.financial.model.FinancialAccount;

public interface FinancialAccountService {

	FinancialAccount createStudentAccount(Long studentId);

	FinancialAccount createBranchAccount(Long branchId);

	FinancialAccount createEmployeeAccount(Long employeeId);

	FinancialAccount createOrganizationAccount(Long organizationId);

	Collection<Transaction> findStudentTransactions(Long studentId);

	Collection<Transaction> findBranchTransactions(Long branchId);

	Collection<Transaction> findEmployeeTransactions(Long employeeId);

	Collection<Transaction> findOrganizationTransactions(Long organizationId);

	void addPayment(Transaction transaction, String accountNumber);

	void addExpense(Transaction transaction, String accountNUmber);

	void addDeposit(Transaction transaction, String accountNUmber);

}
