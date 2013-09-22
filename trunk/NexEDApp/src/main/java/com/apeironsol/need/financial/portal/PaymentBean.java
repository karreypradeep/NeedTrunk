package com.apeironsol.need.financial.portal;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.model.Transaction;
import com.apeironsol.need.core.portal.AbstractStudentBean;
import com.apeironsol.need.financial.service.FinancialAccountService;

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
