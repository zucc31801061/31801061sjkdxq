package takeaway.model;

import java.sql.Date;

public class BeanRider {
	public static final String[] tableTitles={"姓名","注册日期","获得称号"};
	public static final String[] tableTitles1={"账号","姓名","注册日期","获得称号"};
	private String qsno;
	private String ddno;
	private String qsname;
	private Date qsdate;
	private String qsid;
	private Float money;
	private Date time;
	private String pj;
	public String getqsno() {
		return qsno;
	}
	public void setqsno(String qsno) {
		this.qsno = qsno;
	}
	public String getddno() {
		return ddno;
	}
	public void setddno(String ddno) {
		this.ddno = ddno;
	}
	public String getqsname() {
		return qsname;
	}
	public void setqsname(String qsname) {
		this.qsname = qsname;
	}
	public Date getqsdate() {
		return qsdate;
	}
	public void setqsdate(Date qsdate) {
		this.qsdate = qsdate;
	}
	public String getqsid() {
		return qsid;
	}
	public void setqsid(String qsid) {
		this.qsid = qsid;
	}
	public Float getmoney() {
		return money;
	}
	public void setmoney(Float money) {
		this.money = money;
	}
	public Date gettime() {
		return time;
	}
	public void settime(Date time) {
		this.time = time;
	}
	public String getpj() {
		return pj;
	}
	public void setpj(String pj) {
		this.pj = pj;
	}
	public String getCell(int col){
		if (col == 0)
			return this.qsname;
		else if (col == 1)
			return this.qsdate+"";
		else if (col == 2)
			return this.qsid;
		else
		    return "";
	}
	public String getCell1(int col){
		if (col == 0)
			return this.qsno;
		else if (col == 1)
			return this.qsname;
		else if (col == 2)
			return this.qsdate+"";
		else if (col == 3)
			return this.qsid;
		else
		    return "";
	}
	public static BeanRider currentLoginrider=null;
}
