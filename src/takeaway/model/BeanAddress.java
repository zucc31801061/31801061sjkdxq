package takeaway.model;

public class BeanAddress {
	public static final String[] tableTitles={"编号","省","市","区","地址","姓名","手机号"};
	private String addno;
	private String userno;
	private String sheng;
	private String shi;
	private String qu;
	private String address;
	private String username;
	private String userphnum;
	public String getaddno() {
		return addno;
	}
	public void setaddno(String addno) {
		this.addno = addno;
	}
	public String getuserno() {
		return userno;
	}
	public void setuserno(String userno) {
		this.userno = userno;
	}
	public String getsheng() {
		return sheng;
	}
	public void setsheng(String sheng) {
		this.sheng = sheng;
	}
	public String getshi() {
		return shi;
	}
	public void setshi(String shi) {
		this.shi = shi;
	}public String getqu() {
		return qu;
	}
	public void setqu(String qu) {
		this.qu = qu;
	}public String getaddress() {
		return address;
	}
	public void setaddress(String address) {
		this.address = address;
	}public String getusername() {
		return username;
	}
	public void setusername(String username) {
		this.username = username;
	}
	public String getuserphnum() {
		return userphnum;
	}
	public void setuserphnum(String userphnum) {
		this.userphnum = userphnum;
	}
	public String getCell(int col){
		if (col == 0)
			return this.addno;
		else if (col == 1)
			return this.sheng;
		else if (col == 2)
			return this.shi;
		else if (col == 3)
			return this.qu;
		else if (col == 4)
			return this.address;
		else if (col == 5)
			return this.username;
		else if (col == 6)
			return this.userphnum;
		else
		    return "";
	}
}
