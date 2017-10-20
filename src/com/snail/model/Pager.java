package com.snail.model;

import java.io.Serializable;
import java.util.List;

public class Pager<T> implements Serializable {

	private static final long serialVersionUID = 5760745551214243616L;
	
	private long page_size;//每页显示多少条记录  rows
	
	private long current_page;//当前第几页数据  page
	
	private long total_record;//一共多少条记录
	
	private long total_page;//一共多少页记录
	
	private long start_page; //起始页
	
	private List<T> data_list;//要显示的数据
	
	
	public Pager(int pageNum, int pageSize, List<T> sourceList){
		if(sourceList == null || sourceList.isEmpty()){
			return;
		}
		// 总记录条数
		this.total_record = sourceList.size();
		// 每页显示多少条记录
		this.page_size = pageSize;
		//获取总页数
		this.total_page = this.total_record / this.page_size;
		if(this.total_record % this.page_size !=0){
			this.total_page = this.total_page + 1;
		}
		// 当前第几页数据
		this.current_page = this.total_page < pageNum ?  this.total_page : pageNum;
		// 起始索引
		long fromIndex	= this.page_size * (this.current_page -1);
		// 结束索引
		long toIndex  = this.page_size * this.current_page > this.total_record ? this.total_record : this.page_size * this.current_page;
				
		this.data_list = sourceList.subList((int)fromIndex, (int)toIndex);
	}
	
	
	
	public long getStart_page() {
		this.start_page = (current_page -1)*page_size;
		return this.start_page;
	}



	public void setStart_page(long start_page) {
		this.start_page = start_page;
	}



	public Pager() {
		super();
	}

	public Pager(long pageSize, long currentPage, long totalRecord, long totalPage,
			List<T> dataList) {
		super();
		this.page_size = pageSize;
		this.current_page = currentPage;
		this.total_record = totalRecord;
		this.total_page = totalPage;
		this.data_list = dataList;
	}

    public long getPage_size() {
        return page_size;
    }

    public void setPage_size(long page_size) {
        this.page_size = page_size;
    }

    public long getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(long current_page) {
        this.current_page = current_page;
    }

    public long getTotal_record() {
        return total_record;
    }

    public void setTotal_record(long total_record) {
        this.total_record = total_record;
    }

    public long getTotal_page() {
        return total_page;
    }

    public void setTotal_page(long total_page) {
        this.total_page = total_page;
    }

    public List<T> getData_list() {
        return data_list;
    }

    public void setData_list(List<T> data_list) {
        this.data_list = data_list;
    }

}
