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
import com.apeironsol.need.core.model.Subject;
import com.apeironsol.need.core.portal.StudentBean;
import com.apeironsol.need.util.comparator.ReportCardDOComparator;
import com.apeironsol.need.util.portal.ExamSubjectResultTreeNode;
import com.apeironsol.need.util.portal.ReprotCardTreeNode;

@Named
@Scope("session")
public class StudentReportCardBean implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long			serialVersionUID		= 6903693331662339318L;

	private Collection<ReportCardDO>	reportCardDOs			= new ArrayList<ReportCardDO>();

	@Resource
	private ReportCardService			reportCardService;

	/**
	 * Root node for tree.
	 */
	private final TreeNode				root					= new DefaultTreeNode("examReportDetails", null);

	private final TreeNode				reportCardRoot			= new DefaultTreeNode("reportCardDetails", null);

	private ReportCardDO				selectedReportCardDO	= null;

	private Long						selectedReportCardId	= null;

	@Resource
	private StudentAcademicService		studentAcademicService;

	@Resource
	private StudentBean					studentBean;

	/**
	 * Builds tree for class level fees.
	 */
	public void buildExamResultsTreeTable() {

		removeAllChildsOfRootNode(this.root);
		this.selectedReportCardDO = null;
		for (final ReportCardDO reportCardDO : this.reportCardDOs) {
			if (reportCardDO.getReportCard().getId().equals(this.selectedReportCardId)) {
				this.selectedReportCardDO = reportCardDO;
				break;
			}
		}

		if (this.selectedReportCardDO != null) {

			for (final StudentAcademicExamDO studentAcademicExamDO : this.selectedReportCardDO.getStudentAcademicExamDOs()) {

				final ExamSubjectResultTreeNode examSubjectResultTreeNode = new ExamSubjectResultTreeNode();
				examSubjectResultTreeNode.setName(studentAcademicExamDO.getExam().getName());
				examSubjectResultTreeNode.setPercentageForReportCard(this.selectedReportCardDO.getExamPercentages()
						.get(studentAcademicExamDO.getExam().getId()));
				examSubjectResultTreeNode.setScoredPercentageForReportCard(this.selectedReportCardDO.getExamScoredPercentages().get(
						studentAcademicExamDO.getExam().getId()));
				examSubjectResultTreeNode.setStudentSubjectExamResult(studentAcademicExamDO.getStudentExamResult());
				examSubjectResultTreeNode.setReportCardExamDetailsInd(true);
				final TreeNode examTree = new DefaultTreeNode(examSubjectResultTreeNode, this.root);
				examTree.setExpanded(false);

				final Collection<StudentExamSubjectDO> studentExamSubjectDOs = studentAcademicExamDO.getStudentExamSubjectDOs();

				for (final StudentExamSubjectDO studentExamSubjectDO : studentExamSubjectDOs) {

					final ExamSubjectResultTreeNode studentExamSubjectNode = new ExamSubjectResultTreeNode();
					studentExamSubjectNode.setName(studentExamSubjectDO.getSubject().getName());
					studentExamSubjectNode.setTotalMarks(studentExamSubjectDO.getSectionExamSubject().getMaximumMarks());
					studentExamSubjectNode.setMarksScored(studentExamSubjectDO.getStudentExamSubject().getScoredMarks());

					studentExamSubjectNode.setScoredPercentage(studentExamSubjectDO.getPercentageScoredForSubject());
					studentExamSubjectNode.setPercentageForReportCard(this.selectedReportCardDO.getExamPercentages().get(
							studentAcademicExamDO.getExam().getId()));
					studentExamSubjectNode.setScoredPercentageForReportCard((studentExamSubjectDO.getPercentageScoredForSubject() * this.selectedReportCardDO
							.getExamPercentages().get(studentAcademicExamDO.getExam().getId())) / 100);
					studentExamSubjectNode.setStudentSubjectExamResult(studentExamSubjectDO.getStudentSubjectExamResult());
					studentExamSubjectNode.setStatus(studentExamSubjectDO.getStudentExamSubject().getStudentExamSubjectStatus().getLabel());
					new DefaultTreeNode(studentExamSubjectNode, examTree);
				}

			}

		}
	}

	/**
	 * Builds tree for class level fees.
	 */
	public void buildReportCardTreeTable() {
		removeAllChildsOfRootNode(this.reportCardRoot);
		for (final ReportCardDO reportCardDO : this.reportCardDOs) {
			final ReprotCardTreeNode reprotCardTreeNode = new ReprotCardTreeNode();
			reprotCardTreeNode.setReportCardId(reportCardDO.getReportCard().getId());
			reprotCardTreeNode.setName(reportCardDO.getReportCard().getName());
			reprotCardTreeNode.setScoredPercentage(reportCardDO.getScoredPercentageForReportCard());
			reprotCardTreeNode.setResult(reportCardDO.getStudentReportCardResult());
			reprotCardTreeNode.setGrade(reportCardDO.getGradeForReportCard());
			final TreeNode examTree = new DefaultTreeNode(reprotCardTreeNode, this.reportCardRoot);
			examTree.setExpanded(false);
			for (final Map.Entry<Subject, Double> entry : reportCardDO.getScoresBySubject().entrySet()) {
				final ReprotCardTreeNode subjectTreeNode = new ReprotCardTreeNode();
				subjectTreeNode.setName(entry.getKey().getName());
				subjectTreeNode.setScoredPercentage(entry.getValue());
				subjectTreeNode.setGrade(reportCardDO.getGradeForReportCardSubject(entry.getKey()));
				subjectTreeNode.setResult(reportCardDO.getResultForReportCardSubject(entry.getKey()));
				new DefaultTreeNode(subjectTreeNode, examTree);
			}

		}
	}

	public void fetchReportCardsForAcademicYear() {
		this.reportCardDOs.clear();
		final Collection<StudentAcademicExamDO> studentAcademicExamDOs = this.studentAcademicService.getStudentAcademicDetailsByExamWise(this.studentBean
				.getStudentSection().getStudentAcademicYear().getId());
		final Map<Long, StudentAcademicExamDO> examByStudentExamDOs = new HashMap<Long, StudentAcademicExamDO>();
		final Collection<Long> examIDs = new ArrayList<Long>();
		for (final StudentAcademicExamDO studentAcademicExamDO : studentAcademicExamDOs) {
			examByStudentExamDOs.put(studentAcademicExamDO.getExam().getId(), studentAcademicExamDO);
			if (!examIDs.contains(studentAcademicExamDO.getExam().getId())) {
				examIDs.add(studentAcademicExamDO.getExam().getId());
			}
		}
		if (examIDs.size() > 0) {
			final Collection<ReportCard> reportCards = this.reportCardService.findReportCardsByExams(examIDs);
			// ReportCardDO reportCardDO = null;
			for (final ReportCard reportCard : reportCards) {
				final Map<Long, StudentAcademicExamDO> examByStudentAcademicExamDOMap = new HashMap<Long, StudentAcademicExamDO>();
				final Map<Long, Integer> examPercentages = new HashMap<Long, Integer>();
				final ReportCardDO reportCardDO = new ReportCardDO();
				reportCardDO.setReportCard(reportCard);
				for (final ReportCardExam reportCardExam : reportCard.getReportCardExams()) {
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
	 * @return the reportCardDOs
	 */
	public Collection<ReportCardDO> getReportCardDOs() {
		Collections.sort((List<ReportCardDO>) this.reportCardDOs, new ReportCardDOComparator(ReportCardDOComparator.Order.ACADEMIC_YEAR));
		return this.reportCardDOs;
	}

	/**
	 * @return the reportCardRoot
	 */
	public TreeNode getReportCardRoot() {
		return this.reportCardRoot;
	}

	/**
	 * @return the root
	 */
	public TreeNode getRoot() {
		return this.root;
	}

	/**
	 * @return the selectedReportCardDO
	 */
	public ReportCardDO getSelectedReportCardDO() {
		return this.selectedReportCardDO;
	}

	/**
	 * @return the selectedReportCardDO
	 */
	public Collection<StudentAcademicExamDO> getStudentAcademicExamDOs() {
		final Collection<StudentAcademicExamDO> studentAcademicExamDOs = new ArrayList<StudentAcademicExamDO>();
		if ((this.selectedReportCardDO != null) && (this.selectedReportCardDO.getExamByStudentAcademicExamDOMap() != null)) {
			studentAcademicExamDOs.addAll(this.selectedReportCardDO.getExamByStudentAcademicExamDOMap().values());
		}

		return studentAcademicExamDOs;
	}

	/**
	 * Removes all child nodes of the supplied root node.
	 */
	private void removeAllChildsOfRootNode(final TreeNode rootNode) {
		if ((rootNode != null) && (rootNode.getChildCount() > 0)) {
			final TreeNode[] array = rootNode.getChildren().toArray(new TreeNode[rootNode.getChildCount()]);
			for (TreeNode child : array) {
				child.setParent(null);
				child = null;
			}
		}
	}

	/**
	 * @param reportCardDOs
	 *            the reportCardDOs to set
	 */
	public void setReportCardDOs(final Collection<ReportCardDO> reportCardDOs) {
		this.reportCardDOs = reportCardDOs;
	}

	/**
	 * @param selectedReportCardDO
	 *            the selectedReportCardDO to set
	 */
	public void setSelectedReportCardDO(final ReportCardDO selectedReportCardDO) {
		this.selectedReportCardDO = selectedReportCardDO;
	}

	/**
	 * @return the selectedReportCardId
	 */
	public Long getSelectedReportCardId() {
		return this.selectedReportCardId;
	}

	/**
	 * @param selectedReportCardId
	 *            the selectedReportCardId to set
	 */
	public void setSelectedReportCardId(final Long selectedReportCardId) {
		this.selectedReportCardId = selectedReportCardId;
	}

}
