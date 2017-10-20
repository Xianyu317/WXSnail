package com.snail.dao.base;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;

public abstract class BaseDao {
	protected QueryRunner qR = new QueryRunner();
	public Logger log = null;
	
	public BaseDao() {
		log = Logger.getLogger(super.getClass());
	}
}
