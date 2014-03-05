package com.apeironsol.need.util.portal;

public class NonAdmissionFeeCollectedTreeNode {

	private String	name;
	private double	applicationFormFee;
	private double	reservationFee;

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the applicationFormFee
	 */
	public double getApplicationFormFee() {
		return this.applicationFormFee;
	}

	/**
	 * @param applicationFormFee
	 *            the applicationFormFee to set
	 */
	public void setApplicationFormFee(final double applicationFormFee) {
		this.applicationFormFee = applicationFormFee;
	}

	/**
	 * @return the reservationFee
	 */
	public double getReservationFee() {
		return this.reservationFee;
	}

	/**
	 * @param reservationFee
	 *            the reservationFee to set
	 */
	public void setReservationFee(final double reservationFee) {
		this.reservationFee = reservationFee;
	}

}
