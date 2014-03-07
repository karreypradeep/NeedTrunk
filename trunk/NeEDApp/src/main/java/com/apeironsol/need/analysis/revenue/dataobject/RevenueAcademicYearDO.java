package com.apeironsol.need.analysis.revenue.dataobject;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Relation;
import com.apeironsol.need.core.model.StudentAcademicYearFeeSummary;

/**
 * 
 * @author pradeep
 * 
 */
public class RevenueAcademicYearDO implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long							serialVersionUID				= -5240957868002122742L;

	/**
	 * Branch.
	 */
	private Branch										branch;

	private Collection<StudentAcademicYearFeeSummary>	studentAcademicYearFeeSummaries	= null;

	private Map<Klass, Collection<Long>>				studentAcademicYearIdsByKlass	= null;

	private Map<Long, Collection<Relation>>				relationsByStudentId			= null;

	/**
	 * @return the studentAcademicYearFeeSummaries
	 */
	public Collection<StudentAcademicYearFeeSummary> getStudentAcademicYearFeeSummaries() {
		return this.studentAcademicYearFeeSummaries;
	}

	/**
	 * @return the studentAcademicYearIdsByKlass
	 */
	public Map<Klass, Collection<Long>> getStudentAcademicYearIdsByKlass() {
		return this.studentAcademicYearIdsByKlass;
	}

	/**
	 * @return the relationsBystudentAcademicYearId
	 */
	public Map<Long, Collection<Relation>> getRelationsByStudentId() {
		return this.relationsByStudentId;
	}

	/**
	 * @return the branch
	 */
	public Branch getBranch() {
		return this.branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

	/**
	 * @param studentAcademicYearFeeSummaries
	 *            the studentAcademicYearFeeSummaries to set
	 */
	public void setStudentAcademicYearFeeSummaries(final Collection<StudentAcademicYearFeeSummary> studentAcademicYearFeeSummaries) {
		this.studentAcademicYearFeeSummaries = studentAcademicYearFeeSummaries;
	}

	/**
	 * @param studentAcademicYearIdsByKlass
	 *            the studentAcademicYearIdsByKlass to set
	 */
	public void setStudentAcademicYearIdsByKlass(final Map<Klass, Collection<Long>> studentAcademicYearIdsByKlass) {
		this.studentAcademicYearIdsByKlass = studentAcademicYearIdsByKlass;
	}

	/**
	 * @param relationsByStudentId
	 *            the relationsByStudentId to set
	 */
	public void setRelationsByStudentId(final Map<Long, Collection<Relation>> relationsByStudentId) {
		this.relationsByStudentId = relationsByStudentId;
	}

	public Map<Klass, Double> getRevenueByCourse() {
		final Map<Klass, Double> revenueByCourse = new HashMap<Klass, Double>();
		if (this.studentAcademicYearFeeSummaries != null) {
			final Map<Long, StudentAcademicYearFeeSummary> studentAcademicYearFeeSummaryByStudentAcademicYearId = new HashMap<Long, StudentAcademicYearFeeSummary>();
			for (final StudentAcademicYearFeeSummary studentAcademicYearFeeSummary : this.studentAcademicYearFeeSummaries) {
				studentAcademicYearFeeSummaryByStudentAcademicYearId.put(studentAcademicYearFeeSummary.getStudentAcademicYear().getId(),
						studentAcademicYearFeeSummary);
			}

			double revenueByKlass = 0;
			if (this.studentAcademicYearIdsByKlass != null) {
				for (final Map.Entry<Klass, Collection<Long>> entry : this.studentAcademicYearIdsByKlass.entrySet()) {
					revenueByKlass = 0;
					for (final Long studentAcademicYearId : entry.getValue()) {
						final StudentAcademicYearFeeSummary studentAcademicYearFeeSummary = studentAcademicYearFeeSummaryByStudentAcademicYearId
								.get(studentAcademicYearId);
						if (studentAcademicYearFeeSummary != null) {
							revenueByKlass += studentAcademicYearFeeSummary.getTotalFeePaid() != null ? studentAcademicYearFeeSummary.getTotalFeePaid() : 0;
						}
					}
					revenueByCourse.put(entry.getKey(), revenueByKlass);
				}
			}
		}
		return revenueByCourse;
	}

}
