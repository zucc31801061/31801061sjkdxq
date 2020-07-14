package takeaway.model;

import java.sql.Timestamp;

public class BeanOrder {
	public static final String[] tableTitles={"下单时间","用户","骑手","订单状态","满减编号","优惠券编号","原始金额","结算金额"};
	public static final String[] tableTitles1={"订单编号","商家","骑手","订单状态","满减编号","优惠券编号","原始金额","结算金额"};
	public static final String[] tableTitles2={"订单编号","用户","商家","订单状态","下单时间","要求送达时间"};
	public static final String[] tableTitles3={"订单编号","商家","订单状态","下单时间","要求送达时间"};
	public static final String[] tableTitles4={"订单编号","用户","商家","订单状态","单笔收入","时间","评价"};
	public static final String[] tableTitles5={"订单编号","商家","骑手","下单时间","要求送达","时间","评价"};
	public static final String[] tableTitles6={"总单数","总消费","总优惠"};
	public static final String[] tableTitles7={"账号","用户名","总单数","总消费","总优惠"};
	public static final String[] tableTitles8={"账号","骑手名","总单数","总入账"};
	public static final String[] tableTitles9={"总单数","总入账"};
	private Float ddstartmoney;
	private Float ddendmoney;
	private Float sumddendmoney;
	private Float sumddyh;
	private Timestamp ddstarttime;
	private Timestamp ddendtime;
	private String ddzt;
	private String sjno;
	private String sjname;
	private int addno;
	private int mjno;
	private int ddno;
	private int ddcount;
	private String qsno;
	private String qsname;
	private String userno;
	private String username;
	private int yhno;
	private Float money;
	private Float summoney;
	private Timestamp time;
	private String pj;
	public String getpj() {
		return pj;
	}
	public void setpj(String pj) {
		this.pj = pj;
	}
	public Timestamp gettime() {
		return time;
	}
	public void settime(Timestamp time) {
		this.time = time;
	}
	public Float getmoney() {
		return money;
	}
	public void setmoney(Float money) {
		this.money = money;
	}
	public Float getsummoney() {
		return summoney;
	}
	public void setsummoney(Float summoney) {
		this.summoney = summoney;
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
	public Float getsumddendmoney() {
		return sumddendmoney;
	}
	public void setsumddendmoney(Float sumddendmoney) {
		this.sumddendmoney = sumddendmoney;
	}
	public Float getsumddyh() {
		return sumddyh;
	}
	public void setsumddyh(Float sumddyh) {
		this.sumddyh = sumddyh;
	}
	public Timestamp getddstarttime() {
		return ddstarttime;
	}
	public void setddstarttime(Timestamp ddstarttime) {
		this.ddstarttime = ddstarttime;
	}
	public Timestamp getddendtime() {
		return ddendtime;
	}
	public void setddendtime(Timestamp ddendtime) {
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
	public int getddcount() {
		return ddcount;
	}
	public void setddcount(int ddcount) {
		this.ddcount = ddcount;
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
			return this.ddstarttime+"";
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
		else if (col == 4)
			return this.ddstarttime+"";
		else if (col == 5)
			return this.ddendtime+"";
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
		else if (col == 3)
			return this.ddstarttime+"";
		else if (col == 4)
			return this.ddendtime+"";
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
	public String getCell6(int col){
		if (col == 0)
			return this.ddcount+"";
		else if (col == 1)
			return this.sumddendmoney+"";
		else if (col == 2)
			return this.sumddyh+"";
		else
		    return "";
	}
	public String getCell7(int col){
		if (col == 0)
			return this.userno;
		else if (col == 1)
			return this.username;
		else if (col == 2)
			return this.ddcount+"";
		else if (col == 3)
			return this.sumddendmoney+"";
		else if (col == 4)
			return this.sumddyh+"";
		else
		    return "";
	}
	public String getCell8(int col){
		if (col == 0)
			return this.qsno;
		else if (col == 1)
			return this.qsname;
		else if (col == 2)
			return this.ddcount+"";
		else if (col == 3)
			return this.summoney+"";
		else
		    return "";
	}
	public String getCell9(int col){
		if (col == 0)
			return this.ddcount+"";
		else if (col == 1)
			return this.summoney+"";
		else
		    return "";
	}
	public static BeanOrder currentLoginOrder=null;
}
