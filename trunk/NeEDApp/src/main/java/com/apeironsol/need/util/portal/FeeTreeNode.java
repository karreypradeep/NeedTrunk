package com.apeironsol.need.util.portal;

import java.io.Serializable;
import java.util.Date;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.apeironsol.need.util.constants.PaymentFrequencyConstant;

public class FeeTreeNode extends DefaultTreeNode implements Serializable {

	/**
	 * 
	 */
	private static final long			serialVersionUID	= -3901011810149973641L;

	private PaymentFrequencyConstant	paymentFrequency;

	private Double						totalAmount;

	private Date						dueDate;

	private Double						dueAmount;

	private String						name;

	private Boolean						optionsNode;

	public FeeTreeNode() {
		super();
	}

	public FeeTreeNode(final Object data, final TreeNode parent) {
		super(data, parent);

	}

	public FeeTreeNode(final String type, final Object data, final TreeNode parent) {
		super(type, data, parent);
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public PaymentFrequencyConstant getPaymentFrequency() {
		return this.paymentFrequency;
	}

	public void setPaymentFrequency(final PaymentFrequencyConstant paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}

	public Double getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(final Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(final Date dueDate) {
		this.dueDate = dueDate;
	}

	public Double getDueAmount() {
		return this.dueAmount;
	}

	public void setDueAmount(final Double dueAmount) {
		this.dueAmount = dueAmount;
	}

	public Boolean getOptionsNode() {
		return this.optionsNode;
	}

	public void setOptionsNode(final Boolean optionsNode) {
		this.optionsNode = optionsNode;
	}

}
