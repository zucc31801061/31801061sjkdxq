package takeaway.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeaway.itf.IOrderInfoManager;
import takeaway.model.BeanOrder;
import takeaway.model.BeanOrderInfo;
import takeaway.model.BeanProduct;
import takeaway.model.BeanStore;
import takeaway.model.BeanUser;
import takeaway.util.BaseException;
import takeaway.util.DBUtil;
import takeaway.util.DbException;

public class OrderInfoManager implements IOrderInfoManager{
	@Override
	public BeanOrderInfo addOrderInfo(String name) throws BaseException {
		// TODO Auto-generated method stub
		/*if(name.isEmpty()) 
			throw new BusinessException("商家名为空");
		BeanOrder store=new BeanOrder();
		String userno = BeanUser.currentLoginUser.getUserid();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql = "select * from sj_info where sj_no = ?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userno);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("您已是商家，请点击“已是商家”");
			rs.close();
			pst.close();
			sql = "insert into sj_info(sj_name,sj_no) values(?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1,name);
			pst.setString(2,userno);
			pst.execute();
		    pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return store;*/
		return null; 
	}

	@Override
	public List<BeanOrderInfo> loadOrderInfo(BeanOrder order) throws BaseException {
		List<BeanOrderInfo> result=new ArrayList<BeanOrderInfo>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select sp_name,sp_dd.dd_no,num,price,discount,sj_name\r\n" + 
					"from dd_info,sp_info,sj_info,sp_dd\r\n" + 
					"where sp_dd.dd_no=?\r\n" + 
					"and sp_dd.sj_no=sj_info.sj_no\r\n" + 
					"and dd_info.sp_no=sp_info.sp_no\r\n" + 
					"and sp_dd.dd_no=dd_info.dd_no";
		    PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setInt(1, order.getddno());
		    ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrderInfo p=new BeanOrderInfo();
		        p.setspname(rs.getString(1));
		        p.setddno(rs.getInt(2));
		        p.setnum(rs.getInt(3));
		        p.setprice(rs.getFloat(4));
		        p.setdiscount(rs.getFloat(5));
		        p.setsjname(rs.getString(6));
		        result.add(p);
		    }
		    rs.close();
		    pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		    throw new DbException(e);
		} 
		finally {
		    if (conn != null)
		    	try {
		    		conn.close();
		        } catch (SQLException e) {
		        	e.printStackTrace();
		        }
		}
		return result;
	}
	public List<BeanOrderInfo> loadretj()throws BaseException{
		List<BeanOrderInfo> result=new ArrayList<BeanOrderInfo>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select sp_name,sj_name,fl_name,sp_money,sp_yh,sum(dd_info.num),sp_info.sp_no\r\n" + 
					"from dd_info,sp_info,sj_info,sp_kind\r\n" + 
					"where dd_info.sp_no=sp_info.sp_no\r\n" + 
					"and sp_info.sj_no=sj_info.sj_no\r\n" + 
					"and sp_info.fl_no=sp_kind.fl_no\r\n" + 
					"group by sp_name\r\n" + 
					"order by sum(dd_info.num) desc";
		    PreparedStatement pst = conn.prepareStatement(sql);
		    ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrderInfo p=new BeanOrderInfo();
		        p.setspname(rs.getString(1));
		        p.setsjname(rs.getString(2));
		        p.setflname(rs.getString(3));
		        p.setprice(rs.getFloat(4));
		        p.setdiscount(rs.getFloat(5));
		        p.setsumnum(rs.getInt(6));
		        p.setspno(rs.getInt(7));
		        result.add(p);
		    }
		    rs.close();
		    pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		    throw new DbException(e);
		} 
		finally {
		    if (conn != null)
		    	try {
		    		conn.close();
		        } catch (SQLException e) {
		        	e.printStackTrace();
		        }
		}
		return result;
	}
	public void addproduct(BeanProduct product,int num)throws BaseException{
		int ddno = BeanOrder.currentLoginOrder.getddno();
		String userno=BeanUser.currentLoginUser.getUserid(); 
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql = "select dd_no,sp_no\r\n" + 
					"from dd_info\r\n" + 
					"where dd_no=? and sp_no=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, ddno);
			pst.setInt(2, product.getspno());
			java.sql.ResultSet rs=pst.executeQuery();
			if (rs.next()) {
				if(rs.getInt(1)==ddno && rs.getInt(2)==product.getspno()) {
					String sql1 = "update dd_info\r\n" + 
							"set num=num+?\r\n" + 
							"where dd_no=? and sp_no=?";
					java.sql.PreparedStatement pst1=conn.prepareStatement(sql1);
					pst1.setInt(1, num);
					pst1.setInt(2, ddno);
					pst1.setInt(3, product.getspno());
					pst1.execute();
					pst1.close();
				}
		    } 
			else {
				String sql1 = "insert into dd_info(sp_no,dd_no,num,price,discount)\r\n" + 
						"values(?,?,?,?,?)";
				java.sql.PreparedStatement pst1=conn.prepareStatement(sql1);
				pst1.setInt(1, product.getspno());
				pst1.setInt(2, ddno);
				pst1.setInt(3, num);
				pst1.setFloat(4, product.getspmoney());
				pst1.setFloat(5, product.getyhmoney());
				pst1.execute();
				pst1.close();
			}
			rs.close();
			pst.close();
			sql = "select vip\r\n" + 
					"from user_info\r\n" + 
					"where user_no=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, userno);
			rs=pst.executeQuery();
			if(rs.next()) {
				if(rs.getBoolean(1)==true) {
					String sql1 = "update sp_dd\r\n" + 
							"set dd_startmoney=dd_startmoney+?*?,dd_endmoney=dd_endmoney+?*?\r\n" +
							"where dd_no=?";
					java.sql.PreparedStatement pst1=conn.prepareStatement(sql1);
					pst1.setFloat(1, product.getspmoney());
					pst1.setInt(2, num);
					pst1.setFloat(3, product.getyhmoney());
					pst1.setInt(4, num);
					pst1.setInt(5, ddno);
					pst1.execute();
					pst1.close();
				}
				else {
					String sql1 = "update sp_dd\r\n" + 
							"set dd_startmoney=dd_startmoney+?*?,dd_endmoney=dd_endmoney+?*?\r\n" +
							"where dd_no=?";
					java.sql.PreparedStatement pst1=conn.prepareStatement(sql1);
					pst1.setFloat(1, product.getspmoney());
					pst1.setInt(2, num);
					pst1.setFloat(3, product.getspmoney());
					pst1.setInt(4, num);
					pst1.setInt(5, ddno);
					pst1.execute();
					pst1.close();
				}
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
