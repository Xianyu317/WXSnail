package com.snail.web;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.snail.dao.ScrollGraphDao;
import com.snail.model.PageBean;
import com.snail.model.ScrollGraph;
import com.snail.util.Constant;
import com.snail.util.DbUtil;
import com.snail.util.JsonUtil;
import com.snail.util.ObjectToMapUtil;
import com.snail.util.ResponseUtil;
import com.snail.util.StringUtil;
import com.snail.web.base.BaseHttpServlet;
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
		
		PageBean pageBean = new PageBean(StringUtils.isNotBlank(page) ? Integer.parseInt(page) : 1, StringUtils.isNotBlank(rows) ? Integer.parseInt(rows) : 10);
		if (StringUtil.isNotEmpty(remark)) {
			scrollGraph.setRemark(remark);
		}
		
		Connection con = null;
		try {
			con = dbUtil.getConnection();
			JSONObject result = new JSONObject();
			JSONArray jsonArray = JsonUtil.formatRsToJsonArray(scrollGraphDao.scrollGraphList(con, pageBean, scrollGraph));
			JSONArray array = new JSONArray();
			for(Object obj:jsonArray){
				JSONObject objGraph = (JSONObject) obj;
				String graph_path = (String) objGraph.get("graph_path");
				StringBuffer contextPath = request.getRequestURL();
				graph_path = contextPath.substring(0, contextPath.indexOf("/graph")).toString()+graph_path;
				objGraph.put("graph_path", graph_path);
				array.add(objGraph);
			}
	
			int total = scrollGraphDao.scrollGraphCount(con, scrollGraph);
			result.put("rows", array);
			result.put("total", total);
			log.info("result===="+result);
			ResponseUtil.write(response, result);
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
		String graphId = request.getParameter("graphId");
		CommonVO commonVO = null;
		if (StringUtils.isBlank(graphId)) {
			commonVO = new CommonVO(Constant.RSP_FAIL, "广告ID不能为空");
		} else {
			Connection con = dbUtil.getConnection();
			scrollGraphDao.delete(con, graphId);
			commonVO = new CommonVO();
		}
		ResponseUtil.write(response, commonVO);
	}
	
	private void add(HttpServletRequest request, HttpServletResponse response) {
		if (ServletFileUpload.isMultipartContent(request)) {
			try {
				// 1. 创建DiskFileItemFactory对象，设置缓冲区大小和临时文件目录
				DiskFileItemFactory factory = new DiskFileItemFactory();
				// log.info(System.getProperty("java.io.tmpdir"));//默认临时文件夹
				// 2. 创建ServletFileUpload对象，并设置上传文件的大小限制。
				ServletFileUpload sfu = new ServletFileUpload(factory);
				sfu.setSizeMax(10 * 1024 * 1024);// 以byte为单位 不能超过10M 1024byte=1kb 1024kb=1M 1024M = 1G
				sfu.setHeaderEncoding("utf-8");
				// 3. 调用ServletFileUpload.parseRequest方法解析request对象，得到一个保存了所有上传内容的List对象。
				List<FileItem> fileItemList = sfu.parseRequest((RequestContext) request);
				Iterator<FileItem> fileItems = fileItemList.iterator();
				// 4. 遍历list，每迭代一个FileItem对象，调用其isFormField方法判断是否是上传文件
				while (fileItems.hasNext()) {
					FileItem fileItem = fileItems.next();
					// 普通表单元素
					if (fileItem.isFormField()) {
						String name = fileItem.getFieldName();// name属性值
						String value = fileItem.getString("utf-8");// name对应的value值

						log.info(name + " = " + value);
					}
					// <input type="file">的上传文件的元素
					else {
						String fileName = fileItem.getName();// 文件名称
						log.info("原文件名：" + fileName);// Koala.jpg
						String suffix = fileName.substring(fileName.lastIndexOf('.'));
						log.info("扩展名：" + suffix);// .jpg
						// 新文件名（唯一）
						String newFileName = new Date().getTime() + suffix;
						log.info("新文件名：" + newFileName);// image\1478509873038.jpg
						// 5. 调用FileItem的write()方法，写入文件
						File file = new File(getServletContext().getRealPath("upload") + newFileName);
						log.info(file.getAbsolutePath());
						fileItem.write(file);
						// 6. 调用FileItem的delete()方法，删除临时文件
						fileItem.delete();
						
						/** 存储到数据库  */
						String remark = request.getParameter("remark");
						ScrollGraph graph = new ScrollGraph();
						graph.setGraphPath(newFileName);
						graph.setRemark(remark);
						scrollGraphDao.addScrollGraph(graph);
					}
				}
				response.sendRedirect(request.getContextPath()+ "/upImage.html");
			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void init() throws ServletException {
		// 在系统启动的时候，就开始初始化，在初始化时，检查上传图片的文件夹和存放临时文件的文件夹是否存在，如果不存在，就创建
		// 获取根目录对应的真实物理路径
		File uploadPath = new File(getServletContext().getRealPath("upload"));
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