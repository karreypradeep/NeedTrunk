package com.apeironsol.nexed.financial.portal;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.apeironsol.nexed.core.model.Student;
import com.apeironsol.nexed.core.model.Transaction;
import com.apeironsol.nexed.core.portal.AbstractStudentBean;
import com.apeironsol.nexed.financial.service.FinancialAccountService;

public class PaymentBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long		serialVersionUID	= 3711093432881884499L;

	@Resource
	private FinancialAccountService	financialAccountService;

	@Resource
	private AbstractStudentBean		studentBean;

	private Transaction				transaction;

	@PostConstruct
	public void init() {
		this.setTransaction(new Transaction());
	}

	public Collection<Transaction> getStudentPayments() {
		Student student = this.studentBean.getStudent();
		return this.financialAccountService.findStudentTransactions(student.getId());
	}

	public Transaction getTransaction() {
		return this.transaction;
	}

	public void setTransaction(final Transaction transaction) {
		this.transaction = transaction;
	}

}
