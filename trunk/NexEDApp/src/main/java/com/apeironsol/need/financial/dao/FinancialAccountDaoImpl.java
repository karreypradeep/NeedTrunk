package com.apeironsol.need.financial.dao;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.financial.model.FinancialAccount;
import com.apeironsol.framework.BaseDaoImpl;

@Repository(value = "financialAccountDao")
public class FinancialAccountDaoImpl extends BaseDaoImpl<FinancialAccount> implements FinancialAccountDao {

}
