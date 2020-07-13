package takeaway.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeaway.itf.IStoreManager;
import takeaway.model.BeanStore;
import takeaway.model.BeanUser;
import takeaway.util.BaseException;
import takeaway.util.BusinessException;
import takeaway.util.DBUtil;
import takeaway.util.DbException;

public class StoreManager implements IStoreManager {
	@Override
	public BeanStore addStore(String name) throws BaseException {
		// TODO Auto-generated method stub
		if(name.isEmpty()) 
			throw new BusinessException("商家名为空");
		BeanStore store=new BeanStore();
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
		return store;
	}

	@Override
	public BeanStore login() throws BaseException {
		BeanStore store=new BeanStore();
		String userno = BeanUser.currentLoginUser.getUserid();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select sj_no from sj_info where sj_no=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userno);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("您还不是商家");
			store.setsjno(rs.getString(1));
			rs.close();
			pst.close();
			return store;
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
	@Override
	public List<BeanStore> loadAll() throws BaseException {
		List<BeanStore> result=new ArrayList<BeanStore>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "update sj_info set sj_avgxf=(select avg(dd_endmoney) from sp_dd where sj_info.sj_no=sp_dd.sj_no) ,sj_sumxl=(select count(dd_no) from sp_dd where sj_info.sj_no=sp_dd.sj_no) ,sj_star=(select avg(pj_star) from sp_pj where sp_pj.sj_no=sj_info.sj_no)";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
	    	pst.execute();
	    	pst.close();
			sql = "select sj_name,sj_star,sj_avgxf,sj_sumxl,sj_no from sj_info order by sj_star DESC,sj_avgxf";
			pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanStore p=new BeanStore();
		        p.setsjname(rs.getString(1));
		        p.setsjstar(rs.getInt(2));
		        p.setsjavgxf(rs.getFloat(3));
		        p.setsjsumxl(rs.getFloat(4));
		        p.setsjno(rs.getString(5));
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
	public List<BeanStore> selectstore(String sjno)throws BaseException{
		if(sjno.isEmpty())
			throw new BusinessException("账号为空");
		List<BeanStore> result=new ArrayList<BeanStore>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select sj_no from sj_info where sj_no=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,sjno);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("商家不存在");
			rs.close();
			pst.close();
			sql = "update sj_info set sj_avgxf=(select avg(dd_endmoney) from sp_dd where sj_info.sj_no=sp_dd.sj_no) ,sj_sumxl=(select count(dd_no) from sp_dd where sj_info.sj_no=sp_dd.sj_no) ,sj_star=(select avg(pj_star) from sp_pj where sp_pj.sj_no=sj_info.sj_no)";
			pst = conn.prepareStatement(sql);
	    	pst.execute();
	    	pst.close();
			sql = "select sj_name,sj_star,sj_avgxf,sj_sumxl from sj_info where sj_no=? order by sj_star DESC,sj_avgxf";
			pst = conn.prepareStatement(sql);
			pst.setString(1, sjno);
			rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanStore p=new BeanStore();
		        p.setsjname(rs.getString(1));
		        p.setsjstar(rs.getInt(2));
		        p.setsjavgxf(rs.getFloat(3));
		        p.setsjsumxl(rs.getFloat(4));
		        p.setsjno(sjno);
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
	public List<BeanStore> selectstorebyname(String name)throws BaseException{
		if(name.isEmpty())
			throw new BusinessException("商家名为空");
		List<BeanStore> result=new ArrayList<BeanStore>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select sj_no from sj_info where sj_name like ?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,"%"+name+"%");
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("商家不存在");
			rs.close();
			pst.close();
			sql = "update sj_info set sj_avgxf=(select avg(dd_endmoney) from sp_dd where sj_info.sj_no=sp_dd.sj_no) ,sj_sumxl=(select count(dd_no) from sp_dd where sj_info.sj_no=sp_dd.sj_no) ,sj_star=(select avg(pj_star) from sp_pj where sp_pj.sj_no=sj_info.sj_no)";
			pst = conn.prepareStatement(sql);
	    	pst.execute();
	    	pst.close();
			sql = "select sj_name,sj_star,sj_avgxf,sj_sumxl,sj_no from sj_info where sj_name like ? order by sj_star DESC,sj_avgxf";
			pst = conn.prepareStatement(sql);
			pst.setString(1, "%"+name+"%");
			rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanStore p=new BeanStore();
		        p.setsjname(rs.getString(1));
		        p.setsjstar(rs.getInt(2));
		        p.setsjavgxf(rs.getFloat(3));
		        p.setsjsumxl(rs.getFloat(4));
		        p.setsjno(rs.getString(5));
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
	public void deleteStore(BeanStore store) throws BaseException {
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="delete from mj_method\n" + 
					"where sj_no=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,store.getsjno());
			pst.execute();
			pst.close();
			sql="delete from dd_info\n" + 
					"where sp_no in(\n" + 
					"select sp_no\n" + 
					"from sp_info\n" + 
					"where sj_no=?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1,store.getsjno());
			pst.execute();
			pst.close();
			sql="delete from sp_pj\n" + 
					"where sj_no=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,store.getsjno());
			pst.execute();
			pst.close();
			sql="delete from sp_info\n" + 
					"where sj_no=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,store.getsjno());
			pst.execute();
			pst.close();
			sql="delete from own\n" + 
					"where yh_no in(\n" + 
					"select yh_no\n" + 
					"from yh_info\n" + 
					"where sj_no=?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1,store.getsjno());
			pst.execute();
			pst.close();
			sql="delete from yh_use\n" + 
					"where yh_no in(\n" + 
					"select yh_no\n" + 
					"from yh_info\n" + 
					"where sj_no=?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1,store.getsjno());
			pst.execute();
			pst.close();
			sql="delete from yh_info\n" + 
					"where sj_no=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,store.getsjno());
			pst.execute();
			pst.close();
			sql="delete from sj_info\n" + 
					"where sj_no=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,store.getsjno());
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
	
	public void updateinfo(String name) throws BaseException {
		// TODO Auto-generated method stub
		if(name.isEmpty())
			throw new BaseException("商家名为空");
		Connection conn = null;
	    String sjno = BeanStore.currentLoginstore.getsjno();
	    try {
	    	conn = DBUtil.getConnection();
	    	String sql = "update sj_info set sj_name=? where sj_no=?";
	    	java.sql.PreparedStatement pst = conn.prepareStatement(sql);
	    	pst.setString(1, name);
	    	pst.setString(2, sjno);
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
	public List<BeanStore> loadbystore()throws BaseException{
		List<BeanStore> result=new ArrayList<BeanStore>();
		String sjno=BeanStore.currentLoginstore.getsjno();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select sj_name,sj_star,sj_avgxf,sj_sumxl,sj_no from sj_info where sj_no=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, sjno);
			java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanStore p=new BeanStore();
		        p.setsjname(rs.getString(1));
		        p.setsjstar(rs.getInt(2));
		        p.setsjavgxf(rs.getFloat(3));
		        p.setsjsumxl(rs.getFloat(4));
		        p.setsjno(rs.getString(5));
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
