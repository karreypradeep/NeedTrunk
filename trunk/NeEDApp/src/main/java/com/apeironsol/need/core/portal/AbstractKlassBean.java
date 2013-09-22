package com.apeironsol.need.core.portal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Subject;
import com.apeironsol.need.financial.model.KlassLevelFee;
import com.apeironsol.need.financial.model.KlassLevelFeeCatalog;
import com.apeironsol.need.financial.service.KlassLevelFeeService;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.FeeTypeConstant;
import com.apeironsol.need.util.po.AcademicYearKlassFeePO;
import com.apeironsol.need.util.portal.FeeTreeNode;
import com.apeironsol.need.util.portal.KlassFeeTreeNode;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.framework.exception.BusinessException;

public abstract class AbstractKlassBean extends AbstractTabbedBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long						serialVersionUID	= -5004428744395023869L;

	@Resource
	protected KlassLevelFeeService					klassLevelFeeService;

	/**
	 * Collection of class fees.
	 */
	protected Collection<KlassLevelFee>				klassLevelFees;

	protected boolean								loadKlassFeesForCurrentAcademicYearFlag;

	protected AcademicYearKlassFeePO				currentAcademicYearKlassFeePO;

	protected Collection<AcademicYearKlassFeePO>	academicYearKlassFeePOs;

	private boolean									loadKlassSubjects;

	private Collection<Subject>						klassSubjects;

	/**
	 * Indicator to load income types from database.
	 */
	private boolean									buildTree;

	/**
	 * Root node for tree.
	 */
	private final TreeNode							root				= new DefaultTreeNode("klassFee", null);

	@Override
	public void onTabChange() {

		if (this.getActiveTabIndex() == 1) {
			this.onKlassFeeTabSelect();
		}

	}

	public void onKlassFeeTabSelect() {

		this.generateAcademicYearsKlassFee();

		this.loadKlassFeeTreeTable();

	}

	public void loadKlassFeeTreeTable() {

		try {

			if (this.loadKlassFeesForCurrentAcademicYearFlag) {

				Klass currentKlass = this.sessionBean.getCurrentKlass();

				Date currentDate = DateUtil.getSystemDate();

				Branch branch = this.sessionBean.getCurrentBranch();

				AcademicYear academicYear = this.academicYearService.findAcademicYearByBranchIdAndDate(branch.getId(), currentDate);

				if (academicYear == null) {

					academicYear = this.academicYearService.findLatestAcademicYear(branch.getId());

				}

				if (academicYear != null) {
					this.klassLevelFees = this.klassLevelFeeService.findAllKlassFeesByKlassIdAndAcademicYearId(currentKlass.getId(), academicYear.getId());

					this.buildKlassFeeTreeTable(academicYear, this.klassLevelFees);
				}

				this.loadKlassFeesForCurrentAcademicYearFlag = false;

			}

		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		} catch (Exception e) {
			ViewExceptionHandler.handle(e);
		}

	}

	/**
	 * Builds tree for class level fees.
	 */
	public void buildKlassFeeTreeTable(final AcademicYear academicYear, final Collection<KlassLevelFee> klassLevelFees) {

		this.removeAllChildsOfRootNode(this.root);

		double totalKlassAmountForDayScholar = 0, totalKlassAmountForResidential = 0;

		if (this.klassLevelFees != null) {

			for (KlassLevelFee klassLevelFee : this.klassLevelFees) {

				FeeTreeNode klassFeeTreeNode = new KlassFeeTreeNode();
				klassFeeTreeNode.setName(klassLevelFee.getBuildingBlock().getName());
				klassFeeTreeNode.setTotalAmount(klassLevelFee.getAmount());
				klassFeeTreeNode.setPaymentFrequency(klassLevelFee.getPaymentFrequency());
				klassFeeTreeNode.setDueAmount(0d);

				TreeNode klassFeeTree = new DefaultTreeNode(klassFeeTreeNode, this.root);
				klassFeeTree.setExpanded(false);

				Collection<KlassLevelFeeCatalog> klassLevelFeeCatalogs = this.klassLevelFeeService.findAllKlassFeePaymentByKlassFeeId(klassLevelFee.getId());

				for (KlassLevelFeeCatalog klassLevelFeeCatalog : klassLevelFeeCatalogs) {

					FeeTreeNode klassFeeTreeNode1 = new KlassFeeTreeNode();

					klassFeeTreeNode1.setName(klassLevelFee.getBuildingBlock().getName());
					klassFeeTreeNode1.setTotalAmount(klassLevelFeeCatalog.getAmount());
					klassFeeTreeNode1.setDueDate(klassLevelFeeCatalog.getDueDate());

					new DefaultTreeNode(klassFeeTreeNode1, klassFeeTree);
				}

				totalKlassAmountForResidential = totalKlassAmountForResidential + klassLevelFee.getAmount();

				if (!FeeTypeConstant.HOSTEL_FEE.equals(klassLevelFee.getBuildingBlock().getFeeType())) {
					totalKlassAmountForDayScholar = totalKlassAmountForDayScholar + klassLevelFee.getAmount();
				}
			}

			this.currentAcademicYearKlassFeePO = new AcademicYearKlassFeePO();

			this.currentAcademicYearKlassFeePO.setAcademicYear(academicYear);

			this.currentAcademicYearKlassFeePO.setKlassFees(this.klassLevelFees);

			this.currentAcademicYearKlassFeePO.setTotalKlassFeeForDayScholar(totalKlassAmountForDayScholar);
			this.currentAcademicYearKlassFeePO.setTotalKlassFeeForResidentialStudent(totalKlassAmountForResidential);
		}
	}

	/**
	 * Removes all child nodes of the supplied root node.
	 */
	private void removeAllChildsOfRootNode(final TreeNode rootNode) {
		if (rootNode != null && rootNode.getChildCount() > 0) {
			TreeNode[] array = rootNode.getChildren().toArray(new TreeNode[rootNode.getChildCount()]);
			for (TreeNode child : array) {
				child.setParent(null);
				child = null;
			}
		}
	}

	public void generateAcademicYearsKlassFee() {

		Branch branch = this.sessionBean.getCurrentBranch();

		Klass klass = this.sessionBean.getCurrentKlass();

		this.academicYearKlassFeePOs = new ArrayList<AcademicYearKlassFeePO>();

		try {
			Collection<AcademicYear> academicYears = this.academicYearService.findAcademicYearsByBranchId(branch.getId());

			for (AcademicYear academicYear : academicYears) {

				AcademicYearKlassFeePO academicYearKlassFeePO = new AcademicYearKlassFeePO();

				Collection<KlassLevelFee> klassLevelFees = this.klassLevelFeeService.findAllKlassFeesByKlassIdAndAcademicYearId(klass.getId(),
						academicYear.getId());

				double totalKlassAmountForResidential = 0.0, totalKlassAmountForDayScholar = 0;

				for (KlassLevelFee klassLevelFee : klassLevelFees) {
					totalKlassAmountForResidential = totalKlassAmountForResidential + klassLevelFee.getAmount();
					if (!FeeTypeConstant.HOSTEL_FEE.equals(klassLevelFee.getBuildingBlock().getFeeType())) {
						totalKlassAmountForDayScholar = totalKlassAmountForDayScholar + klassLevelFee.getAmount();
					}
				}

				academicYearKlassFeePO.setAcademicYear(academicYear);
				academicYearKlassFeePO.setTotalKlassFeeForDayScholar(totalKlassAmountForDayScholar);
				academicYearKlassFeePO.setTotalKlassFeeForResidentialStudent(totalKlassAmountForResidential);
				academicYearKlassFeePO.setKlassFees(klassLevelFees);
				this.academicYearKlassFeePOs.add(academicYearKlassFeePO);

			}

		} catch (BusinessException e) {
			ViewExceptionHandler.handle(e);
		}

	}

	public AcademicYearKlassFeePO getCurrentAcademicYearKlassFeePO() {
		return this.currentAcademicYearKlassFeePO;
	}

	public void setCurrentAcademicYearKlassFeePO(final AcademicYearKlassFeePO currentAcademicYearKlassFeePO) {
		this.currentAcademicYearKlassFeePO = currentAcademicYearKlassFeePO;
	}

	public Collection<AcademicYearKlassFeePO> getAcademicYearKlassFeePOs() {
		return this.academicYearKlassFeePOs;
	}

	public void setAcademicYearKlassFeePOs(final Collection<AcademicYearKlassFeePO> academicYearKlassFeePOs) {
		this.academicYearKlassFeePOs = academicYearKlassFeePOs;
	}

	public boolean isLoadKlassFeesForCurrentAcademicYearFlag() {
		return this.loadKlassFeesForCurrentAcademicYearFlag;
	}

	public void setLoadKlassFeesForCurrentAcademicYearFlag(final boolean loadKlassFeesForCurrentAcademicYearFlag) {
		this.loadKlassFeesForCurrentAcademicYearFlag = loadKlassFeesForCurrentAcademicYearFlag;
	}

	public boolean isLoadKlassSubjects() {
		return this.loadKlassSubjects;
	}

	public void setLoadKlassSubjects(final boolean loadKlassSubjects) {
		this.loadKlassSubjects = loadKlassSubjects;
	}

	public Collection<Subject> getKlassSubjects() {
		return this.klassSubjects;
	}

	public void setKlassSubjects(final Collection<Subject> klassSubjects) {
		this.klassSubjects = klassSubjects;
	}

	public boolean isBuildTree() {
		return this.buildTree;
	}

	public void setBuildTree(final boolean buildTree) {
		this.buildTree = buildTree;
	}

	public TreeNode getRoot() {
		return this.root;
	}

}
