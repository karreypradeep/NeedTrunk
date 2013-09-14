package com.apeironsol.framework.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class PortalExceptionHandlerFactory extends ExceptionHandlerFactory {

	private final ExceptionHandlerFactory	base;

	public PortalExceptionHandlerFactory(final ExceptionHandlerFactory base) {
		this.base = base;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		return new PortalExceptionHandler(this.base.getExceptionHandler());
	}

}
