/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.hrms.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.hrms.dao.EmployeeCTCDao;
import com.apeironsol.need.hrms.dao.EmployeeCTCDetailsDao;
import com.apeironsol.need.hrms.dataobject.EmployeeCTCDO;
import com.apeironsol.need.hrms.model.EmployeeCTC;
import com.apeironsol.need.hrms.model.EmployeeCTCDetails;
import com.apeironsol.framework.exception.BusinessException;

/**
 * @author Sunny
 * 
 */
@Service("employeeCTCService")
@Transactional
public class EmployeeCTCServiceImpl implements EmployeeCTCService {

	@Resource
	private EmployeeCTCDao			employeeCTCDao;

	@Resource
	private EmployeeCTCDetailsDao	employeeCTCDetailsDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmployeeCTCDO saveEmployeeCTC(final EmployeeCTCDO employeeCTCDO) throws BusinessException {

		boolean newAction = false;
		EmployeeCTCDO result = employeeCTCDO;
		EmployeeCTC updatedEmployeeCTC = null;
		Collection<EmployeeCTCDetails> existingEmployeeCTCDetails = null;
		if (employeeCTCDO.getEmployeeCTC().getId() == null) {
			newAction = true;
		} else {
			existingEmployeeCTCDetails = this.employeeCTCDetailsDao.findAllEmployeeCTCDetailsByEmployeeCTCID(employeeCTCDO.getEmployeeCTC().getId());
		}
		if (employeeCTCDO.getEmployeeCTC().getEmployeeCTCDetails() != null) {
			employeeCTCDO.getEmployeeCTC().getEmployeeCTCDetails().clear();
		}
		employeeCTCDO.getEmployeeCTC().setEmployeeCTCDetails(employeeCTCDO.getAllEmployeeCTCDefinitionDetails());
		EmployeeCTC latestEmployeeCTC = this.employeeCTCDao.findLatestEmployeeCTCByEmployeeID(employeeCTCDO.getEmployeeCTC().getEmployee().getId());
		this.validateEmployeeCTC(employeeCTCDO.getEmployeeCTC(), latestEmployeeCTC);
		if (newAction) {
			if (latestEmployeeCTC != null
					&& (employeeCTCDO.getEmployeeCTC().getId() == null || !employeeCTCDO.getEmployeeCTC().getId().equals(latestEmployeeCTC.getId()))) {
				Calendar endDate = Calendar.getInstance();
				endDate.setTime(employeeCTCDO.getEmployeeCTC().getStartDate());
				endDate.add(Calendar.DATE, -1);
				latestEmployeeCTC.setEndDate(endDate.getTime());
				latestEmployeeCTC = this.employeeCTCDao.persist(latestEmployeeCTC);
				employeeCTCDO.getEmployeeCTC().setPreviousEmployeeCTC(latestEmployeeCTC);
			}
			updatedEmployeeCTC = this.employeeCTCDao.persist(employeeCTCDO.getEmployeeCTC());
		} else {
			Collection<EmployeeCTCDetails> toBeDeleteEmployeeCTCDetails = new ArrayList<EmployeeCTCDetails>();
			Collection<EmployeeCTCDetails> toBeUpdatedEmployeeCTCDetails = new ArrayList<EmployeeCTCDetails>();
			for (EmployeeCTCDetails existingEmployeeCTCDet : existingEmployeeCTCDetails) {
				boolean found = false;
				for (EmployeeCTCDetails employeeCTCDetails : employeeCTCDO.getEmployeeCTC().getEmployeeCTCDetails()) {
					if (employeeCTCDetails.getId() == null || existingEmployeeCTCDet.getId().equals(employeeCTCDetails.getId())) {
						toBeUpdatedEmployeeCTCDetails.add(employeeCTCDetails);
						found = true;
					}
				}
				if (!found) {
					toBeDeleteEmployeeCTCDetails.add(existingEmployeeCTCDet);
				}
			}
			employeeCTCDO.getEmployeeCTC().setEmployeeCTCDetails(null);
			if (latestEmployeeCTC != null
					&& (employeeCTCDO.getEmployeeCTC().getId() == null || !employeeCTCDO.getEmployeeCTC().getId().equals(latestEmployeeCTC.getId()))) {
				Calendar endDate = Calendar.getInstance();
				endDate.setTime(employeeCTCDO.getEmployeeCTC().getStartDate());
				endDate.add(Calendar.DATE, -1);
				latestEmployeeCTC.setEndDate(endDate.getTime());
				latestEmployeeCTC = this.employeeCTCDao.persist(latestEmployeeCTC);
				employeeCTCDO.getEmployeeCTC().setPreviousEmployeeCTC(latestEmployeeCTC);
			}
			updatedEmployeeCTC = this.employeeCTCDao.persist(employeeCTCDO.getEmployeeCTC());

			for (EmployeeCTCDetails employeeCTCDetails : toBeDeleteEmployeeCTCDetails) {
				this.employeeCTCDetailsDao.remove(employeeCTCDetails);
			}
			for (EmployeeCTCDetails employeeCTCDetails : toBeUpdatedEmployeeCTCDetails) {
				employeeCTCDetails.setEmployeeCTC(updatedEmployeeCTC);
				this.employeeCTCDetailsDao.persist(employeeCTCDetails);
			}
		}
		result.setEmployeeCTC(this.findEmployeeCTCById(updatedEmployeeCTC.getId()));
		result.getEmployeeCTC().setEmployeeCTCDetails(this.employeeCTCDetailsDao.findAllEmployeeCTCDetailsByEmployeeCTCID(result.getEmployeeCTC().getId()));
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeEmployeeCTC(final EmployeeCTC employeeCTC) throws BusinessException {
		EmployeeCTC previousEmployeeCTC = employeeCTC.getPreviousEmployeeCTC() == null ? null : this.employeeCTCDao.findById(employeeCTC
				.getPreviousEmployeeCTC().getId());
		this.employeeCTCDao.remove(employeeCTC);
		if (previousEmployeeCTC != null) {
			previousEmployeeCTC.setEndDate(null);
			this.employeeCTCDao.persist(previousEmployeeCTC);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmployeeCTC findEmployeeCTCById(final Long id) throws BusinessException {
		EmployeeCTC updatedEmployeeCTC = this.employeeCTCDao.findById(id);
		updatedEmployeeCTC.setEmployeeCTCDetails(this.employeeCTCDetailsDao.findAllEmployeeCTCDetailsByEmployeeCTCID(updatedEmployeeCTC.getId()));
		return updatedEmployeeCTC;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<EmployeeCTC> findAllEmployeeCTCs() throws BusinessException {
		return this.employeeCTCDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeEmployeeCTCByEmployeeID(final Long employeeId) throws BusinessException {
		this.employeeCTCDao.removeAllEmployeeCTCsByEmployeeID(employeeId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<EmployeeCTC> findAllEmployeeCTCsByEmployeeID(final Long employeeId) throws BusinessException {
		return this.employeeCTCDao.findAllEmployeeCTCsByEmployeeID(employeeId);
	}

	private void validateEmployeeCTC(final EmployeeCTC employeeCTC, final EmployeeCTC latestEmployeeCTC) {
		if (latestEmployeeCTC != null) {
			if (employeeCTC.getId() != null && !latestEmployeeCTC.getId().equals(employeeCTC.getId())
					&& !employeeCTC.getStartDate().after(latestEmployeeCTC.getStartDate())) {
				throw new BusinessException("New CTC start date should be after start date of previous CTC " + latestEmployeeCTC.getStartDate());
			} else if (latestEmployeeCTC.getId().equals(employeeCTC.getId()) && latestEmployeeCTC.getPreviousEmployeeCTC() != null) {
				if (employeeCTC.getStartDate().before(latestEmployeeCTC.getPreviousEmployeeCTC().getEndDate())) {
					throw new BusinessException("CTC start date should be after end date of previous CTC "
							+ latestEmployeeCTC.getPreviousEmployeeCTC().getEndDate());
				}
			}
		}
		// if (!DateUtil.isFirstDayOfMonth(employeeCTC.getStartDate())) {
		// throw new
		// BusinessException("CTC start date should be first of month.");
		// }
		double totalEmployeeCTC = 0;
		if (employeeCTC.getEmployeeCTCDetails() != null && !employeeCTC.getEmployeeCTCDetails().isEmpty()) {
			for (EmployeeCTCDetails existingEmployeeCTCDet : employeeCTC.getEmployeeCTCDetails()) {
				totalEmployeeCTC += existingEmployeeCTCDet.getAmount() != null ? existingEmployeeCTCDet.getAmount() : 0;
			}
		}
		if (!employeeCTC.getTotalCTC().equals(Double.valueOf(totalEmployeeCTC))) {
			throw new BusinessException("Total CTC do not match with total CTC definition amount");
		}
	}

	@Override
	public EmployeeCTCDO findEmployeeCTCByEmployeeIdAndDate(final Long employeeId, final Date salaryDate) throws BusinessException {
		EmployeeCTC employeeCTC = this.employeeCTCDao.findEmployeeCTCByEmployeeIdAndDate(employeeId, salaryDate);
		EmployeeCTCDO employeeCTCDO = new EmployeeCTCDO();
		employeeCTCDO.setEmployeeCTC(employeeCTC);
		if (employeeCTC != null) {
			employeeCTCDO.setAllEmployeeCTCDefinitionDetails(this.employeeCTCDetailsDao.findAllEmployeeCTCDetailsByEmployeeCTCID(employeeCTC.getId()));
		}
		return employeeCTCDO;
	}
}
