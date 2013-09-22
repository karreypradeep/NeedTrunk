/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.portal;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.Relation;

@Named
@Scope(value = "session")
public class RelationBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long	serialVersionUID	= 6495812092058584114L;
	private Relation			relation;

	@PostConstruct
	public void init() {
		this.relation = new Relation();
	}

	public Relation getRelation() {
		return this.relation;
	}

	public void setRelation(final Relation relation) {
		this.relation = relation;
	}

}
