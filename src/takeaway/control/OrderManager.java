package takeaway.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeaway.itf.IOrderManager;
import takeaway.model.BeanOrder;
import takeaway.model.BeanRider;
import takeaway.model.BeanStore;
import takeaway.model.BeanUser;
import takeaway.util.BaseException;
import takeaway.util.BusinessException;
import takeaway.util.DBUtil;
import takeaway.util.DbException;

public class OrderManager implements IOrderManager{
	@Override
	public BeanOrder addOrder(String name) throws BaseException {
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
	public List<BeanOrder> loadBysj() throws BaseException {
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		String sjno = BeanStore.currentLoginstore.getsjno();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select user_name,qs_name,dd_zt,mj_no,yh_no,dd_startmoney,dd_endmoney,dd_no from sp_dd,qs_info,add_info where sj_no=? and add_info.add_no=sp_dd.add_no and qs_info.qs_no=sp_dd.qs_no";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, sjno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrder p=new BeanOrder();
		        p.setusername(rs.getString(1));
		        p.setqsname(rs.getString(2));
		        p.setddzt(rs.getString(3));
		        p.setmjno(rs.getInt(4));
		        p.setyhno(rs.getInt(5));
		        p.setddstartmoney(rs.getFloat(6));
		        p.setddendmoney(rs.getFloat(7));
		        p.setddno(rs.getInt(8));
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
	public List<BeanOrder> loadByqs()throws BaseException{
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		String qsno = BeanRider.currentLoginrider.getqsno();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select sj_name,dd_zt,mj_no,yh_no,dd_startmoney,dd_endmoney,dd_no,add_no\r\n" + 
					"from sp_dd,sj_info\r\n" + 
					"where qs_no=? and sj_info.sj_no=sp_dd.sj_no";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, qsno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrder p=new BeanOrder();
		        p.setsjname(rs.getString(1));
		        p.setddzt(rs.getString(2));
		        p.setmjno(rs.getInt(3));
		        p.setyhno(rs.getInt(4));
		        p.setddstartmoney(rs.getFloat(5));
		        p.setddendmoney(rs.getFloat(6));
		        p.setddno(rs.getInt(7));
		        p.setaddno(rs.getInt(8));
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
	public List<BeanOrder> loaduserHisOrder() throws BaseException{
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		String userno = BeanUser.currentLoginUser.getUserid();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select sj_name,qs_name,dd_zt,mj_no,yh_no,dd_startmoney,dd_endmoney,dd_no from sp_dd,sj_info,qs_info where user_no=? and dd_zt=? and sp_dd.sj_no=sj_info.sj_no and sp_dd.qs_no=qs_info.qs_no order by dd_endtime desc";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, userno);
		    pst.setString(2, "已送达");
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrder p=new BeanOrder();
		        p.setsjname(rs.getString(1));
		        p.setqsname(rs.getString(2));
		        p.setddzt(rs.getString(3));
		        p.setmjno(rs.getInt(4));
		        p.setyhno(rs.getInt(5));
		        p.setddstartmoney(rs.getFloat(6));
		        p.setddendmoney(rs.getFloat(7));
		        p.setddno(rs.getInt(8));
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
	public List<BeanOrder> loadstoreHisOrder() throws BaseException{
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		String sjno = BeanStore.currentLoginstore.getsjno();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select dd_no,user_name,qs_name,dd_zt,mj_no,yh_no,dd_startmoney,dd_endmoney\r\n" + 
					"from sp_dd,add_info,qs_info\r\n" + 
					"where sj_no=? and dd_zt=?\r\n" + 
					"and sp_dd.add_no=add_info.add_no\r\n" + 
					"and sp_dd.qs_no=qs_info.qs_no\r\n" + 
					"order by dd_endtime desc";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, sjno);
		    pst.setString(2, "已送达");
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	System.out.println(rs.getString(2));
		    	BeanOrder p=new BeanOrder();
		    	p.setddno(rs.getInt(1));
		    	p.setusername(rs.getString(2));
		        p.setqsname(rs.getString(3));
		        p.setddzt(rs.getString(4));
		        p.setmjno(rs.getInt(5));
		        p.setyhno(rs.getInt(6));
		        p.setddstartmoney(rs.getFloat(7));
		        p.setddendmoney(rs.getFloat(8));
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
	public List<BeanOrder> loadstoreingOrder() throws BaseException{
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		String sjno = BeanStore.currentLoginstore.getsjno();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select dd_no,user_name,qs_name,dd_zt,mj_no,yh_no,dd_startmoney,dd_endmoney\r\n" + 
					"from sp_dd,add_info,qs_info\r\n" + 
					"where sj_no=? and (dd_zt=? or dd_zt=?)\r\n" + 
					"and sp_dd.add_no=add_info.add_no\r\n" + 
					"and sp_dd.qs_no=qs_info.qs_no\r\n" + 
					"order by dd_endtime desc";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, sjno);
		    pst.setString(2, "已超时");
		    pst.setString(3, "配送中");
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrder p=new BeanOrder();
		    	p.setddno(rs.getInt(1));
		    	p.setusername(rs.getString(2));
		        p.setqsname(rs.getString(3));
		        p.setddzt(rs.getString(4));
		        p.setmjno(rs.getInt(5));
		        p.setyhno(rs.getInt(6));
		        p.setddstartmoney(rs.getFloat(7));
		        p.setddendmoney(rs.getFloat(8));
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
	public List<BeanOrder> loaduserIngOrder() throws BaseException{
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		String userno = BeanUser.currentLoginUser.getUserid();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select sj_name,qs_name,dd_zt,mj_no,yh_no,dd_startmoney,dd_endmoney,dd_no from sp_dd,sj_info,qs_info where user_no=? and sj_info.sj_no=sp_dd.sj_no and qs_info.qs_no=sp_dd.qs_no and (dd_zt=? or dd_zt=?)";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, userno);
		    pst.setString(2, "配送中");
		    pst.setString(3, "已超时");
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrder p=new BeanOrder();
		        p.setsjname(rs.getString(1));
		        p.setqsname(rs.getString(2));
		        p.setddzt(rs.getString(3));
		        p.setmjno(rs.getInt(4));
		        p.setyhno(rs.getInt(5));
		        p.setddstartmoney(rs.getFloat(6));
		        p.setddendmoney(rs.getFloat(7));
		        p.setddno(rs.getInt(8));
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
	public List<BeanOrder> loaduserpj() throws BaseException{
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		/*String userno = BeanUser.currentLoginUser.getUserid();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select sj_name,qs_name,dd_zt,mj_no,yh_no,dd_startmoney,dd_endmoney from sp_dd,sj_info,qs_info where user_no=? and sj_info.sj_no=sp_dd.sj_no and qs_info.qs_no=sp_dd.qs_no and (dd_zt=? or dd_zt=?)";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, userno);
		    pst.setString(2, "配送中");
		    pst.setString(3, "已超时");
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrder p=new BeanOrder();
		        p.setsjname(rs.getString(1));
		        p.setqsname(rs.getString(2));
		        p.setddzt(rs.getString(3));
		        p.setmjno(rs.getInt(4));
		        p.setyhno(rs.getInt(5));
		        p.setddstartmoney(rs.getFloat(6));
		        p.setddendmoney(rs.getFloat(7));
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
		}*/
		return result;
	}
	@Override
	public void deleteOrder(BeanOrder plan) throws BaseException {
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
