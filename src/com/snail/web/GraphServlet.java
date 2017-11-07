package com.snail.web;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;

import com.snail.dao.ScrollGraphDao;
import com.snail.model.PageBean;
import com.snail.model.ScrollGraph;
import com.snail.util.Constant;
import com.snail.util.DbUtil;
import com.snail.util.JsonUtil;
import com.snail.util.ResponseUtil;
import com.snail.util.StringUtil;
import com.snail.web.base.BaseHttpServlet;
import com.snail.web.vo.CommonDataVO;
import com.snail.web.vo.CommonVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("static-access")
public class GraphServlet extends BaseHttpServlet {
	DbUtil dbUtil = new DbUtil();
	ScrollGraphDao scrollGraphDao = new ScrollGraphDao();
	private static final long serialVersionUID = 1L;
	

	public GraphServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");

		try {
			if ("list".equals(action)) {
				list(request, response);
			} else if ("delete".equals(action)) {
				delete(request, response);
			} else if ("add".equals(action)) {
				add(request, response);
			}
		} catch (Exception e) {
			log.error("AddressServlet error", e);
			ResponseUtil.write(response, new CommonVO(Constant.RSP_FAIL, "系统异常,请稍后再试"));
		}
	}

	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		ScrollGraph scrollGraph = new ScrollGraph();
		String remark = request.getParameter("remark");
		String state = request.getParameter("state");
		
		PageBean pageBean = new PageBean(StringUtils.isNotBlank(page) ? Integer.parseInt(page) : 1, StringUtils.isNotBlank(rows) ? Integer.parseInt(rows) : 10);
		if (StringUtil.isNotEmpty(remark)) {
			scrollGraph.setRemark(remark);
		}
		
		Connection con = null;
		try {
			con = dbUtil.getConnection();
			JSONObject result = new JSONObject();
			JSONArray jsonArray = JsonUtil.formatRsToJsonArray(scrollGraphDao.scrollGraphList(con, pageBean, scrollGraph,state));
			JSONArray array = new JSONArray();
			for(Object obj:jsonArray){
				JSONObject objGraph = (JSONObject) obj;
				String graph_path = (String) objGraph.get("graph_path");
				StringBuffer contextPath = request.getRequestURL();
				if(contextPath.indexOf("/upload/image")>0){
					graph_path = contextPath.substring(0, contextPath.indexOf("/upload/image")).toString()+graph_path;
					objGraph.put("graph_path", graph_path);
				}
				array.add(objGraph);
			}
	
			int total = scrollGraphDao.scrollGraphCount(con, scrollGraph);
			result.put("rows", array);
			result.put("page", page);
			result.put("total", total);
			log.info("result===="+result);
			ResponseUtil.write(response, new CommonDataVO<JSONObject>(result));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.releaseDB(null, null, con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String graphIds = request.getParameter("graphIds");
		CommonVO commonVO = null;
		if (StringUtils.isBlank(graphIds)) {
			commonVO = new CommonVO(Constant.RSP_FAIL, "广告ID不能为空");
		} else {
			Connection con = dbUtil.getConnection();
			scrollGraphDao.delete(con, graphIds);
			commonVO = new CommonVO();
		}
		ResponseUtil.write(response, commonVO);
	}
	
	private void add(HttpServletRequest request, HttpServletResponse response) {
		String graphPath = "";
		String state = "";
		String remark = "";
		String orderNum = "";
		
		if (ServletFileUpload.isMultipartContent(request)) {
	        DiskFileItemFactory factory = new DiskFileItemFactory();// 获得磁盘文件条目工厂  
	                // 获取服务器下的工程文件中image文件夹的路径  
	        String path=request.getSession().getServletContext().getRealPath("upload/image");  
	        System.out.println("文件保存路径：" + path);  
	        /** 
	         * 如果没以下两行设置的话，上传大的 文件 会占用 很多内存， 设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同 原理 
	         * 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上， 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的 
	         * 然后再将其真正写到 对应目录的硬盘上 
	         */  
	        factory.setRepository(new File(path));  
	        // 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室  
	        factory.setSizeThreshold(1024 * 1024);  
	        // 高水平的API文件上传处理  
	        ServletFileUpload upload = new ServletFileUpload(factory);  
	        try {  
	            // 可以上传多个文件  
	            List<FileItem> list = (List<FileItem>) upload.parseRequest(request);  
	  
	            for (FileItem item : list) {  
	                // 获取表单的属性名字  
	                String name = item.getFieldName();  
	  
	                // 如果获取的 表单信息是普通的 文本 信息  
	                if (item.isFormField()) {  
	                    // 获取用户具体输入的字符串 ，名字起得挺好，因为表单提交过来的是 字符串类型的  
	                    String value = item.getString();  
	                    if("state".equals(name)){
	                    	state = value;
	                    }else if("remark".equals(name)){
	                    	remark = value;
	                    }else if("orderNum".equals(name)){
	                    	orderNum = value;
	                    }
	  
	                    request.setAttribute(name, value);  
	                }  
	                // 对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些  
	                else {  
	                	String fileName = item.getName();// 文件名称
						log.info("原文件名：" + fileName);// Koala.jpg
						String suffix = fileName.substring(fileName.lastIndexOf('.'));
						log.info("扩展名：" + suffix);// .jpg
						// 新文件名（唯一）
						String newFileName = new Date().getTime() + suffix;
						log.info("新文件名：" + newFileName);// image\1478509873038.jpg
						// 5. 调用FileItem的write()方法，写入文件
//						StringBuffer contextPath = request.getRequestURL();
//						
//						File file = new File(contextPath.substring(0, contextPath.indexOf("/graph")).toString()+ "/graph");
						File file = new File(getServletContext().getRealPath("upload/image") + newFileName);
						graphPath = newFileName;
						log.info(file.getAbsolutePath());
						item.write(file);
						item.delete();
	                }  
	            }  
	  
	        } catch (FileUploadException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        } catch (Exception e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	        
	        ScrollGraph graph =  new ScrollGraph();
	        graph.setGraphPath(graphPath);
	        graph.setOrderNum(Integer.parseInt(StringUtils.isEmpty(orderNum)?"1":orderNum));
	        graph.setRemark(remark);
	        graph.setState(Integer.parseInt(StringUtils.isEmpty(state)?"1":state));
	        scrollGraphDao.addScrollGraph(graph);
	        
	        JSONObject result=new JSONObject();
			result.put("success", true);
			ResponseUtil.write(response, result);
		}
	}
	
	@Override
	public void init() throws ServletException {
		// 在系统启动的时候，就开始初始化，在初始化时，检查上传图片的文件夹和存放临时文件的文件夹是否存在，如果不存在，就创建
		// 获取根目录对应的真实物理路径
		File uploadPath = new File(getServletContext().getRealPath("upload/image"));
		log.info("uploadPath=" + uploadPath);
		// 如果目录不存在
		if (!uploadPath.exists()) {
			// 创建目录
			uploadPath.mkdir();
		}
		// 如果不显示调用父类方法，就不会有itemManager实例，因此会造成空指针
		super.init();
	}

}