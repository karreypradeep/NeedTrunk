/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.config;

/**
 * Class for spring configuration.
 * 
 * @author Pradeep
 */
import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.velocity.app.VelocityEngine;
import org.jasypt.spring.security3.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.apeironsol.need.core.portal.filter.BranchSelectionCheckFilter;
import com.apeironsol.need.util.aspect.LoggingAspect;
import com.apeironsol.need.util.portal.ViewUtil;

@Configuration
public class SpringConfiguration {

	Properties	applicationProperties;

	Properties	persistenceProperties;

	@PostConstruct
	public void initialize() throws IOException {
		this.applicationProperties = ViewUtil.loadPropertiesFromClasspath("application.properties");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PasswordEncoder();
	}

	@Bean
	public JavaMailSenderImpl getMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(this.applicationProperties.getProperty("mail.smpt.host"));
		mailSender.setPort(Integer.valueOf(this.applicationProperties.getProperty("mail.smpt.port")).intValue());
		mailSender.setUsername(this.applicationProperties.getProperty("mail.smpt.username"));
		mailSender.setPassword(this.applicationProperties.getProperty("mail.smpt.password"));
		return mailSender;
	}

	@Bean
	public SimpleMailMessage getSimpleMailMessage() {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		return simpleMailMessage;
	}

	@Bean
	public VelocityEngine getVelocityEngine() {
		Properties velocityEngineProperties = new Properties();
		VelocityEngine velocityEngine = new VelocityEngine(velocityEngineProperties);
		return velocityEngine;
	}

	@Bean
	public BranchSelectionCheckFilter branchSelectionCheckFilter() {
		return new BranchSelectionCheckFilter();
	}

	@Bean
	public LoggingAspect loggingAspect() {
		return new LoggingAspect();
	}
}
