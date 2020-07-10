package takeaway.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeaway.itf.IKindManager;
import takeaway.model.BeanKind;
import takeaway.model.BeanStore;
import takeaway.util.BaseException;
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
}
