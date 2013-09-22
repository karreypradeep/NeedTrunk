/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.reporting.model;

import com.apeironsol.need.core.model.Section;

/**
 * @author sunny This class is model for Section Report.
 */
public class SectionReport {
	
	private Section section;

	private double totalFees;

	private double weaveredFees;

	private double refundFees;

	private double recievedFees;

	private double dueFees;

	/**
	 * @return the section
	 */
	public Section getSection() {
		return section;
	}

	/**
	 * @param section the section to set
	 */
	public void setSection(Section section) {
		this.section = section;
	}

	/**
	 * @return the totalFees
	 */
	public double getTotalFees() {
		return totalFees;
	}

	/**
	 * @param totalFees the totalFees to set
	 */
	public void setTotalFees(double totalFees) {
		this.totalFees = totalFees;
	}

	/**
	 * @return the weaveredFees
	 */
	public double getWeaveredFees() {
		return weaveredFees;
	}

	/**
	 * @param weaveredFees the weaveredFees to set
	 */
	public void setWeaveredFees(double weaveredFees) {
		this.weaveredFees = weaveredFees;
	}

	/**
	 * @return the refundFees
	 */
	public double getRefundFees() {
		return refundFees;
	}

	/**
	 * @param refundFees the refundFees to set
	 */
	public void setRefundFees(double refundFees) {
		this.refundFees = refundFees;
	}

	/**
	 * @return the recievedFees
	 */
	public double getRecievedFees() {
		return recievedFees;
	}

	/**
	 * @param recievedFees the recievedFees to set
	 */
	public void setRecievedFees(double recievedFees) {
		this.recievedFees = recievedFees;
	}

	/**
	 * @return the dueFees
	 */
	public double getDueFees() {
		return dueFees;
	}

	/**
	 * @param dueFees the dueFees to set
	 */
	public void setDueFees(double dueFees) {
		this.dueFees = dueFees;
	}

}
