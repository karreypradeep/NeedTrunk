/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.reporting.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Utility class for generating jasper reports.
 * 
 * @author pradeep
 * 
 */
public class JReportGenerator {

	/**
	 * Generates Jasper report using JReportUsingParameter and returns the file
	 * as byte array so that it can be streamed into what ever format required.
	 * 
	 * @param jReportParameters
	 *            report parameter object.
	 * @param reportType
	 *            report type.
	 * @return generated jasper report in byte array.
	 */
	public static byte[] generateReportUsingParameters(final JReportUsingParameter jReportParameters,
			final JReportType reportType) {
		try {
			return generateReportUsingParameters(jReportParameters.getReportParameters(),
					jReportParameters.getJasperFilePath(), reportType);

		} catch (final JRException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Generate Jasper report using parameters passed.
	 * 
	 * @param parameters
	 *            parameters used in jasper report.
	 * @param jasperFileName
	 *            compiled jasper report file name.
	 * @param reportType
	 *            report type.
	 * @return generated jasper report in byte array.
	 * @throws JRException
	 */
	public static byte[] generateReportUsingParameters(final Map<String, Object> parameters,
			final String jasperFileName, final JReportType reportType) throws JRException {
		return generateReport(parameters, null, jasperFileName, reportType);
	}

	/**
	 * Generate Jasper report using bean data source.
	 * 
	 * @param dataSourceBeans
	 *            bean data source.
	 * @param jasperFileName
	 *            compiled jasper report file name.
	 * @param reportType
	 *            report type.
	 * @return generated jasper report in byte array.
	 * @throws JRException
	 */
	public static byte[] generateReportUsingDataSourceBeans(final List<Object> dataSourceBeans,
			final String jasperFileName, final JReportType reportType) throws JRException {
		final JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(dataSourceBeans);
		return generateReport(null, beanCollectionDataSource, jasperFileName, reportType);
	}

	/**
	 * Generate Jasper report using bean data source.
	 * 
	 * @param dataSourceBeans
	 *            bean data source.
	 * @param jasperFileName
	 *            compiled jasper report file name.
	 * @param reportType
	 *            report type.
	 * @return generated jasper report in byte array.
	 * @throws JRException
	 */
	public static byte[] generateReportUsingDataSourceBeansAndParameters(final Map<String, Object> parameters,
			final List<Object> dataSourceBeans, final String jasperFileName, final JReportType reportType)
			throws JRException {
		final JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(dataSourceBeans);
		return generateReport(parameters, beanCollectionDataSource, jasperFileName, reportType);
	}

	/**
	 * Generate Jasper report using parameters and bean data source.
	 * 
	 * @param parameters
	 *            parameters used in jasper report.
	 * @param beanCollectionDataSource
	 *            bean data source.
	 * @param jasperFileName
	 *            compiled jasper report file name.
	 * @param reportType
	 *            report type.
	 * @return generated jasper report in byte array.
	 * @throws JRException
	 */
	public static byte[] generateReport(final Map<String, Object> parameters,
			final JRBeanCollectionDataSource beanCollectionDataSource, final String jasperFileName,
			final JReportType reportType) throws JRException {
		String fileName = FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath(jasperFileName);
		JasperPrint jasperPrint = JasperFillManager.fillReport(fileName, parameters != null ? parameters
				: new HashMap<String, Object>(), beanCollectionDataSource != null ? beanCollectionDataSource
				: new JREmptyDataSource());
		return generateReport(jasperPrint, reportType);
	}

	/**
	 * Generate report from jasper print.
	 * 
	 * @param jasperPrint
	 *            jasper print.
	 * @param reportType
	 *            report type.
	 * @return generated jasper report in byte array.
	 * @throws JRException
	 */
	public static byte[] generateReport(final JasperPrint jasperPrint, final JReportType reportType) throws JRException {
		byte[] bytes = null;
		if (JReportType.PDF_REPORT.equals(reportType)) {
			bytes = JasperExportManager.exportReportToPdf(jasperPrint);
		}
		return bytes;
	}
}
