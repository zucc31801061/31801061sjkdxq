package takeaway.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BeanProduct {
	public static final String[] tblStepTitle={"序号","名称","计划开始时间","计划完成时间","实际开始时间","实际完成时间"};
	/**
	 * 请自行根据javabean的设计修改本函数代码，col表示界面表格中的列序号，0开始
	 */
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private int steporder;
	private int stepid;
	private int planid;
	private String userid;
	private String stepname;
	private Date planbegintime;
	private Date planendtime;
	private Date realbegintime = null;
	private Date realendtime = null;
	public int getSteporder() {
		return steporder;
	}
	public void setSteporder(int steporder) {
	    this.steporder = steporder;
	}
	public String getUserid() {
	    return userid;
	}
	public void setUserid(String userid) {
	    this.userid = userid;
	}
	public String getStepname() {
	    return stepname;
	}
	public void setStepname(String stepname) {
	    this.stepname = stepname;
	}
	public Date getPlanbegin_time() {
	    return planbegintime;
	}
	public void setPlanbegintime(Date planbegintime) {
	    this.planbegintime = planbegintime;
	}
	public Date getPlanendtime() {
	    return planendtime;
	}
	public void setPlanendtime(Date planendtime) {
	    this.planendtime = planendtime;
	}
	public Date getRealbegintime() {
	    return realbegintime;
	}
	public void setRealbegintime(Date realbegintime) {
		this.realbegintime = realbegintime;
	}
	public Date getRealendtime() {
	    return realendtime;
	}
	public void setRealendtime(Date realendtime) {
	    this.realendtime = realendtime;
	}
	public int getStepid() {
	    return stepid;
	}
	public void setStepid(int stepid) {
	    this.stepid = stepid;
	}
	public int getPlanid() {
	    return planid;
	}
	public void setPlanid(int planid) {
	    this.planid = planid;
	}
	public String getCell(int col){
		if (col == 0)
			return this.steporder + "";
		else if (col == 1)
		    return this.stepname;
		else if (col == 2)
		    return sdf.format(this.planbegintime);
		else if (col == 3)
		    return sdf.format(this.planendtime);
		else if (col == 4)
		    return this.realbegintime == null ? "" : sdf.format(this.realbegintime);
		else if (col == 5)
		    return this.realendtime == null ? "" : sdf.format(this.realendtime);
		else
		    return "";
	}
}
