package com.rabbiter.ol.vo;

import java.util.Date;


/**
 * 
 * 
 * @author 
 * @email ${email}
 * @date 2024-02-12 00:24:20
 */

public class HomeworkVo {

	/**
	 * 练习题ID
	 */
	private Integer id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 题目
	 */
	private String content;
	/**
	 * 参考答案
	 */
	private String answer;
	/**
	 * 创建人
	 */
	private Integer creator;
	/**
	 * 所属班级ID
	 */
	private Integer classId;
	/**
	 * 角色ID
	 */
	private Integer roleId;
	/**
	 * 当前登录人ID
	 */
	private Integer userId;
	/**
	 * 提交时间
	 */
	private Date commitTime;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 当前页
	 */
	private Integer page;

	/**
	 * 每页条数
	 */
	private Integer pageSize;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Date getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
