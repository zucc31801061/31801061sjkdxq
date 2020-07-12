package takeaway.model;

import java.sql.Timestamp;

public class BeanSppj {
	public static final String[] tableTitles={"订单编号","评价","评价日期","星级","图片","商家"};
	public static final String[] tableTitles1={"订单编号","商品","评价","评价日期","星级","图片","用户"};
	public static final String[] tableTitles2={"评价","评价日期","星级","图片","用户"};
	private String pjnr;
	private Timestamp pjdate;
	private int pjstar;
	private boolean photo;
	private String sjno;
	private String sjname;
	private String userno;
	private String username;
	private int dd;
	private int spno;
	private String spname;
	public void setspname(String spname) {
		this.spname = spname;
	}
	public String getspname() {
		return spname;
	}
	public void setpjnr(String pjnr) {
		this.pjnr = pjnr;
	}
	public String getpjnr() {
		return pjnr;
	}
	public Timestamp getpjdate() {
		return pjdate;
	}
	public void setpjdate(Timestamp pjdate) {
		this.pjdate = pjdate;
	}
	public int getpjstar() {
		return pjstar;
	}
	public void setpjstar(int pjstar) {
		this.pjstar = pjstar;
	}
	public boolean getphoto() {
	    return photo;
	}
	public void setphoto(boolean photo) {
	    this.photo = photo;
	}
	public String getsjno() {
	    return sjno;
	}
	public void setsjno(String sjno) {
	    this.sjno = sjno;
	}
	public String getsjname() {
	    return sjname;
	}
	public void setsjname(String sjname) {
	    this.sjname = sjname;
	}
	public String getuserno() {
		return userno;
	}
	public void setuserno(String userno) {
		this.userno = userno;
	}
	public String getusername() {
		return username;
	}
	public void setusername(String username) {
		this.username = username;
	}
	public int getdd() {
		return dd;
	}
	public void setdd(int dd) {
		this.dd = dd;
	}
	public int getspno() {
		return spno;
	}
	public void setspno(int spno) {
		this.spno = spno;
	}
	public String getCell(int col){
		if (col == 0)
			return this.dd+"";
		else if (col == 1)
			return this.pjnr;
		else if (col == 2)
			return this.pjdate+"";
		else if (col == 3)
			return this.pjstar+"";
		else if (col == 4)
			if(this.photo==true) {
				return "如图";
			}
			else {
				return "无";
			}
		else if (col == 5)
			return this.sjname;
		else
		    return "";
	}
	public String getCell1(int col){
		if (col == 0)
			return this.dd+"";
		else if (col == 1)
			return this.spname;
		else if (col == 2)
			return this.pjnr;
		else if (col == 3)
			return this.pjdate+"";
		else if (col == 4)
			return this.pjstar+"";
		else if (col == 5)
			if(this.photo==true) {
				return "如图";
			}
			else {
				return "无";
			}
		else if (col == 5)
			return this.username;
		else
		    return "";
	}
	public String getCell2(int col){
		if (col == 0)
			return this.pjnr;
		else if (col == 1)
			return this.pjdate+"";
		else if (col == 2)
			return this.pjstar+"";
		else if (col == 3)
			if(this.photo==true) {
				return "如图";
			}
			else {
				return "无";
			}
		else if (col == 4)
			return this.username;
		else
		    return "";
	}
}
