package takeaway.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeaway.itf.ISppjManager;
import takeaway.model.BeanSppj;
import takeaway.model.BeanStore;
import takeaway.model.BeanUser;
import takeaway.util.BaseException;
import takeaway.util.BusinessException;
import takeaway.util.DBUtil;
import takeaway.util.DbException;

public class SppjManager implements ISppjManager{
	public BeanSppj addSppj(String pjnr,int pjstar,boolean photo,int ddno) throws BaseException {
		// TODO Auto-generated method stub
		BeanSppj sppj=new BeanSppj();
		String userno = BeanUser.currentLoginUser.getUserid();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql = "insert into sp_pj(sp_no,dd) select sp_no,dd_no from dd_info where dd_no=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, ddno);
			pst.execute();
			pst.close();
			sql = "update sp_pj set pj_nr=?,pj_date=?,pj_star=?,pj_photo=?,sj_no=(select sj_no from sp_dd where dd_no=?),user_no=? where dd=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1,pjnr);
			pst.setTimestamp(2,new java.sql.Timestamp(System.currentTimeMillis()));
			pst.setInt(3,pjstar);
			pst.setBoolean(4,photo);
			pst.setInt(5,ddno);
			pst.setString(6,userno);
			pst.setInt(7,ddno);
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
		return sppj;
	}

	public List<BeanSppj> loadbyuser()throws BaseException {
		List<BeanSppj> result=new ArrayList<BeanSppj>();
		String userno = BeanUser.currentLoginUser.getUserid();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select distinct dd,pj_nr,pj_star,pj_photo,sj_name,user_no,pj_date from sp_pj,sj_info where sp_pj.user_no=? and sj_info.sj_no=sp_pj.sj_no order by pj_date desc";
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
		        p.setuserno(rs.getString(6));
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
