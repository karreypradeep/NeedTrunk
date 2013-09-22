/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.reporting.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;

import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.apeironsol.need.reporting.dataobject.ReportDO;
import com.apeironsol.need.util.portal.ViewExceptionHandler;

/**
 * @author sunny
 * 
 *         Util class for Jasper Functionality.
 * 
 */
public class JasperUtil {

	private static final Logger	log	= Logger.getLogger(JasperUtil.class);

	/**
	 * Top level method which handles print functionality.
	 * 
	 * @param jasperFileName
	 *            main jasper file name
	 * @param paramsMap
	 *            parameters map
	 * @param dataSource
	 *            data source object
	 */
	public static StreamedContent printReport(final ReportDO reportDO, final HashMap<String, Object> paramsMap) {
		log.info("Jasper report creation started......");
		JasperPrint jasperPrint = createJasperPrintObject(reportDO, paramsMap);
		StreamedContent file = null;
		if (jasperPrint != null) {
			if (reportDO.getReportType().equals(JReportType.PDF_REPORT)) {
				file = createPDFReport(reportDO, jasperPrint);
			} else if (reportDO.getReportType().equals(JReportType.EXCEL_REPORT)) {
				file = createExcelReport(reportDO, jasperPrint);
			} else if (reportDO.getReportType().equals(JReportType.DOC_REPORT)) {
				file = createDocReport(reportDO, jasperPrint);
			}
			log.info("Jasper report creation succeded......");
		} else {
			log.info("Jasper report creation failed......");
		}
		return file;
	}

	/**
	 * Method which creates jasper print object.
	 * 
	 * @return JasperPrint object.
	 */
	private static JasperPrint createJasperPrintObject(final ReportDO reportDO, final HashMap<String, Object> paramsMap) {
		log.info("Jasper print object creation started......");
		JasperPrint jasperPrint = null;
		try {
			jasperPrint = JasperFillManager.fillReport(reportDO.getInputJasperFileName(), paramsMap, new JREmptyDataSource());
			log.info("Jasper print object creation succeded......");
		} catch (JRException e) {
			ViewExceptionHandler.handle(e);
			log.error("Jasper print object creation failed......" + e.getMessage());
		} catch (Exception e) {
			ViewExceptionHandler.handle(e);
			log.error("Jasper print object creation failed......" + e.getMessage());
		}
		return jasperPrint;
	}

	/**
	 * Creates PDF output report.
	 * 
	 * @param reportDO
	 * @param jasperPrint
	 * @return
	 */
	private static StreamedContent createPDFReport(final ReportDO reportDO, final JasperPrint jasperPrint) {
		log.info("Creating PDF report......");
		StreamedContent file = null;
		try {
			InputStream inputstream = new ByteArrayInputStream(JasperExportManager.exportReportToPdf(jasperPrint));
			file = new DefaultStreamedContent(inputstream, "application/pdf", reportDO.getOutputReportName() + reportDO.getReportType().getExtension());
			log.info("Creating PDF report succeded......");
		} catch (JRException e) {
			log.error("PDF report creation failed......" + e.getMessage());
		}
		log.info("PDF report created successfully......");
		return file;
	}

	/**
	 * Creates PDF output report.
	 * 
	 * @param reportDO
	 * @param jasperPrint
	 * @return
	 */
	private static StreamedContent createExcelReport(final ReportDO reportDO, final JasperPrint jasperPrint) {
		log.info("Creating XLSX report......");
		StreamedContent file = null;
		try {
			JRXlsExporter exporter = new JRXlsExporter();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			out = new ByteArrayOutputStream();
			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, out);
			exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporter.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.TRUE);
			exporter.exportReport();

			InputStream inputstream = new ByteArrayInputStream(out.toByteArray());
			file = new DefaultStreamedContent(inputstream, "application/xlsx", reportDO.getOutputReportName() + reportDO.getReportType().getExtension());
			log.info("Creating XLSX report succeded......");
		} catch (JRException e) {
			log.error("XLSX report creation failed......" + e.getMessage());
		}
		log.info("XLSX report created successfully......");
		return file;
	}

	/**
	 * Creates PDF output report.
	 * 
	 * @param reportDO
	 * @param jasperPrint
	 * @return
	 */
	private static StreamedContent createDocReport(final ReportDO reportDO, final JasperPrint jasperPrint) {
		log.info("Creating PDF report......");
		StreamedContent file = null;
		try {
			InputStream inputstream = new ByteArrayInputStream(JasperExportManager.exportReportToPdf(jasperPrint));
			file = new DefaultStreamedContent(inputstream, "application/pdf", reportDO.getOutputReportName() + ".pdf");
			log.info("Creating PDF report succeded......");
		} catch (JRException e) {
			log.error("PDF report creation failed......" + e.getMessage());
		}
		log.info("PDF report created successfully......");
		return file;
	}

	/**
	 * Return's full file path.
	 * 
	 * @param filePath
	 * @return full file path.
	 */
	public static String getFullFilePath(final String filePath) {
		return FacesContext.getCurrentInstance().getExternalContext().getRealPath(filePath);
	}

}
