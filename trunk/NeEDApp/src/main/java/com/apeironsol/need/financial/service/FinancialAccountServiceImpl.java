package com.apeironsol.need.financial.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.model.Transaction;
import com.apeironsol.need.financial.dao.FinancialAccountDao;
import com.apeironsol.need.financial.dao.TransactionDao;
import com.apeironsol.need.financial.model.FinancialAccount;
import com.apeironsol.need.util.UniqueIdGenerator;
import com.apeironsol.need.util.constants.FinancialAccountTypeConstant;
import com.apeironsol.need.util.constants.TransactionTypeConstant;

@Service(value = "financialAccountService")
@Transactional(rollbackFor = Exception.class)
public class FinancialAccountServiceImpl implements FinancialAccountService {

	@Resource
	TransactionDao		transactionDao;

	@Resource
	FinancialAccountDao	financialAccountDao;

	@Override
	public FinancialAccount createStudentAccount(final Long studentId) {
		FinancialAccount financialAccount = new FinancialAccount();
		financialAccount.setAccountNumber(String.valueOf(UniqueIdGenerator.get()));
		financialAccount.setAccountType(FinancialAccountTypeConstant.STUDENT_ACCOUNT);
		return financialAccount;
	}

	@Override
	public FinancialAccount createBranchAccount(final Long branchId) {
		FinancialAccount financialAccount = new FinancialAccount();
		financialAccount.setAccountNumber(String.valueOf(UniqueIdGenerator.get()));
		financialAccount.setAccountType(FinancialAccountTypeConstant.STUDENT_ACCOUNT);
		return financialAccount;
	}

	@Override
	public FinancialAccount createEmployeeAccount(final Long employeeId) {
		FinancialAccount financialAccount = new FinancialAccount();
		financialAccount.setAccountNumber(String.valueOf(UniqueIdGenerator.get()));
		financialAccount.setAccountType(FinancialAccountTypeConstant.STUDENT_ACCOUNT);
		return financialAccount;
	}

	@Override
	public FinancialAccount createOrganizationAccount(final Long organizationId) {
		FinancialAccount financialAccount = new FinancialAccount();
		financialAccount.setAccountNumber(String.valueOf(UniqueIdGenerator.get()));
		financialAccount.setAccountType(FinancialAccountTypeConstant.STUDENT_ACCOUNT);
		return financialAccount;
	}

	@Override
	public Collection<Transaction> findStudentTransactions(final Long studentId) {
		return null;
	}

	@Override
	public Collection<Transaction> findBranchTransactions(final Long branchId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Transaction> findEmployeeTransactions(final Long employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Transaction> findOrganizationTransactions(final Long organizationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPayment(final Transaction transaction, final String accountNumber) {
		transaction.setType(TransactionTypeConstant.PAYMENT);
		this.transactionDao.persist(transaction);
	}

	@Override
	public void addExpense(final Transaction transaction, final String accountNUmber) {
		transaction.setType(TransactionTypeConstant.EXPENSES);
		this.transactionDao.persist(transaction);

	}

	@Override
	public void addDeposit(final Transaction transaction, final String accountNUmber) {
		// TODO Auto-generated method stub

	}

}
