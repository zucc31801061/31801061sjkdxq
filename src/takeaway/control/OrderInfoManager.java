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
import takeaway.model.BeanStore;
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
			String sql = "select sp_name,dd_no,num,price,discount from dd_info,sp_info where dd_no=? and dd_info.sp_no=sp_info.sp_no";
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
	@Override
	public void deleteOrderInfo(BeanOrderInfo plan) throws BaseException {
		/*Connection conn=null;
		try {
			conn = DBUtil.getConnection();
		    String sql = "select finished_step_count,step_count from tbl_plan where user_id=? and plan_order=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, BeanUser.currentLoginUser.getUserid());
		    pst.setInt(2, plan.getplanorder());
		    ResultSet rs = pst.executeQuery();
		    rs.next();
		    if (rs.getInt(1) < rs.getInt(2)) {
		    	throw new BusinessException("计划执行步骤有误");
		    }
		    sql = "delete from tbl_plan where user_id=? and plan_order=?";
		    pst = conn.prepareStatement(sql);
		    pst.setString(1, BeanUser.currentLoginUser.getUserid());
		    pst.setInt(2, plan.getplanorder());
		    pst.executeUpdate();
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
		}*/
	}
}
