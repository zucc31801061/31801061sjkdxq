package takeaway.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeaway.itf.IKindManager;
import takeaway.model.BeanKind;
import takeaway.model.BeanMjMethod;
import takeaway.model.BeanStore;
import takeaway.model.BeanKind;
import takeaway.util.BaseException;
import takeaway.util.BusinessException;
import takeaway.util.DBUtil;
import takeaway.util.DbException;

public class KindManager implements IKindManager{
	public List<BeanKind> loadAll()throws BaseException{
		List<BeanKind> result=new ArrayList<BeanKind>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select fl_no,fl_name,num from sp_kind order by fl_no";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanKind p=new BeanKind();
		        p.setflno(rs.getInt(1));
		        p.setflname(rs.getString(2));
		        p.setnum(rs.getInt(3));
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
	public void Addfl(String name)throws BaseException{
		if(name.isEmpty())
			throw new BusinessException("分类名为空");
		int flno;
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
		    String sql = "select fl_no from sp_kind order by fl_no desc limit 0,1";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    java.sql.ResultSet rs = pst.executeQuery();
		    if (rs.next()) {
		    	flno=rs.getInt(1) + 1;
		    } 
		    else {
		    	flno=1;
		    }
		    rs.close();
			pst.close();
		    sql = "insert into sp_kind(fl_no,fl_name,num) values(?,?,0)";
		    pst = conn.prepareStatement(sql);
		    pst.setInt(1, flno);
		    pst.setString(2, name);
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
	}
	public void Delfl(BeanKind kind)throws BaseException{
		if(kind.getnum()>0)
			throw new BusinessException("该分类下有商品，不能删除");
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
		    String sql = "delete from sp_kind where fl_no=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setInt(1, kind.getflno());
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
	public void Updfl(BeanKind kind,String name)throws BaseException{
		if(name.isEmpty())
			throw new BusinessException("分类名为空");
		int flno;
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
		    String sql = "update sp_kind set fl_name=? where fl_no=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, name);
		    pst.setInt(2, kind.getflno());
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
	}
}
