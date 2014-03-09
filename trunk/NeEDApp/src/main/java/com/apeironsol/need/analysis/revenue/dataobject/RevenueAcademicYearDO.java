package com.apeironsol.need.analysis.revenue.dataobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Relation;
import com.apeironsol.need.core.model.StudentAcademicYearFeeSummary;
import com.apeironsol.need.util.constants.GenderConstant;

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

	public Map<GenderConstant, Double> getRevenueByGender() {
		final Map<GenderConstant, Double> revenueByGender = new HashMap<GenderConstant, Double>();
		if (this.studentAcademicYearFeeSummaries != null) {
			for (final StudentAcademicYearFeeSummary studentAcademicYearFeeSummary : this.studentAcademicYearFeeSummaries) {
				if (revenueByGender.get(studentAcademicYearFeeSummary.getStudentAcademicYear().getStudent().getGender()) != null) {
					double currentAmount = revenueByGender.get(studentAcademicYearFeeSummary.getStudentAcademicYear().getStudent().getGender());
					currentAmount += studentAcademicYearFeeSummary.getTotalFeePaid() != null ? studentAcademicYearFeeSummary.getTotalFeePaid() : 0;
					revenueByGender.put(studentAcademicYearFeeSummary.getStudentAcademicYear().getStudent().getGender(), currentAmount);
				} else {
					revenueByGender.put(studentAcademicYearFeeSummary.getStudentAcademicYear().getStudent().getGender(),
							studentAcademicYearFeeSummary.getTotalFeePaid() != null ? studentAcademicYearFeeSummary.getTotalFeePaid() : 0);
				}
			}
		}
		return revenueByGender;
	}

	public Map<String, Double> getRevenueByLocation() {
		final Map<String, Double> revenueByLocation = new HashMap<String, Double>();
		if (this.studentAcademicYearFeeSummaries != null) {
			// As number of zi codes can be many, only display those pincodes
			// where substantial students are coming from.
			final Map<String, Integer> locationCount = new HashMap<String, Integer>();
			for (final StudentAcademicYearFeeSummary studentAcademicYearFeeSummary : this.studentAcademicYearFeeSummaries) {
				if (locationCount.get(studentAcademicYearFeeSummary.getStudentAcademicYear().getStudent().getAddress().getZipCode()) != null) {
					int count = locationCount.get(studentAcademicYearFeeSummary.getStudentAcademicYear().getStudent().getAddress().getZipCode());
					count += 1;
					locationCount.put(studentAcademicYearFeeSummary.getStudentAcademicYear().getStudent().getAddress().getZipCode(), count);
				} else {
					locationCount.put(studentAcademicYearFeeSummary.getStudentAcademicYear().getStudent().getAddress().getZipCode(), 1);
				}
			}
			final Collection<String> locationsUsedInGraph = new ArrayList<String>();
			if (locationCount.size() <= 10) {
				for (final Map.Entry<String, Integer> entry : locationCount.entrySet()) {
					locationsUsedInGraph.add(entry.getKey());
				}
			} else {

			}
			for (final StudentAcademicYearFeeSummary studentAcademicYearFeeSummary : this.studentAcademicYearFeeSummaries) {
				final String zipCode = locationsUsedInGraph.contains(studentAcademicYearFeeSummary.getStudentAcademicYear().getStudent().getAddress()
						.getZipCode()) ? studentAcademicYearFeeSummary.getStudentAcademicYear().getStudent().getAddress().getZipCode() : "Other";
				if (revenueByLocation.get(zipCode) != null) {
					double currentAmount = revenueByLocation.get(zipCode);
					currentAmount += studentAcademicYearFeeSummary.getTotalFeePaid() != null ? studentAcademicYearFeeSummary.getTotalFeePaid() : 0;
					revenueByLocation.put(zipCode, currentAmount);
				} else {
					revenueByLocation.put(zipCode, studentAcademicYearFeeSummary.getTotalFeePaid() != null ? studentAcademicYearFeeSummary.getTotalFeePaid()
							: 0);
				}
			}
		}
		return revenueByLocation;
	}

	private static Map sortByComparator(final Map unsortMap) {

		final List list = new LinkedList(unsortMap.entrySet());

		// sort list based on comparator
		Collections.sort(list, new Comparator() {
			@Override
			public int compare(final Object o1, final Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
			}
		});

		// put sorted list into map again
		// LinkedHashMap make sure order in which keys were inserted
		final Map sortedMap = new LinkedHashMap();
		for (final Iterator it = list.iterator(); it.hasNext();) {
			final Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
}
