package com.snail.web.base;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

public abstract class BaseHttpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Logger log = null;
	
	public BaseHttpServlet() {
		log = Logger.getLogger(super.getClass());
	}
}
