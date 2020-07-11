package takeaway.model;

import java.sql.Date;

public class BeanOrder {
	public static final String[] tableTitles={"订单编号","用户","骑手","订单状态","满减编号","优惠券编号","原始金额","结算金额"};
	public static final String[] tableTitles1={"订单编号","商家","骑手","订单状态","满减编号","优惠券编号","原始金额","结算金额"};
	public static final String[] tableTitles2={"订单编号","用户","商家","订单状态"};
	public static final String[] tableTitles3={"订单编号","商家","订单状态"};
	public static final String[] tableTitles4={"订单编号","用户","商家","订单状态","单笔收入","时间","评价"};
	public static final String[] tableTitles5={"订单编号","商家","骑手","下单时间","要求送达","时间","评价"};
	private Float ddstartmoney;
	private Float ddendmoney;
	private Date ddstarttime;
	private Date ddendtime;
	private String ddzt;
	private String sjno;
	private String sjname;
	private int addno;
	private int mjno;
	private int ddno;
	private String qsno;
	private String qsname;
	private String userno;
	private String username;
	private int yhno;
	private Float money;
	private Date time;
	private String pj;
	public String getpj() {
		return pj;
	}
	public void setpj(String pj) {
		this.pj = pj;
	}
	public Date gettime() {
		return time;
	}
	public void settime(Date time) {
		this.time = time;
	}
	public Float getmoney() {
		return money;
	}
	public void setmoney(Float money) {
		this.money = money;
	}
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
	public String getsjname() {
		return sjname;
	}
	public void setsjname(String sjname) {
		this.sjname = sjname;
	}
	public int getaddno() {
		return addno;
	}
	public void setaddno(int addno) {
		this.addno = addno;
	}
	public int getmjno() {
		return mjno;
	}
	public void setmjno(int mjno) {
		this.mjno = mjno;
	}
	public int getddno() {
		return ddno;
	}
	public void setddno(int ddno) {
		this.ddno = ddno;
	}
	public String getqsno() {
		return qsno;
	}
	public void setqsno(String qsno) {
		this.qsno = qsno;
	}
	public String getqsname() {
		return qsname;
	}
	public void setqsname(String qsname) {
		this.qsname = qsname;
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
	public int getyhno() {
		return yhno;
	}
	public void setyhno(int yhno) {
		this.yhno = yhno;
	}
	public String getCell(int col){
		if (col == 0)
			return this.ddno+"";
		else if (col == 1)
			return this.username;
		else if (col == 2)
			return this.qsname;
		else if (col == 3)
			return this.ddzt;
		else if (col == 4)
			return this.mjno+"";
		else if (col == 5)
			return this.yhno+"";
		else if (col == 6)
			return (this.ddstartmoney + "");
		else if (col == 7)
			return (this.ddendmoney + "");
		else
		    return "";
	}
	public String getCell1(int col){
		if (col == 0)
			return this.ddno+"";
		else if (col == 1)
			return this.sjname;
		else if (col == 2)
			return this.qsname;
		else if (col == 3)
			return this.ddzt;
		else if (col == 4)
			return this.mjno+"";
		else if (col == 5)
			return this.yhno+"";
		else if (col == 6)
			return (this.ddstartmoney + "");
		else if (col == 7)
			return (this.ddendmoney + "");
		else
		    return "";
	}
	public String getCell2(int col){
		if (col == 0)
			return this.ddno+"";
		else if (col == 1)
			return this.username;
		else if (col == 2)
			return this.sjname;
		else if (col == 3)
			return this.ddzt;
		else
		    return "";
	}
	public String getCell3(int col){
		if (col == 0)
			return this.ddno+"";
		else if (col == 1)
			return this.sjname;
		else if (col == 2)
			return this.ddzt;
		else
		    return "";
	}
	public String getCell4(int col){
		if (col == 0)
			return this.ddno+"";
		else if (col == 1)
			return this.username;
		else if (col == 2)
			return this.sjname;
		else if (col == 3)
			return this.ddzt;
		else if (col == 4)
			return this.money+"";
		else if (col == 5)
			return this.time+"";
		else if (col == 6)
			return this.pj;
		else
		    return "";
	}
	public String getCell5(int col){
		if (col == 0)
			return this.ddno+"";
		else if (col == 1)
			return this.sjname;
		else if (col == 2)
			return this.qsname;
		else if (col == 3)
			return this.ddstarttime+"";
		else if (col == 4)
			return this.ddendtime+"";
		else if (col == 5)
			return this.time+"";
		else if (col == 6)
			return this.pj;
		else
		    return "";
	}
}
