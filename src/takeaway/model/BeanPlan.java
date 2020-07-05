package takeaway.model;

import java.util.Date;

public class BeanPlan {
	public static final String[] tableTitles={"序号","名称","步骤数","已完成数"};
	/**
	 * 请自行根据javabean的设计修改本函数代码，col表示界面表格中的列序号，0开始
	 */
	private int planid;
	private String userid;
	private int planorder;
	private String planname;
	private Date createtime;
	private int stepcount;
	private int startstep;
	private int finishstep;
	public int getplanid() {
		return planid;
	}
	public void setplanid(int planid) {
		this.planid = planid;
	}
	public String getuserid() {
		return userid;
	}
	public void setuserid(String userid) {
		this.userid = userid;
	}
	public int getplanorder() {
		return planorder;
	}
	public void setplanorder(int planorder) {
		this.planorder = planorder;
	}
	public String getplanname() {
		return planname;
	}
	public void setplanname(String planname) {
		this.planname = planname;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public int getstepcount() {
		return stepcount;
	}
	public void setstepcount(int stepcount) {
		this.stepcount = stepcount;
	}
	public int getstartstep() {
		return startstep;
	}
	public void setstartstep(int startstep) {
		this.startstep = startstep;
	}
	public int getfinishstep() {
		return finishstep;
	}
	public void setfinishstep(int finishstep) {
		this.finishstep = finishstep;
	}
	public String getCell(int col){
		if (col == 0)
			return (this.planorder + "");
		else if (col == 1)
		    return this.planname;
		else if (col == 2)
		    return this.stepcount + "";
		else if (col == 3)
		    return this.finishstep + "";
		else
		    return "";
	}

}
