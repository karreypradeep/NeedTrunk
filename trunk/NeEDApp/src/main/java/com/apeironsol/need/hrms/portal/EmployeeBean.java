/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.hrms.portal;

/**
 * View employees class.
 *
 * @author Sunny
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageInputStream;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.model.ProfilePicture;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.core.service.BuildingBlockService;
import com.apeironsol.need.core.service.ProfilePictureService;
import com.apeironsol.need.hrms.dataobject.EmployeeDO;
import com.apeironsol.need.hrms.model.Employee;
import com.apeironsol.need.hrms.model.EmployeeDesignation;
import com.apeironsol.need.hrms.service.EmployeeDesignationService;
import com.apeironsol.need.hrms.service.EmployeeService;
import com.apeironsol.need.security.model.UserAccount;
import com.apeironsol.need.util.constants.BuildingBlockConstant;
import com.apeironsol.need.util.constants.EmploymentCurrentStatusConstant;
import com.apeironsol.need.util.constants.UserAccountTypeConstant;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.need.util.searchcriteria.EmployeeSearchCriteria;

@Named
@Scope("session")
public class EmployeeBean extends AbstractTabbedBean {

	private static final Logger			log								= Logger.getLogger(EmployeeBean.class);

	/**
	 * Unique serial version id for this class.
	 */
	private static final long			serialVersionUID				= 7261293041290339387L;

	@Resource
	private EmployeeService				employeeService;

	@Resource
	private EmployeeDesignationService	employeeDesignationService;

	@Resource
	protected ProfilePictureService		profilePictureService;

	@Resource
	private BuildingBlockService		buildingBlockService;

	private Employee					employee;

	private EmployeeDO					employeeDO;

	private Collection<EmployeeDO>		employeesSearchResults;

	private EmployeeDesignation			employeeDesignation;

	private boolean						editEmployeePersonalDetails;

	private boolean						editEmployeeAddressDetails;

	private static final String			PERSONAL						= "personal";

	private String						activeStep						= PERSONAL;

	private Collection<BuildingBlock>	buildingBlockTypeDesignations;

	private Collection<BuildingBlock>	buildingBlockTypeDepartments;

	private boolean						loadEmployeeBuildingBlockTypes	= false;

	private boolean						loadEmployeeByUserIdFlag;

	private boolean						loadCurrentEmployeeDesignationByEmpIdFlag;

	private EmployeeSearchCriteria		employeeSearchCriteria			= null;

	private byte[]						profilePicture;

	private boolean						loadProfilePictureFlag;

	public EmployeeSearchCriteria getEmployeeSearchCriteria() {
		return this.employeeSearchCriteria;
	}

	public boolean isLoadCurrentEmployeeDesignationByEmpIdFlag() {
		return this.loadCurrentEmployeeDesignationByEmpIdFlag;
	}

	public void setLoadCurrentEmployeeDesignationByEmpIdFlag(final boolean loadCurrentEmployeeDesignationByEmpIdFlag) {
		this.loadCurrentEmployeeDesignationByEmpIdFlag = loadCurrentEmployeeDesignationByEmpIdFlag;
	}

	public boolean isLoadEmployeeByUserIdFlag() {
		return this.loadEmployeeByUserIdFlag;
	}

	public void setLoadEmployeeByUserIdFlag(final boolean loadEmployeeByUserIdFlag) {
		this.loadEmployeeByUserIdFlag = loadEmployeeByUserIdFlag;
	}

	/**
	 * @return the loadEmployeeBuildingBlockTypes
	 */
	public boolean isLoadEmployeeBuildingBlockTypes() {
		return this.loadEmployeeBuildingBlockTypes;
	}

	/**
	 * @param loadEmployeeBuildingBlockTypes
	 *            the loadEmployeeBuildingBlockTypes to set
	 */
	public void setLoadEmployeeBuildingBlockTypes(final boolean loadEmployeeBuildingBlockTypes) {
		this.loadEmployeeBuildingBlockTypes = loadEmployeeBuildingBlockTypes;
	}

	/**
	 * @return the employeeDesignation
	 */
	public EmployeeDesignation getEmployeeDesignation() {
		return this.employeeDesignation;
	}

	/**
	 * @param employeeDesignation
	 *            the employeeDesignation to set
	 */
	public void setEmployeeDesignation(final EmployeeDesignation employeeDesignation) {
		this.employeeDesignation = employeeDesignation;
	}

	/**
	 * @return the activeStep
	 */
	public String getActiveStep() {
		return this.activeStep;
	}

	/**
	 * @param activeStep
	 *            the activeStep to set
	 */
	public void setActiveStep(final String activeStep) {
		this.activeStep = activeStep;
	}

	/**
	 * @return the editEmployeePersonalDetails
	 */
	public boolean isEditEmployeePersonalDetails() {
		return this.editEmployeePersonalDetails;
	}

	/**
	 * @param editEmployeePersonalDetails
	 *            the editEmployeePersonalDetails to set
	 */
	public void setEditEmployeePersonalDetails(final boolean editEmployeePersonalDetails) {
		this.editEmployeePersonalDetails = editEmployeePersonalDetails;
	}

	/**
	 * @return the editEmployeeAddressDetails
	 */
	public boolean isEditEmployeeAddressDetails() {
		return this.editEmployeeAddressDetails;
	}

	/**
	 * @param editEmployeeAddressDetails
	 *            the editEmployeeAddressDetails to set
	 */
	public void setEditEmployeeAddressDetails(final boolean editEmployeeAddressDetails) {
		this.editEmployeeAddressDetails = editEmployeeAddressDetails;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(final Employee employee) {
		this.employee = employee;
	}

	public boolean isDisbledEmployeeSubTabs() {
		if (this.employee == null || this.employee.getId() == null) {
			return true;
		}
		return false;
	}

	public void createNewEmployee() {
		this.employee = new Employee();
		this.employeeDesignation = new EmployeeDesignation();
		this.setNewAction(true);
		this.setActiveStep(PERSONAL);
	}

	public void saveNewEmployee() {
		this.employee.setBranch(this.sessionBean.getCurrentBranch());
		this.employee.setCurrentStatus(EmploymentCurrentStatusConstant.ACTIVE);
		this.employeeService.saveNewEmployeeWizard(this.employee, this.employeeDesignation);
		this.setNewAction(false);
		this.setViewAction(false);
		this.employeeSearchCriteria.setDateOfBirth(this.employee.getDateOfBirth());
		this.searchEmployeesBySearchCriteria();
		ViewUtil.addMessage("Employee saved sucessfully.", FacesMessage.SEVERITY_INFO);
	}

	public void deleteEmployee() {
		this.employeeService.removeEmployee(this.employee);
		this.searchEmployeesBySearchCriteria();
		ViewUtil.addMessage("Employee deleted sucessfully.", FacesMessage.SEVERITY_INFO);
	}

	public void showEmployee() {

		this.setViewAction(true);
		this.setActiveTabIndex(0);
		this.employee = this.employeeDO.getEmployee();
		this.setEditEmployeePersonalDetails(false);
	}

	public String onFlowProcess(final FlowEvent event) {
		log.info("Current wizard step:" + event.getOldStep());
		log.info("Next step:" + event.getNewStep());
		return event.getNewStep();
	}

	@Override
	public void onTabChange() {
		this.editEmployeePersonalDetails = false;
	}

	public void saveEmployeePersonalDetails() {
		this.employee = this.employeeService.saveEmployee(this.employee);
		this.setEditEmployeePersonalDetails(false);
	}

	public void loadEmployeeBuildingBlocks() {
		this.loadEmployeeDesigntons();
		this.loadEmployeeDepartments();
		this.loadEmployeeBuildingBlockTypes = false;
	}

	public void loadEmployeeDesigntons() {
		if (this.loadEmployeeBuildingBlockTypes) {
			this.setBuildingBlockTypeDesignations(this.buildingBlockService.findBuildingBlocksbyBranchIdAndBuildingBlockType(this.sessionBean
					.getCurrentBranch().getId(), BuildingBlockConstant.DESIGNATION));
		}
	}

	public void loadEmployeeDepartments() {
		if (this.loadEmployeeBuildingBlockTypes) {
			this.setBuildingBlockTypeDepartments(this.buildingBlockService.findBuildingBlocksbyBranchIdAndBuildingBlockType(this.sessionBean.getCurrentBranch()
					.getId(), BuildingBlockConstant.DEPARTMENT));
		}
	}

	/**
	 * @return the buildingBlockTypeDesignations
	 */
	public Collection<BuildingBlock> getBuildingBlockTypeDesignations() {
		return this.buildingBlockTypeDesignations;
	}

	/**
	 * @param buildingBlockTypeDesignations
	 *            the buildingBlockTypeDesignations to set
	 */
	public void setBuildingBlockTypeDesignations(final Collection<BuildingBlock> buildingBlockTypeDesignations) {
		this.buildingBlockTypeDesignations = buildingBlockTypeDesignations;
	}

	/**
	 * @return the buildingBlockTypeDepartments
	 */
	public Collection<BuildingBlock> getBuildingBlockTypeDepartments() {
		return this.buildingBlockTypeDepartments;
	}

	/**
	 * @param buildingBlockTypeDepartments
	 *            the buildingBlockTypeDepartments to set
	 */
	public void setBuildingBlockTypeDepartments(final Collection<BuildingBlock> buildingBlockTypeDepartments) {
		this.buildingBlockTypeDepartments = buildingBlockTypeDepartments;
	}

	public void loadEmployeeByUserId() {
		if (this.loadEmployeeByUserIdFlag) {
			UserAccount userAccount = this.sessionBean.getCurrentUserAccount();
			if (UserAccountTypeConstant.EMPLOYEE.equals(userAccount.getUserAccountType())) {
				this.employee = this.employeeService.findEmployeeByUsername(userAccount.getUsername());
			}
			this.loadEmployeeByUserIdFlag = false;
		}
	}

	public void loadCurrentEmployeeDesignationByEmpId() {
		if (this.loadCurrentEmployeeDesignationByEmpIdFlag) {
			this.employeeDesignation = this.employeeDesignationService.findCurrentEmployeeDesignationByEmployeeID(this.employee.getId());
			this.loadCurrentEmployeeDesignationByEmpIdFlag = false;
		}
	}

	@PostConstruct
	public void init() {
		this.initializeSearchCriteria();
	}

	public void initializeSearchCriteria() {
		if (this.employeeSearchCriteria == null) {
			this.employeeSearchCriteria = new EmployeeSearchCriteria(this.sessionBean.getCurrentBranch());
		}
	}

	public String resetSearchCriteria() {
		this.employeeSearchCriteria.resetSeacrhCriteria();
		return null;
	}

	public String searchEmployeesBySearchCriteria() {

		if (this.employeeSearchCriteria.isSearchCriteriaIsEmpty()) {
			ViewUtil.addMessage("Please enter search criteria.", FacesMessage.SEVERITY_ERROR);
		} else {
			this.employeeSearchCriteria.setBranch(this.sessionBean.getCurrentBranch());
			this.employeesSearchResults = this.employeeService.findEmployeesBySearchCriteria(this.employeeSearchCriteria);
			if (this.employeesSearchResults == null || this.employeesSearchResults.isEmpty()) {
				ViewUtil.addMessage("No employees found for entered search criteria..", FacesMessage.SEVERITY_INFO);
			}
		}
		return null;
	}

	public StreamedContent getProfilePictureStreamedContent() {
		return new DefaultStreamedContent(new ByteArrayInputStream(this.profilePicture), "image/jpeg");
	}

	public void loadProfilePicture() {

		if (this.isLoadProfilePictureFlag()) {

			ProfilePicture profilePicture = this.profilePictureService.findStudentProfilePicture(this.employeeDO.getEmployee().getEmployeeNumber());

			if (profilePicture == null) {

				ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

				String profilePictureCatoon = servletContext.getRealPath("") + File.separator + "images" + File.separator + "profile-photo.jpeg";

				try {

					this.profilePicture = this.readImageToByteArray(profilePictureCatoon);

				} catch (FileNotFoundException e) {

				} catch (IOException e) {

				}
			} else {
				this.profilePicture = profilePicture.getProfilePicture();
			}
			this.setLoadProfilePictureFlag(false);
		}

	}

	private byte[] readImageToByteArray(final String imagePath) throws FileNotFoundException, IOException {

		FileImageInputStream fiStream = null;

		ByteArrayOutputStream output = null;
		byte[] buf = null;
		byte[] data = null;

		fiStream = new FileImageInputStream(new File(imagePath));

		output = new ByteArrayOutputStream();
		buf = new byte[512];
		data = null;

		int numBytesRead = 0;
		while ((numBytesRead = fiStream.read(buf)) != -1) {
			output.write(buf, 0, numBytesRead);
		}

		data = output.toByteArray();
		output.close();
		fiStream.close();

		return data;
	}



	public boolean isLoadProfilePictureFlag() {
		return this.loadProfilePictureFlag;
	}

	public void setLoadProfilePictureFlag(final boolean loadProfilePictureFlag) {
		this.loadProfilePictureFlag = loadProfilePictureFlag;
	}

	public Collection<EmployeeDO> getEmployeesSearchResults() {
		return this.employeesSearchResults;
	}

	public void setEmployeesSearchResults(final Collection<EmployeeDO> employeesSearchResults) {
		this.employeesSearchResults = employeesSearchResults;
	}

	public EmployeeDO getEmployeeDO() {
		return this.employeeDO;
	}

	public void setEmployeeDO(final EmployeeDO employeeDO) {
		this.employeeDO = employeeDO;
	}
}
