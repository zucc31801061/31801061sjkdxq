package takeaway.model;

import java.sql.Date;

public class BeanYhInfo {
	public static final String[] tableTitles={"优惠金额","商家","订单编号"};
	public static final String[] tableTitles1={"优惠金额","起始日期","结束日期","商家"};
	public static final String[] tableTitles2={"优惠金额","商家","集单要求","已订单数"};
	private int yhmoney;
	private int jdnum;
	private Date startdate;
	private Date enddate;
	private int yhno;
	private String sjno;
	private String sjname;
	private int ddno;
	private int already;
	public int getyhmoney() {
		return yhmoney;
	}
	public void setyhmoney(int yhmoney) {
		this.yhmoney = yhmoney;
	}
	public int getjdnum() {
		return jdnum;
	}
	public void setjdnum(int jdnum) {
		this.jdnum = jdnum;
	}
	public Date getstartdate() {
		return startdate;
	}
	public void setstartdate(Date startdate) {
		this.startdate = startdate;
	}public Date getenddate() {
		return enddate;
	}
	public void setenddate(Date enddate) {
		this.enddate = enddate;
	}
	public int getyhno() {
		return yhno;
	}
	public void setyhno(int yhno) {
		this.yhno = yhno;
	}
	public void setsjno(String sjno) {
		this.sjno = sjno;
	}
	public String getsjno() {
		return sjno;
	}
	public void setsjname(String sjname) {
		this.sjname = sjname;
	}
	public String getsjname() {
		return sjname;
	}
	public int getddno() {
		return ddno;
	}
	public void setddno(int ddno) {
		this.ddno = ddno;
	}
	public int getalready() {
		return already;
	}
	public void setalready(int already) {
		this.already = already;
	}
	public String getCell(int col){
		if (col == 0)
			return this.yhmoney+"";
		else if (col == 1)
			return this.sjname;
		else if (col == 2)
			return this.ddno+"";
		else
		    return "";
	}
	public String getCell1(int col){
		if (col == 0)
			return this.yhmoney+"";
		else if (col == 1)
			return this.startdate+"";
		else if (col == 2)
			return this.enddate+"";
		else if (col == 3)
			return this.sjname;
		else
		    return "";
	}
	public String getCell2(int col){
		if (col == 0)
			return this.yhmoney+"";
		else if (col == 1)
			return this.sjname;
		else if (col == 2)
			return this.jdnum+"";
		else if (col == 3)
			return this.already+"";
		else
		    return "";
	}
}
