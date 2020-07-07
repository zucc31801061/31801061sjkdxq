package takeaway.model;

import java.sql.Date;

public class BeanOrder {
	public static final String[] tableTitles={"ÓÃ»§±àºÅ","ÆïÊÖ±àºÅ","¶©µ¥×´Ì¬","Âú¼õ±àºÅ","ÓÅ»İÈ¯±àºÅ","Ô­Ê¼½ğ¶î","½áËã½ğ¶î"};
	private Float ddstartmoney;
	private Float ddendmoney;
	private Date ddstarttime;
	private Date ddendtime;
	private String ddzt;
	private String sjno;
	private String addno;
	private String mjno;
	private String ddno;
	private String qsno;
	private String userno;
	private String yhno;
	public Float getddstartmoney() {
		return ddstartmoney;
	}
	public void setddstartmoney(Float ddstartmoney) {
		this.ddstartmoney = ddstartmoney;
	}
	public Float getddendmoney() {
		return ddendmoney;
	}
	public void setddendmoney(Float ddendmoney) {
		this.ddendmoney = ddendmoney;
	}
	public Date getddstarttime() {
		return ddstarttime;
	}
	public void setddstarttime(Date ddstarttime) {
		this.ddstarttime = ddstarttime;
	}
	public Date getddendtime() {
		return ddendtime;
	}
	public void setddendtime(Date ddendtime) {
		this.ddendtime = ddendtime;
	}
	public String getddzt() {
		return ddzt;
	}
	public void setddzt(String ddzt) {
		this.ddzt = ddzt;
	}
	public String getsjno() {
		return sjno;
	}
	public void setsjno(String sjno) {
		this.sjno = sjno;
	}
	public String getaddno() {
		return addno;
	}
	public void setaddno(String addno) {
		this.addno = addno;
	}public String getmjno() {
		return mjno;
	}
	public void setmjno(String mjno) {
		this.mjno = mjno;
	}
	public String getddno() {
		return ddno;
	}
	public void setddno(String ddno) {
		this.ddno = ddno;
	}public String getqsno() {
		return qsno;
	}
	public void setqsno(String qsno) {
		this.qsno = qsno;
	}public String getuserno() {
		return userno;
	}
	public void setuserno(String userno) {
		this.userno = userno;
	}public String getyhno() {
		return yhno;
	}
	public void setyhno(String yhno) {
		this.yhno = yhno;
	}
	public String getCell(int col){
		if (col == 0)
			return this.userno;
		else if (col == 1)
			return this.qsno;
		else if (col == 2)
			return this.ddzt;
		else if (col == 3)
			return this.mjno;
		else if (col == 4)
			return this.yhno;
		else if (col == 5)
			return (this.ddstartmoney + "");
		else if (col == 6)
			return (this.ddendmoney + "");
		else
		    return "";
	}
}
