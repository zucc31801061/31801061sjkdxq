package takeaway.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BeanProduct {
	public static final String[] tblProductTitle={"商品名","分类","原价","优惠价"};
	/**
	 * 请自行根据javabean的设计修改本函数代码，col表示界面表格中的列序号，0开始
	 */
	private int spno;
	private int flno;
	private String flname;
	private String sjno;
	private String spname;
	private Float spmoney;
	private Float yhmoney;
	public int getspno() {
		return spno;
	}
	public void setspno(int spno) {
	    this.spno = spno;
	}
	public int getflno() {
		return flno;
	}
	public void setflno(int flno) {
	    this.flno = flno;
	}
	public String getflname() {
	    return flname;
	}
	public void setflname(String flname) {
	    this.flname = flname;
	}
	public String getsjno() {
	    return sjno;
	}
	public void setsjno(String sjno) {
	    this.sjno = sjno;
	}
	public String getspname() {
	    return spname;
	}
	public void setspname(String spname) {
	    this.spname = spname;
	}
	public Float getspmoney() {
	    return spmoney;
	}
	public void setspmoney(Float spmoney) {
	    this.spmoney = spmoney;
	}
	public Float getyhmoney() {
	    return yhmoney;
	}
	public void setyhmoney(Float yhmoney) {
	    this.yhmoney = yhmoney;
	}
	public String getCell(int col){
		if (col == 0)
			return this.spname;
		else if (col == 1)
		    return this.flname;
		else if (col == 2)
		    return this.spmoney+"";
		else if (col == 3)
		    return this.yhmoney+"";
		else
		    return "";
	}
}
