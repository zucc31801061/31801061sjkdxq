package takeaway.model;

public class BeanOrderInfo {
	public static final String[] tblOrderInfoTitle={"商家","商品","商品数量","商品价格","单品优惠"};
	public static final String[] tblOrderInfoTitle1={"商品","商家","分类","商品价格","单品优惠","总销量"};
	private int spno;
	private String spname;
	private String sjname;
	private String flname;
	private int ddno;
	private int num;
	private int sumnum;
	private Float price;
	private Float discount;
	public int getspno() {
		return spno;
	}
	public void setspno(int spno) {
		this.spno = spno;
	}
	public String getspname() {
		return spname;
	}
	public void setspname(String spname) {
		this.spname = spname;
	}
	public String getsjname() {
		return sjname;
	}
	public void setsjname(String sjname) {
		this.sjname = sjname;
	}
	public String getflname() {
		return flname;
	}
	public void setflname(String flname) {
		this.flname = flname;
	}
	public int getddno() {
		return ddno;
	}
	public void setddno(int ddno) {
		this.ddno = ddno;
	}
	public int getnum() {
		return num;
	}
	public void setnum(int num) {
		this.num = num;
	}
	public int getsumnum() {
		return sumnum;
	}
	public void setsumnum(int sumnum) {
		this.sumnum = sumnum;
	}
	public Float getprice() {
		return price;
	}
	public void setprice(Float price) {
		this.price = price;
	}
	public Float getdiscount() {
		return discount;
	}
	public void setdiscount(Float discount) {
		this.discount = discount;
	}
	public String getCell(int col){
		if (col == 0)
			return this.sjname;
		else if (col == 1)
			return this.spname;
		else if (col == 2)
			return this.num + "";
		else if (col == 3)
		    return this.price + "";
		else if (col == 4)
		    return this.discount + "";
		else
		    return "";
	}
	public String getCell1(int col){
		if (col == 0)
			return this.spname;
		else if (col == 1)
		    return this.sjname;
		else if (col == 2)
		    return this.flname;
		else if (col == 3)
		    return this.price + "";
		else if (col == 4)
		    return this.discount + "";
		else if (col == 5)
		    return this.sumnum + "";
		else
		    return "";
	}
}
