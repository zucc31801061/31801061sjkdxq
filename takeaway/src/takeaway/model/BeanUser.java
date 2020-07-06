package takeaway.model;

import java.util.Date;

public class BeanUser {
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
	public static BeanUser currentLoginUser=null;
}
