package takeaway.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeaway.itf.ISppjManager;
import takeaway.model.BeanProduct;
import takeaway.model.BeanSppj;
import takeaway.model.BeanStore;
import takeaway.model.BeanUser;
import takeaway.util.BaseException;
import takeaway.util.BusinessException;
import takeaway.util.DBUtil;
import takeaway.util.DbException;

public class SppjManager implements ISppjManager{
	public void addSppj(BeanSppj sppj,String pjnr,int pjstar,boolean photo) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql = "update sp_pj\r\n" + 
					"set pj_nr=?,pj_date=?,pj_star=?,pj_photo=?\r\n" + 
					"where dd=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1,pjnr);
			pst.setTimestamp(2,new java.sql.Timestamp(System.currentTimeMillis()));
			pst.setInt(3,pjstar);
			pst.setBoolean(4,photo);
			pst.setInt(5,sppj.getdd());
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
	}

	public List<BeanSppj> loadbyuser()throws BaseException {
		List<BeanSppj> result=new ArrayList<BeanSppj>();
		String userno = BeanUser.currentLoginUser.getUserid();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select distinct dd,pj_nr,pj_star,pj_photo,sj_name,pj_date\r\n" + 
					"from sp_pj,sj_info,sp_dd\r\n" + 
					"where sp_dd.user_no=?\r\n" + 
					"and sj_info.sj_no=sp_pj.sj_no\r\n" + 
					"order by pj_date desc";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, userno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanSppj p=new BeanSppj();
		        p.setdd(rs.getInt(1));
		        p.setpjnr(rs.getString(2));
		        p.setpjstar(rs.getInt(3));
		        p.setphoto(rs.getBoolean(4));
		        p.setsjname(rs.getString(5));
		        p.setuserno(userno);
		        p.setpjdate(rs.getDate(6));
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
	public List<BeanSppj> loadbystore()throws BaseException {
		List<BeanSppj> result=new ArrayList<BeanSppj>();
		String sjno=BeanStore.currentLoginstore.getsjno();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select distinct dd,pj_nr,pj_star,pj_photo,user_name,sj_no,pj_date from sp_pj,user_info where sp_pj.sj_no=? and user_info.user_no=sp_pj.user_no order by pj_date desc";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, sjno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanSppj p=new BeanSppj();
		    	p.setdd(rs.getInt(1));
		        p.setpjnr(rs.getString(2));
		        p.setpjstar(rs.getInt(3));
		        p.setphoto(rs.getBoolean(4));
		        p.setusername(rs.getString(5));
		        p.setsjno(rs.getString(6));
		        p.setpjdate(rs.getDate(7));
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
	public List<BeanSppj> loadpropj(BeanProduct product)throws BaseException{
		List<BeanSppj> result=new ArrayList<BeanSppj>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select pj_nr,pj_star,pj_date,pj_photo,user_name\r\n" + 
					"from sp_pj,user_info\r\n" + 
					"where sp_no=? and sp_pj.user_no=user_info.user_no\r\n" + 
					"order by pj_date desc";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setInt(1, product.getspno());
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanSppj p=new BeanSppj();
		    	p.setpjnr(rs.getString(1));
		    	p.setpjstar(rs.getInt(2));
		    	p.setpjdate(rs.getDate(3));
		    	p.setphoto(rs.getBoolean(4));
		    	p.setusername(rs.getString(5));
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
	public void deleteSppj(BeanSppj sppj)throws BaseException {
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
		    String sql = "delete from sp_pj where user_no=? and dd=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, BeanUser.currentLoginUser.getUserid());
		    pst.setInt(2, sppj.getdd());
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
		}
	}
}
