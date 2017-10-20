package com.snail.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.snail.model.WXUser;
import com.snail.util.Constant;
import com.snail.web.vo.CommonVO;

public class DisableUrlSessionFilter implements Filter {
	private static Logger log = Logger.getLogger(DisableUrlSessionFilter.class);
	// 需要过滤的url集合
	public static List<String> pattenUrl = new ArrayList<String>();  
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		pattenUrl.add("");
		pattenUrl.add("");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (!(request instanceof HttpServletRequest)) {
			chain.doFilter(request, response);
			return;
		}

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();

		if (httpRequest.isRequestedSessionIdFromURL()) {
			if (session != null) {
				session.invalidate();
			}
		}
		
		// wrap response to remove URL encoding
		HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(httpResponse) {
			
			@Override
			public String encodeRedirectUrl(String url) {
				return url;
			}

			public String encodeRedirectURL(String url) {
				return url;
			}

			public String encodeUrl(String url) {
				return url;
			}

			public String encodeURL(String url) {
				return url;
			}
		};
		
		long t1 = -1l;
		if (log.isInfoEnabled()) {
			t1 = System.nanoTime();
			log.info("Begin Request[" + getRequestUri(httpRequest, true) + "]...");
		}
		try {
			String requestUrl = httpRequest.getRequestURI();  
			if (!pattenUrl.contains(requestUrl)) {
				// XXX 测试代码记得删除--start
				WXUser u = new WXUser();
				u.setUserId(100);
				session.setAttribute(Constant.SESSION_USER, u);
				// XXX 测试代码记得删除--end
				
				WXUser user = (WXUser) session.getAttribute(Constant.SESSION_USER);
				if (user == null) {
					resJson(wrappedResponse, new CommonVO(Constant.RSP_FAIL, "用户未登录或登录已过期，请重新登录").toString());
					return;
				}
			}
			
			chain.doFilter(request, wrappedResponse);
		} catch (Exception e) {
			resJson(wrappedResponse, new CommonVO(Constant.RSP_FAIL, "未知错误").toString());
			log.error("doFilter error", e);
		} finally{  
            if(log.isInfoEnabled()){  
            	log.info(">>>>>>> Completed request["+ getRequestUri(httpRequest, false) +"]["+ (System.nanoTime()-t1)/1000/1000.0 +"ms].");  
            }  
        }  
	}
	
	protected String getRequestUri(HttpServletRequest request, boolean includeQueryString) {
		String path = request.getRequestURI();
		String qs = "";
		if (includeQueryString) {
			qs = request.getQueryString();
			if (qs != null && qs.length() > 0) {
				qs = "?" + qs;
			} else {
				qs = "";
			}
		}
		return path + qs;
	}
	
	private void resJson(ServletResponse response, String jsonString) {
		try {
			response.setContentType("text/html;charset=UTF-8");	// 解决中文乱码  
			PrintWriter writer = response.getWriter();
			writer.write(jsonString);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			log.error("doFilter error", e);
		}
	}
	
	@Override
	public void destroy(){}

}