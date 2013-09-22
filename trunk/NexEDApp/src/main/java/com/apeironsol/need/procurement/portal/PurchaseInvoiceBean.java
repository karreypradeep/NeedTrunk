/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.procurement.portal;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.EnumSet;

import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.financial.model.BranchBankAccount;
import com.apeironsol.need.financial.model.BranchCreditAccount;
import com.apeironsol.need.financial.service.BranchBankAccountService;
import com.apeironsol.need.financial.service.BranchCreditAccountService;
import com.apeironsol.need.procurement.model.PurchaseInvoice;
import com.apeironsol.need.procurement.model.PurchaseOrder;
import com.apeironsol.need.procurement.service.PurchaseInvoiceService;
import com.apeironsol.need.procurement.service.PurchaseOrderService;
import com.apeironsol.need.util.constants.PurchaseOrderStatusConstant;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

/**
 * JSF managed for purchase invoice.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class PurchaseInvoiceBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long				serialVersionUID	= 2649327175051422697L;

	/**
	 * Supplier.
	 */
	private PurchaseInvoice					purchaseInvoice;

	/**
	 * Collection of suppliers for current branch.
	 */
	private Collection<PurchaseInvoice>		purchaseInvoicesForCurrentBranch;

	@Resource
	private PurchaseInvoiceService			purchaseInvoiceService;

	@Resource
	private PurchaseOrderService			purchaseOrderService;

	/**
	 * Collection of purchase orders approved for current branch.
	 */
	private Collection<PurchaseOrder>		purchaseOrdersApprovedForCurrentBranch;

	/**
	 * Indicator for specifying to load PurchaseInvoices from database.
	 */
	private boolean							loadPurchaseInvoicesForCurrentBranchIndicator;

	/**
	 * Building blocks for type expenses.
	 */
	private Collection<BranchBankAccount>	branchBakAccounts;

	/**
	 * Branch expense service.
	 */
	@Resource
	private BranchBankAccountService		branchBankAccountService;

	/**
	 * Branch expense service.
	 */
	@Resource
	private BranchCreditAccountService		branchCreditAccountService;

	/**
	 * Building blocks for type expenses.
	 */
	private Collection<BranchCreditAccount>	branchCreditAccounts;

	@Override
	public void onTabChange() {
		this.setViewOrNewAction(false);
		this.setLoadPurchaseInvoicesForCurrentBranchIndicator(true);
	}

	/**
	 * @return the purchaseInvoice
	 */
	public PurchaseInvoice getPurchaseInvoice() {
		return this.purchaseInvoice;
	}

	/**
	 * @param purchaseInvoice
	 *            the purchaseInvoice to set
	 */
	public void setPurchaseInvoice(final PurchaseInvoice purchaseInvoice) {
		this.purchaseInvoice = purchaseInvoice;
	}

	/**
	 * @return the suppliersForCurrentBranch
	 */
	public Collection<PurchaseInvoice> getPurchaseInvoicesForCurrentBranch() {
		return this.purchaseInvoicesForCurrentBranch;
	}

	/**
	 * @param suppliersForCurrentBranch
	 *            the suppliersForCurrentBranch to set
	 */
	public void setPurchaseInvoicesForCurrentBranch(final Collection<PurchaseInvoice> purchaseInvoicesForCurrentBranch) {
		this.purchaseInvoicesForCurrentBranch = purchaseInvoicesForCurrentBranch;
	}

	public void loadAllPurchaseInvoicesForCurrentBranch() {
		if (this.isLoadPurchaseInvoicesForCurrentBranchIndicator()) {
			this.setPurchaseInvoicesForCurrentBranch(this.purchaseInvoiceService.findPurchaseInvoicesByBranchId(this.sessionBean.getCurrentBranch().getId()));
			this.branchBakAccounts = this.branchBankAccountService.findBranchBankAccountByBranchId(this.sessionBean.getCurrentBranch().getId());
			this.branchCreditAccounts = this.branchCreditAccountService.findBranchCreditAccountByBranchId(this.sessionBean.getCurrentBranch().getId());
		}
	}

	/**
	 * @return the loadSuppliersForCurrentBranchIndicator
	 */
	public boolean isLoadPurchaseInvoicesForCurrentBranchIndicator() {
		return this.loadPurchaseInvoicesForCurrentBranchIndicator;
	}

	/**
	 * @param loadSuppliersForCurrentBranchIndicator
	 *            the loadSuppliersForCurrentBranchIndicator to set
	 */
	public void setLoadPurchaseInvoicesForCurrentBranchIndicator(final boolean loadPurchaseInvoicesForCurrentBranchIndicator) {
		this.loadPurchaseInvoicesForCurrentBranchIndicator = loadPurchaseInvoicesForCurrentBranchIndicator;
	}

	public String savePurchaseInvoice() {
		this.purchaseInvoice = this.purchaseInvoiceService.savePurchaseInvoice(this.purchaseInvoice);
		this.setLoadPurchaseInvoicesForCurrentBranchIndicator(true);
		this.setViewOrNewAction(false);
		return null;
	}

	public String removePurchaseInvoice() {
		this.purchaseInvoiceService.removePurchaseInvoice(this.purchaseInvoice);
		this.setLoadPurchaseInvoicesForCurrentBranchIndicator(true);
		this.setViewOrNewAction(false);
		return null;
	}

	/**
	 * Removes academic year Income from database.
	 */
	public String calcelAction() {
		this.setViewOrNewAction(false);
		return null;
	}

	/**
	 * Removes academic year Income from database.
	 */
	public String viewPurchaseInvoice() {
		this.setViewOrNewAction(true);
		return null;
	}

	/**
	 * Create new academic year Income.
	 */
	public String newPurchaseInvoice() {
		this.purchaseInvoice = new PurchaseInvoice();
		this.purchaseInvoice.setBranch(this.sessionBean.getCurrentBranch());
		this.setViewOrNewAction(true);
		this.loadPurchaseOrdersWithStateApprovedForCurrentBranch();
		return null;
	}

	/**
	 * @return the purchaseOrdersApprovedForCurrentBranch
	 */
	public Collection<PurchaseOrder> getPurchaseOrdersApprovedForCurrentBranch() {
		return this.purchaseOrdersApprovedForCurrentBranch;
	}

	/**
	 * @param purchaseOrdersApprovedForCurrentBranch
	 *            the purchaseOrdersApprovedForCurrentBranch to set
	 */
	public void setPurchaseOrdersApprovedForCurrentBranch(final Collection<PurchaseOrder> purchaseOrdersApprovedForCurrentBranch) {
		this.purchaseOrdersApprovedForCurrentBranch = purchaseOrdersApprovedForCurrentBranch;
	}

	/**
	 * Loads purchase orders approved for current branch.
	 */
	public void loadPurchaseOrdersWithStateApprovedForCurrentBranch() {
		this.setPurchaseOrdersApprovedForCurrentBranch(this.purchaseOrderService.findPurchaseOrdersByBranchIdAndPurchaseOrderStatus(this.sessionBean
				.getCurrentBranch().getId(), EnumSet.of(PurchaseOrderStatusConstant.APPROVED)));
	}

	/**
	 * Pre-process method for processing pdf file to be exported.
	 * 
	 * @param document
	 *            document to be pre processed.
	 * @throws IOException
	 *             IOException.
	 * @throws BadElementException
	 *             Bad element exception.
	 * @throws DocumentException
	 *             document exception.
	 */
	public void preProcessPDF(final Object document) throws IOException, BadElementException, DocumentException {
		final Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);
		final ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		try {
			final String logo = servletContext.getRealPath("") + File.separator + "images" + File.separator + "school_logo.png";
			pdf.add(Image.getInstance(logo));
		} catch (final IOException e) {
			// TODO: handle exception
		}
		pdf.addTitle(this.sessionBean.getCurrentBranch().getName());
		pdf.addHeader("Header", "Fee collected");
	}

	public void handlePaymentModeChange() {
		this.purchaseInvoice.setChequeNumber(null);
		this.purchaseInvoice.setBranchBankAccount(null);
	}

	public void handleBranchAccountTypeChange() {
		this.purchaseInvoice.setChequeNumber(null);
		this.purchaseInvoice.setBranchBankAccount(null);
		this.purchaseInvoice.setBranchCreditAccount(null);
	}

	/**
	 * @return the branchBakAccounts
	 */
	public Collection<BranchBankAccount> getBranchBakAccounts() {
		return this.branchBakAccounts;
	}

	/**
	 * @param branchBakAccounts
	 *            the branchBakAccounts to set
	 */
	public void setBranchBakAccounts(final Collection<BranchBankAccount> branchBakAccounts) {
		this.branchBakAccounts = branchBakAccounts;
	}

	public Collection<BranchCreditAccount> getBranchCreditAccounts() {
		return this.branchCreditAccounts;
	}

	public void setBranchCreditAccounts(final Collection<BranchCreditAccount> branchCreditAccounts) {
		this.branchCreditAccounts = branchCreditAccounts;
	}

}
