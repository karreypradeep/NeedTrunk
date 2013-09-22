package com.apeironsol.need.security.portal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.service.BranchService;

@Named
@Scope(value = "application")
public class BranchesAppStatusBean {

	@Resource
	private BranchService				branchService;

	private final Map<Long, Boolean>	branchesStatus	= new HashMap<Long, Boolean>();

	@PostConstruct
	public void init() {
		this.loadBranchesStatus();
	}

	public Map<Long, Boolean> getBranchesStatus() {
		return this.branchesStatus;
	}

	public void reloadBranchesStatus() {
		this.branchesStatus.clear();
		this.loadBranchesStatus();
	}

	private void loadBranchesStatus() {
		Collection<Branch> branches = this.branchService.findAllBranchsByActiveState(true);

		if (branches != null) {
			for (Branch branch : branches) {
				this.branchesStatus.put(branch.getId(), branch.isActive());
			}
		}

	}

}
