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

import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.procurement.model.PurchaseOrder;
import com.apeironsol.need.procurement.model.PurchaseOrderItem;
import com.apeironsol.need.procurement.model.PurchaseOrderStatus;
import com.apeironsol.need.procurement.model.Supplier;
import com.apeironsol.need.procurement.service.PurchaseOrderItemService;
import com.apeironsol.need.procurement.service.PurchaseOrderService;
import com.apeironsol.need.procurement.service.PurchaseOrderStatusService;
import com.apeironsol.need.procurement.service.SupplierService;
import com.apeironsol.need.security.portal.GrantedAuthorityBean;
import com.apeironsol.need.util.constants.PurchaseOrderStatusConstant;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

/**
 * JSF managed for financial income.
 *
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class PurchaseOrderBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long				serialVersionUID	= 7717831606295888121L;

	/**
	 * Purchase order.
	 */
	private PurchaseOrder					purchaseOrder;

	/**
	 * Purchase order service.
	 */
	@Resource
	private PurchaseOrderService			purchaseOrderService;

	/**
	 * Collection containing purchase orders for the current branch.
	 */
	private Collection<PurchaseOrder>		purchaseOrdersForCurrentBranch;

	/**
	 * Boolean indicating to load purchase orders for the current branch from
	 * database..
	 */
	private boolean							loadPurchaseOrdersForCurrentBranch;

	/**
	 * Collection containing purchase orders for the current branch.
	 */
	private Collection<PurchaseOrderItem>	purchaseOrderItemsForCurrentPurchseOrder;

	/**
	 * Collection containing purchase orders for the current branch.
	 */
	private Collection<PurchaseOrderStatus>	purchaseOrderStatusForCurrentPurchseOrder;

	/**
	 * Purchase order item service.
	 */
	@Resource
	private PurchaseOrderItemService		purchaseOrderItemService;

	/**
	 * Purchase order status service.
	 */
	@Resource
	private PurchaseOrderStatusService		purchaseOrderStatusService;

	@Resource
	private SupplierService					supplierService;

	/**
	 * Purchase order status for update.
	 */
	private PurchaseOrderStatusConstant		purchaseOrderStatusForUpdate;

	/**
	 * Remarks for purchase order status.
	 */
	private String							remarksForNewPurchaseOrderStatus;
	private String							purchaseOrderStatusForUpdateString;

	/**
	 * Granted authority bean.
	 */
	@Resource
	private GrantedAuthorityBean			grantedAuthorityBean;

	/**
	 * Collection of suppliers for current branch.
	 */
	private Collection<Supplier>			suppliersForCurrentBranch;

	/**
	 * @return the purchaseOrderStatusForUpdateString
	 */
	public String getPurchaseOrderStatusForUpdateString() {
		return this.purchaseOrderStatusForUpdateString;
	}

	/**
	 * @param purchaseOrderStatusForUpdateString
	 *            the purchaseOrderStatusForUpdateString to set
	 */
	public void setPurchaseOrderStatusForUpdateString(final String purchaseOrderStatusForUpdateString) {
		this.purchaseOrderStatusForUpdateString = purchaseOrderStatusForUpdateString;
		this.purchaseOrderStatusForUpdate = PurchaseOrderStatusConstant.valueOf(purchaseOrderStatusForUpdateString);
	}

	@Override
	public void onTabChange() {

	}

	/**
	 * @return the purchaseOrder
	 */
	public PurchaseOrder getPurchaseOrder() {
		return this.purchaseOrder;
	}

	/**
	 * @param purchaseOrder
	 *            the purchaseOrder to set
	 */
	public void setPurchaseOrder(final PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public void savePurchaseOrder() {
		this.purchaseOrder.setTotalCost(getTotalAmountForPurchaseOrder());
		this.purchaseOrder = this.purchaseOrderService.savePurchaseOrder(this.purchaseOrder);
		setLoadPurchaseOrdersForCurrentBranch(true);
	}

	public void removePurchaseOrder() {
		this.purchaseOrderService.removePurchaseOrder(this.purchaseOrder);
		setLoadPurchaseOrdersForCurrentBranch(true);
	}

	public void loadAllPurchaseOrdersForCurrentBranch() {
		if (isLoadPurchaseOrdersForCurrentBranch()) {
			this.purchaseOrdersForCurrentBranch = this.purchaseOrderService.findPurchaseOrdersByBranchId(this.sessionBean.getCurrentBranch().getId());
			setLoadPurchaseOrdersForCurrentBranch(false);
		}
	}

	/**
	 * @return the purchaseOrdersForCurrentBranch
	 */
	public Collection<PurchaseOrder> getPurchaseOrdersForCurrentBranch() {
		return this.purchaseOrdersForCurrentBranch;
	}

	/**
	 * @param purchaseOrdersForCurrentBranch
	 *            the purchaseOrdersForCurrentBranch to set
	 */
	public void setPurchaseOrdersForCurrentBranch(final Collection<PurchaseOrder> purchaseOrdersForCurrentBranch) {
		this.purchaseOrdersForCurrentBranch = purchaseOrdersForCurrentBranch;
	}

	/**
	 * @return the loadPurchaseOrdersForCurrentBranch
	 */
	public boolean isLoadPurchaseOrdersForCurrentBranch() {
		return this.loadPurchaseOrdersForCurrentBranch;
	}

	/**
	 * @param loadPurchaseOrdersForCurrentBranch
	 *            the loadPurchaseOrdersForCurrentBranch to set
	 */
	public void setLoadPurchaseOrdersForCurrentBranch(final boolean loadPurchaseOrdersForCurrentBranch) {
		this.loadPurchaseOrdersForCurrentBranch = loadPurchaseOrdersForCurrentBranch;
	}

	/**
	 * Create new purchase order.
	 */
	public String newPurchaseOrder() {
		this.purchaseOrder = new PurchaseOrder();
		this.purchaseOrder.setBranch(this.sessionBean.getCurrentBranch());
		setViewOrNewAction(true);
		this.purchaseOrder.setPurchaseOrderCurrentStatusConstant(PurchaseOrderStatusConstant.NEW);
		loadSupplierForCurrentBranch();
		this.purchaseOrderStatusForCurrentPurchseOrder = null;
		this.purchaseOrderItemsForCurrentPurchseOrder = null;
		setActiveTabIndex(0);
		return null;
	}

	/**
	 * @return the purchaseOrderStatusConstant
	 */
	public PurchaseOrderStatusConstant getCurrentPurchaseOrderStatusConstant() {
		return this.purchaseOrder.getPurchaseOrderCurrentStatusConstant() == null ? PurchaseOrderStatusConstant.NEW : this.purchaseOrder
				.getPurchaseOrderCurrentStatusConstant();
	}

	/**
	 * Removes academic year Income from database.
	 */
	public String calcelAction() {
		setViewOrNewAction(false);
		return null;
	}

	public String viewPruchaseOrder() {
		setViewOrNewAction(true);
		loadAllPurchaseOrderItemsForPurchaseOrder();
		loadAllPurchaseOrderStatusForPurchaseOrder();
		return null;
	}

	public void loadAllPurchaseOrderItemsForPurchaseOrder() {
		if (getPurchaseOrder() != null) {
			this.purchaseOrderItemsForCurrentPurchseOrder = this.purchaseOrderItemService.findPurchaseOrderItemsByPurchaseOrderId(getPurchaseOrder().getId());
		}
	}

	/**
	 * @return the purchaseOrderItemsForCurrentPurchseOrder
	 */
	public Collection<PurchaseOrderItem> getPurchaseOrderItemsForCurrentPurchseOrder() {
		return this.purchaseOrderItemsForCurrentPurchseOrder;
	}

	/**
	 * @param purchaseOrderItemsForCurrentPurchseOrder
	 *            the purchaseOrderItemsForCurrentPurchseOrder to set
	 */
	public void setPurchaseOrderItemsForCurrentPurchseOrder(final Collection<PurchaseOrderItem> purchaseOrderItemsForCurrentPurchseOrder) {
		this.purchaseOrderItemsForCurrentPurchseOrder = purchaseOrderItemsForCurrentPurchseOrder;
	}

	public Double getTotalAmountForPurchaseOrder() {
		Double totalCost = 0.0;
		if (this.purchaseOrderItemsForCurrentPurchseOrder != null && !this.purchaseOrderItemsForCurrentPurchseOrder.isEmpty()) {
			for (PurchaseOrderItem purchaseOrderItem : this.purchaseOrderItemsForCurrentPurchseOrder) {
				totalCost += purchaseOrderItem.getToatlCost();
			}
		}
		getPurchaseOrder().setTotalCost(totalCost);
		return totalCost;
	}

	public boolean isSaveButtonDisabled() {
		return !getCurrentPurchaseOrderStatusConstant().equals(PurchaseOrderStatusConstant.NEW);
	}

	public void loadAllPurchaseOrderStatusForPurchaseOrder() {
		if (getPurchaseOrder() != null) {
			this.purchaseOrderStatusForCurrentPurchseOrder = this.purchaseOrderStatusService.findPurchaseOrderStatusByPurchaseOrderId(getPurchaseOrder()
					.getId());
		}
	}

	/**
	 * @return the purchaseOrderItemsStatusForCurrentPurchseOrder
	 */
	public Collection<PurchaseOrderStatus> getPurchaseOrderStatusForCurrentPurchseOrder() {
		return this.purchaseOrderStatusForCurrentPurchseOrder;
	}

	/**
	 * @param purchaseOrderItemsStatusForCurrentPurchseOrder
	 *            the purchaseOrderItemsStatusForCurrentPurchseOrder to set
	 */
	public void setPurchaseOrderStatusForCurrentPurchseOrder(final Collection<PurchaseOrderStatus> purchaseOrderStatusForCurrentPurchseOrder) {
		this.purchaseOrderStatusForCurrentPurchseOrder = purchaseOrderStatusForCurrentPurchseOrder;
	}

	public String actionApprovePurchaseOrder() {
		this.purchaseOrder = this.purchaseOrderService.savePurchaseOrder(this.purchaseOrder);
		return null;
	}

	/**
	 * @return the purchaseOrderStatusForUpdate
	 */
	public PurchaseOrderStatusConstant getPurchaseOrderStatusForUpdate() {
		return this.purchaseOrderStatusForUpdate;
	}

	/**
	 * @return the remarksForNewPurchaseOrderStatus
	 */
	public String getRemarksForNewPurchaseOrderStatus() {
		return this.remarksForNewPurchaseOrderStatus;
	}

	/**
	 * @param remarksForNewPurchaseOrderStatus
	 *            the remarksForNewPurchaseOrderStatus to set
	 */
	public void setRemarksForNewPurchaseOrderStatus(final String remarksForNewPurchaseOrderStatus) {
		this.remarksForNewPurchaseOrderStatus = remarksForNewPurchaseOrderStatus;
	}

	public String updatePurchaseOrderStatus() {
		this.purchaseOrder = this.purchaseOrderService.updatePurchaseOrderStatus(this.purchaseOrder, this.purchaseOrderStatusForUpdate,
				this.remarksForNewPurchaseOrderStatus);
		loadAllPurchaseOrderStatusForPurchaseOrder();
		setLoadPurchaseOrdersForCurrentBranch(true);
		return null;
	}

	/**
	 * Returns true if request for approval button has to be enabled. The button
	 * is enabled only if the purchase order is in status NEW.
	 *
	 * @return true if request for approval button has to be enabled.
	 */
	public boolean isRequestForApprovalButtonEnabled() {
		return this.grantedAuthorityBean.isUserAllowedToCreatePurchaseOrder() && this.purchaseOrder != null
				&& this.purchaseOrder.getPurchaseOrderNumber() != null
				&& this.purchaseOrder.getPurchaseOrderCurrentStatusConstant().equals(PurchaseOrderStatusConstant.NEW)
				&& getTotalAmountForPurchaseOrder().doubleValue() > 0.0;
	}

	/**
	 * Returns true if request for approval button has to be enabled. The button
	 * is enabled only if the purchase order is in status REQUEST_FOR_APPROVAL.
	 *
	 * @return true if request for approval button has to be enabled.
	 */
	public boolean isApproveOrRejectButtonEnabled() {
		return this.grantedAuthorityBean.isUserAllowedToApprovePurchaseOrder() && this.purchaseOrder != null
				&& this.purchaseOrder.getPurchaseOrderNumber() != null
				&& this.purchaseOrder.getPurchaseOrderCurrentStatusConstant().equals(PurchaseOrderStatusConstant.REQUEST_FOR_APPROVAL);
	}

	/**
	 * Returns true if request for approval button has to be enabled. The button
	 * is enabled only if the purchase order is in status REQUEST_FOR_APPROVAL.
	 *
	 * @return true if request for approval button has to be enabled.
	 */
	public boolean isClosePurchaseOrderButtonEnabled() {
		return (this.grantedAuthorityBean.isUserAllowedToCreatePurchaseOrder() || this.grantedAuthorityBean.isUserAllowedToUdatePurchaseOrder() || this.grantedAuthorityBean
				.isUserAllowedToApprovePurchaseOrder())
				&& this.purchaseOrder != null
				&& this.purchaseOrder.getPurchaseOrderNumber() != null
				&& (this.purchaseOrder.getPurchaseOrderCurrentStatusConstant().equals(PurchaseOrderStatusConstant.APPROVED) || this.purchaseOrder
						.getPurchaseOrderCurrentStatusConstant().equals(PurchaseOrderStatusConstant.REJECTED));
	}

	public boolean isPurchaseOrderAllowedToDelete(final PurchaseOrder purchaseOrder) {
		return purchaseOrder.getPurchaseOrderCurrentStatusConstant().equals(PurchaseOrderStatusConstant.NEW);
	}

	/**
	 * Returns true if logged in user has role to either create or update or
	 * delete academic year holidays.
	 *
	 * @return true if logged in user has role to either create or update or
	 *         delete academic year holidays.
	 */
	public boolean isUserAllowedToApprovePurchaseOrder() {
		return this.grantedAuthorityBean.isUserAllowedToUpdatePurchaseOrder();
	}

	/**
	 * Returns true if other sub table like items,status have to be disabled.
	 *
	 * @return true if other sub table like items,status have to be disabled.
	 */
	public boolean isDisableSubTab() {
		if (this.purchaseOrder != null && this.purchaseOrder.getId() != null) {
			return false;
		}
		return true;
	}

	/**
	 * @return the suppliersForCurrentBranch
	 */
	public Collection<Supplier> getSuppliersForCurrentBranch() {
		return this.suppliersForCurrentBranch;
	}

	/**
	 * @param suppliersForCurrentBranch
	 *            the suppliersForCurrentBranch to set
	 */
	public void setSuppliersForCurrentBranch(final Collection<Supplier> suppliersForCurrentBranch) {
		this.suppliersForCurrentBranch = suppliersForCurrentBranch;
	}

	/**
	 * Loads purchase orders approved for current branch.
	 */
	public void loadSupplierForCurrentBranch() {
		setSuppliersForCurrentBranch(this.supplierService.findSuppliersByBranchId(this.sessionBean.getCurrentBranch().getId()));
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
		Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		try {
			String logo = servletContext.getRealPath("") + File.separator + "images" + File.separator
					+ "school_logo.png";
			pdf.add(Image.getInstance(logo));
		} catch (IOException e) {
			// TODO: handle exception
		}
		pdf.addTitle(this.sessionBean.getCurrentBranch().getName());
		pdf.addHeader("Header", "Fee collected");
	}

}
