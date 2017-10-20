package com.snail.web;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.snail.dao.GoodsDetailDao;
import com.snail.model.GoodsDetail;
import com.snail.model.WXUser;
import com.snail.util.Constant;
import com.snail.util.DateUtil;
import com.snail.util.DbUtil;
import com.snail.util.JsonUtil;
import com.snail.util.ResponseUtil;
import com.snail.web.base.BaseHttpServlet;
import com.snail.web.vo.CommonDataVO;
import com.snail.web.vo.CommonVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GoodsDetailServlet extends BaseHttpServlet {
	private static final long serialVersionUID = -6971529709644703112L;
	
	
	GoodsDetailDao goodsDetailDao = new GoodsDetailDao();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		
		HttpSession session = request.getSession();
		WXUser user = (WXUser) session.getAttribute(Constant.SESSION_USER);
		int userId = user.getUserId();
		
		Connection con = null;
		CommonVO commonVO = null;
		try {
			con = DbUtil.getConnection();
			if ("list".equals(action)) {
				commonVO = list(request, response, userId, con);			
			}else if ("query".equals(action)) {
				commonVO = query(request, response, userId, con);			
			} else if ("add".equals(action)) {
				add(request, response);
				return;
			} else if ("update".equals(action)) {
				update(request, response, con);
				return;
			} else if ("delete".equals(action)) {
				commonVO = delete(request, response, con);
			}
		} catch (Exception e) {
			log.error("GoodsDetailServlet error", e);
			commonVO = new CommonVO(Constant.RSP_FAIL, "系统异常,请稍后再试");
		} finally {
			DbUtil.releaseDB(null, null, con);
		}	
		ResponseUtil.write(response, commonVO);
	}

	
	private CommonVO list(HttpServletRequest request, HttpServletResponse response, int userId, Connection con) throws SQLException {
		// 查询条件：1=按分类  2=上架时间大于当前时间  3=按积分倒序查询
		String queryKey = request.getParameter("queryKey");
		String typeId = request.getParameter("typeId");
		String addedTime = request.getParameter("addedTime");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		
		JSONObject result = new JSONObject();
		JSONArray list = JsonUtil.formatRsToJsonArray(goodsDetailDao.queryList(queryKey,typeId, addedTime, page, rows, con));
		int total = goodsDetailDao.queryListCount(queryKey, addedTime, page, rows, con);
		result.put("rows", list);
		result.put("total", total);
		log.info("result===="+result);
		
		return new CommonDataVO<JSONObject>(result);
	}
	
	
	private CommonVO query(HttpServletRequest request, HttpServletResponse response, int userId, Connection con) throws SQLException {
		String goodId = request.getParameter("goodId");
		
		JSONObject result = new JSONObject();
		JSONArray list = JsonUtil.formatRsToJsonArray(goodsDetailDao.query(goodId, con));
		result.put("rows", list);
		log.info("result===="+result);
		
		return new CommonDataVO<JSONObject>(result);
	}
	
	private CommonVO update(HttpServletRequest request, HttpServletResponse response, Connection con) throws Exception {
		String goodsId = request.getParameter("goodsId");
		String goodsName = request.getParameter("goodsName");
		String goodsPrice = request.getParameter("goodsPrice");
		String typeId = request.getParameter("typeId");
		String stockSize = request.getParameter("stockSize");
		String company = request.getParameter("company");
		String integral = request.getParameter("integral");
		String detailDes = request.getParameter("detailDes");
		String carryFare = request.getParameter("carryFare");
		String addedTime = request.getParameter("addedTime");
		
		if (StringUtils.isNotBlank(goodsId)) {
			GoodsDetail detail = new GoodsDetail();
			detail.setGoodsId(Integer.parseInt(goodsId));
			detail.setGoodsName(goodsName);
			detail.setGoodsPricel(new BigDecimal(goodsPrice));
			detail.setTypeId(Integer.parseInt(typeId));
			detail.setStockSize(Integer.parseInt(stockSize));
			detail.setCompany(Integer.parseInt(company));
			detail.setIntegral(Integer.parseInt(integral));
			detail.setDetailDes(detailDes);
			detail.setCarryFare(Integer.parseInt(carryFare));
			detail.setAddedTime(DateUtil.string2Date(addedTime, DateUtil.YYYYMMDDHHMMSS));
			return new CommonVO();
		}
		return new CommonVO(Constant.RSP_FAIL, "更新商品信息失败");
	}
	
	private CommonVO delete(HttpServletRequest request, HttpServletResponse response, Connection con) throws SQLException {
		String goodsId = request.getParameter("goodsId");
		if (StringUtils.isBlank(goodsId)) {
			return new CommonVO(Constant.RSP_FAIL, "商品ID不能为空");
		}
		goodsDetailDao.delete(con, goodsId);
		return new CommonVO();
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
						File file = new File(getServletContext().getRealPath("goodsImages") + newFileName);
						log.info(file.getAbsolutePath());
						fileItem.write(file);
						// 6. 调用FileItem的delete()方法，删除临时文件
						fileItem.delete();
						
						/** 存储到数据库  */
						String goodsName = request.getParameter("goodsName");
						String goodsPrice = request.getParameter("goodsPrice");
						String typeId = request.getParameter("typeId");
						String stockSize = request.getParameter("stockSize");
						String company = request.getParameter("company");
						String integral = request.getParameter("integral");
						String detailDes = request.getParameter("detailDes");
						String carryFare = request.getParameter("carryFare");
						String addedTime = request.getParameter("addedTime");
						
						GoodsDetail detail = new GoodsDetail();
						detail.setMasterImg(newFileName);
						detail.setGoodsName(goodsName);
						detail.setGoodsPricel(new BigDecimal(goodsPrice));
						detail.setTypeId(Integer.parseInt(typeId));
						detail.setStockSize(Integer.parseInt(stockSize));
						detail.setCompany(Integer.parseInt(company));
						detail.setIntegral(Integer.parseInt(integral));
						detail.setDetailDes(detailDes);
						detail.setCarryFare(Integer.parseInt(carryFare));
						detail.setAddedTime(DateUtil.string2Date(addedTime, DateUtil.YYYYMMDDHHMMSS));
						goodsDetailDao.addGoodsDetail(detail);
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
		File uploadPath = new File(getServletContext().getRealPath("goodsImages"));
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
