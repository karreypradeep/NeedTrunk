package com.apeironsol.nexed.util.po;

import com.apeironsol.nexed.core.model.Section;

public class SectionNotificationPO {

	private final Section	section;

	private long			nrOfNotificationsSent;

	public SectionNotificationPO(final Section section) {
		this.section = section;
	}

	/**
	 * @return the section
	 */
	public Section getSection() {
		return this.section;
	}

	/**
	 * @return the nrOfNotificationsSent
	 */
	public long getNrOfNotificationsSent() {
		return this.nrOfNotificationsSent;
	}

	/**
	 * @param nrOfNotificationsSent
	 *            the nrOfNotificationsSent to set
	 */
	public void setNrOfNotificationsSent(final long nrOfNotificationsSent) {
		this.nrOfNotificationsSent = nrOfNotificationsSent;
	}

}
