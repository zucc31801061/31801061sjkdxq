package takeaway.model;

import java.util.Date;

public class BeanUser {
	public static final String[] tableTitles={"姓名","性别","电话号","邮箱地址","所在城市"};
	public static final String[] tableTitles1={"是否是会员","到期时间"};
	private String username;
	private String usersex;
	private String phnum;
	private String email;
	private String city;
	private boolean vip;
	private Date vipenddate;
	private String userid;
	private String pwd;
	private Date createDate;
	
	public void setvip(boolean vip) {
		this.vip = vip;
	}
	public boolean getvip() {
		return vip;
	}
	public Date getvipenddate() {
		return vipenddate;
	}
	public void setvipenddate(Date vipenddate) {
		this.vipenddate = vipenddate;
	}
	public void setusername(String username) {
		this.username = username;
	}
	public String getusername() {
		return username;
	}
	public void setusersex(String usersex) {
		this.usersex = usersex;
	}
	public String getusersex() {
		return usersex;
	}
	public void setphnum(String phnum) {
		this.phnum = phnum;
	}
	public String getphnum() {
		return phnum;
	}
	public void setemail(String email) {
		this.email = email;
	}
	public String getemail() {
		return email;
	}
	public void setcity(String city) {
		this.city = city;
	}
	public String getcity() {
		return city;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCell(int col){
		if (col == 0)
			return this.username;
		else if (col == 1)
			return this.usersex;
		else if (col == 2)
			return this.phnum;
		else if (col == 3)
			return this.email;
		else if (col == 4)
			return this.city;
		else
		    return "";
	}
	public String getCell1(int col){
		if (col == 0)
			return String.valueOf(this.vip);
		else if (col == 1)
			return String.valueOf(this.vipenddate);
		    return "";
	}
	public static BeanUser currentLoginUser=null;

}
