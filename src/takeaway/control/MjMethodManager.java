package takeaway.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeaway.itf.IMjMethodManager;
import takeaway.model.BeanMjMethod;
import takeaway.model.BeanStore;
import takeaway.util.BaseException;
import takeaway.util.DBUtil;
import takeaway.util.DbException;

public class MjMethodManager implements IMjMethodManager {
	@Override
	public List<BeanMjMethod> loadAll() throws BaseException {
		List<BeanMjMethod> result=new ArrayList<BeanMjMethod>();
		String sjno=BeanStore.currentLoginstore.getsjno();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select mj_no,sj_no,mj_money,mj_yh,mj_dj from mj_method where sj_no=? order by mj_no";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, sjno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanMjMethod p=new BeanMjMethod();
		        p.setmjno(rs.getInt(1));
		        p.setsjno(rs.getString(2));
		        p.setmjmoney(rs.getFloat(3));
		        p.setmjyh(rs.getFloat(4));
		        p.setdj(rs.getBoolean(5));
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
	public BeanMjMethod Addmj(Float money,Float yh,Boolean yhdj)throws BaseException {
		String sjno = BeanStore.currentLoginstore.getsjno();
		BeanMjMethod method=new BeanMjMethod();
		int mjno;
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
		    String sql = "select mj_no from mj_method where sj_no=? order by mj_no desc limit 0,1";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, sjno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    if (rs.next()) {
		    	mjno=rs.getInt(1) + 1;
		    } 
		    else {
		    	mjno=1;
		    }
		    rs.close();
			pst.close();
		    sql = "insert into mj_method(mj_no,sj_no,mj_money,mj_yh,mj_dj) values(?,?,?,?,?)";
		    pst = conn.prepareStatement(sql);
		    pst.setInt(1, mjno);
		    pst.setString(2, sjno);
		    pst.setFloat(3, money);
		    pst.setFloat(4, yh);
		    pst.setBoolean(5, yhdj);
		    pst.execute();
		    pst.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		    throw new DbException(e);
		} finally {
		    if (conn != null)
		    	try {
		    		conn.close();
		        } catch (SQLException e) {
		          e.printStackTrace();
		        }
		}
		return method;
	}
	@Override
	public void Delmj(BeanMjMethod method)throws BaseException {
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
		    String sql = "delete from mj_method where sj_no=? and mj_no=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, BeanStore.currentLoginstore.getsjno());
		    pst.setInt(2, method.getmjno());
		    pst.executeUpdate();
		    pst.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		    throw new DbException(e);
		} finally {
		    if (conn != null)
		    	try {
		    		conn.close();
		        } catch (SQLException e) {
		          e.printStackTrace();
		        }
		}
	}
	public void Updmj(BeanMjMethod method,Float money,Float yh,Boolean yhdj)throws BaseException{
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
		    String sql = "update mj_method set mj_money=?,mj_yh=?,mj_dj=? where mj_no=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setFloat(1, money);
		    pst.setFloat(2, yh);
		    pst.setBoolean(3, yhdj);
		    pst.setInt(4, method.getmjno());
		    pst.executeUpdate();
		    pst.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		    throw new DbException(e);
		} finally {
		    if (conn != null)
		    	try {
		    		conn.close();
		        } catch (SQLException e) {
		          e.printStackTrace();
		        }
		}
	}
}
