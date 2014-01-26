package com.apeironsol.need.academics.portal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.academics.dataobject.ReportCardDO;
import com.apeironsol.need.academics.dataobject.StudentAcademicExamDO;
import com.apeironsol.need.academics.dataobject.StudentExamSubjectDO;
import com.apeironsol.need.academics.model.ReportCard;
import com.apeironsol.need.academics.model.ReportCardExam;
import com.apeironsol.need.academics.service.ReportCardService;
import com.apeironsol.need.academics.service.StudentAcademicService;
import com.apeironsol.need.core.portal.StudentBean;
import com.apeironsol.need.util.comparator.ReportCardDOComparator;
import com.apeironsol.need.util.portal.ExamSubjectResultTreeNode;

@Named
@Scope("session")
public class StudentReportCardBean implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long			serialVersionUID		= 6903693331662339318L;

	@Resource
	private StudentAcademicService		studentAcademicService;

	@Resource
	private ReportCardService			reportCardService;

	@Resource
	private StudentBean					studentBean;

	private Collection<ReportCardDO>	reportCardDOs			= new ArrayList<ReportCardDO>();

	private ReportCardDO				selectedReportCardDO	= null;

	/**
	 * Root node for tree.
	 */
	private final TreeNode				root					= new DefaultTreeNode("examReportDetails", null);

	/**
	 * @return the reportCardDOs
	 */
	public Collection<ReportCardDO> getReportCardDOs() {
		Collections.sort((List<ReportCardDO>) this.reportCardDOs, new ReportCardDOComparator(ReportCardDOComparator.Order.ACADEMIC_YEAR));
		return this.reportCardDOs;
	}

	/**
	 * @param reportCardDOs
	 *            the reportCardDOs to set
	 */
	public void setReportCardDOs(final Collection<ReportCardDO> reportCardDOs) {
		this.reportCardDOs = reportCardDOs;
	}

	public void fetchReportCardsForAcademicYear() {
		this.reportCardDOs.clear();
		Collection<StudentAcademicExamDO> studentAcademicExamDOs = this.studentAcademicService.getStudentAcademicDetailsByExamWise(this.studentBean
				.getStudentSection().getStudentAcademicYear().getId());
		Map<Long, StudentAcademicExamDO> examByStudentExamDOs = new HashMap<Long, StudentAcademicExamDO>();
		Collection<Long> examIDs = new ArrayList<Long>();
		for (StudentAcademicExamDO studentAcademicExamDO : studentAcademicExamDOs) {
			examByStudentExamDOs.put(studentAcademicExamDO.getExam().getId(), studentAcademicExamDO);
			if (!examIDs.contains(studentAcademicExamDO.getExam().getId())) {
				examIDs.add(studentAcademicExamDO.getExam().getId());
			}
		}
		if (examIDs.size() > 0) {
			Collection<ReportCard> reportCards = this.reportCardService.findReportCardsByExams(examIDs);
			ReportCardDO reportCardDO = null;
			for (ReportCard reportCard : reportCards) {
				Map<Long, StudentAcademicExamDO> examByStudentAcademicExamDOMap = new HashMap<Long, StudentAcademicExamDO>();
				Map<Long, Integer> examPercentages = new HashMap<Long, Integer>();
				reportCardDO = new ReportCardDO();
				reportCardDO.setReportCard(reportCard);
				for (ReportCardExam reportCardExam : reportCard.getReportCardExams()) {
					examByStudentAcademicExamDOMap.put(reportCardExam.getExam().getId(), examByStudentExamDOs.get(reportCardExam.getExam().getId()));
					examPercentages.put(reportCardExam.getExam().getId(), reportCardExam.getPercentage());
				}
				reportCardDO.setExamByStudentAcademicExamDOMap(examByStudentAcademicExamDOMap);
				reportCardDO.setExamPercentages(examPercentages);
				reportCardDO.computeReportCard();
				this.reportCardDOs.add(reportCardDO);
			}
		}
	}

	/**
	 * @return the selectedReportCardDO
	 */
	public ReportCardDO getSelectedReportCardDO() {
		return this.selectedReportCardDO;
	}

	/**
	 * @param selectedReportCardDO
	 *            the selectedReportCardDO to set
	 */
	public void setSelectedReportCardDO(final ReportCardDO selectedReportCardDO) {
		this.selectedReportCardDO = selectedReportCardDO;
	}

	/**
	 * @return the selectedReportCardDO
	 */
	public Collection<StudentAcademicExamDO> getStudentAcademicExamDOs() {
		Collection<StudentAcademicExamDO> studentAcademicExamDOs = new ArrayList<StudentAcademicExamDO>();
		if (this.selectedReportCardDO != null && this.selectedReportCardDO.getExamByStudentAcademicExamDOMap() != null) {
			studentAcademicExamDOs.addAll(this.selectedReportCardDO.getExamByStudentAcademicExamDOMap().values());
		}

		return studentAcademicExamDOs;
	}

	/**
	 * @return the root
	 */
	public TreeNode getRoot() {
		return this.root;
	}

	/**
	 * Builds tree for class level fees.
	 */
	public void buildExamResultsTreeTable() {

		this.removeAllChildsOfRootNode(this.root);

		if (this.selectedReportCardDO != null) {

			for (StudentAcademicExamDO studentAcademicExamDO : this.selectedReportCardDO.getStudentAcademicExamDOs()) {

				ExamSubjectResultTreeNode examSubjectResultTreeNode = new ExamSubjectResultTreeNode();
				examSubjectResultTreeNode.setName(studentAcademicExamDO.getExam().getName());
				examSubjectResultTreeNode.setTotalPercentage(studentAcademicExamDO.getPercentageForReportCard());
				examSubjectResultTreeNode.setPercentageScored(studentAcademicExamDO.getScoredPercentageForReportCard());
				examSubjectResultTreeNode.setStudentSubjectExamResult(studentAcademicExamDO.getStudentExamResult());

				TreeNode examTree = new DefaultTreeNode(examSubjectResultTreeNode, this.root);
				examTree.setExpanded(false);

				Collection<StudentExamSubjectDO> studentExamSubjectDOs = studentAcademicExamDO.getStudentExamSubjectDOs();

				for (StudentExamSubjectDO studentExamSubjectDO : studentExamSubjectDOs) {

					ExamSubjectResultTreeNode studentExamSubjectNode = new ExamSubjectResultTreeNode();
					studentExamSubjectNode.setName(studentExamSubjectDO.getSubject().getName());
					studentExamSubjectNode.setTotalPercentage(studentExamSubjectDO.getSectionExamSubject().getMaximumMarks());
					studentExamSubjectNode.setPercentageScored(studentExamSubjectDO.getStudentExamSubject().getScoredMarks());
					studentExamSubjectNode.setStudentSubjectExamResult(studentExamSubjectDO.getStudentSubjectExamResult());
					studentExamSubjectNode.setStatus(studentExamSubjectDO.getStudentExamSubject().getStudentExamSubjectStatus().getLabel());
					new DefaultTreeNode(studentExamSubjectNode, examTree);
				}

			}

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

}
