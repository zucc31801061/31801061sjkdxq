package takeaway.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import takeaway.itf.IProductManager;
import takeaway.model.BeanStore;
import takeaway.model.BeanKind;
import takeaway.model.BeanProduct;
import takeaway.model.BeanUser;
import takeaway.util.BaseException;
import takeaway.util.BusinessException;
import takeaway.util.DBUtil;
import takeaway.util.DbException;

public class ProductManager implements IProductManager {
	@Override
	public BeanProduct addproduct(String name,String kind,Float start,Float end)throws BaseException {
		if(name.isEmpty()||kind.isEmpty())
			throw new BusinessException("请输入完整的商品信息");
		String sjno = BeanStore.currentLoginstore.getsjno();
		BeanProduct product=new BeanProduct();
		int spno=1;
		int flno;
		int num;
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
		    String sql = "select sp_no from sp_info order by sp_no desc limit 0,1";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    java.sql.ResultSet rs = pst.executeQuery();
		    if (rs.next()) {
		    	spno=rs.getInt(1) + 1;
		    } 
		    else {
		    	spno=1;
		    }
		    rs.close();
			pst.close();
			sql = "select fl_no,num from sp_kind where fl_name=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, kind);
			rs = pst.executeQuery();
			if(!rs.next()) {
				throw new BusinessException("该分类不存在");
			}
			else{
				flno=rs.getInt(1);
				num=rs.getInt(2);
			}
			rs.close();
			pst.close();
		    sql = "insert into sp_info(sp_no,fl_no,sj_no,sp_name,sp_money,sp_yh) values(?,?,?,?,?,?)";
		    pst = conn.prepareStatement(sql);
		    pst.setInt(1, spno);
		    pst.setInt(2, flno);
		    pst.setString(3, sjno);
		    pst.setString(4, name);
		    pst.setFloat(5, start);
		    pst.setFloat(6, end);
		    pst.execute();
		    pst.close();
		    sql ="update sp_kind set num=? where fl_no=?";
		    pst = conn.prepareStatement(sql);
		    pst.setInt(1, num+1);
		    pst.setInt(2, flno);
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
		return product;
	}
	@Override
	public List<BeanProduct> loadAll()throws BaseException {
		List<BeanProduct> result = new ArrayList<BeanProduct>();
		String store = BeanStore.currentLoginstore.getsjno();
		Connection conn = null;
		try {
	    	conn = DBUtil.getConnection();
	    	String sql = "select sp_name,fl_name,sp_money,sp_yh,sp_info.sp_no\n" + 
	    			"from sp_info,sp_kind\n" + 
	    			"where sj_no=?\n" + 
	    			"and sp_info.fl_no=sp_kind.fl_no\n" + 
	    			"group by sp_info.sp_no";
	    	PreparedStatement pst = conn.prepareStatement(sql);
	    	pst.setString(1, store);
	    	ResultSet rs = pst.executeQuery();
	    	while (rs.next()) {
	    		BeanProduct s = new BeanProduct();
	    		s.setspname(rs.getString(1));
	    		s.setflname(rs.getString(2));
	    		s.setspmoney(rs.getFloat(3));
	    		s.setyhmoney(rs.getFloat(4));
	    		s.setspno(rs.getInt(5));
	    		result.add(s);
	    	}
	    	rs.close();
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
		return result;
	}
	public List<BeanProduct> loadAllbyuser()throws BaseException {
		List<BeanProduct> result = new ArrayList<BeanProduct>();
		Connection conn = null;
		try {
	    	conn = DBUtil.getConnection();
	    	String sql = "select sp_name,fl_name,sp_money,sp_yh,sp_no,sj_name\n" + 
	    			"from sp_info,sp_kind,sj_info\n" + 
	    			"where sj_info.sj_no=sp_info.sj_no\n" + 
	    			"and sp_info.fl_no=sp_kind.fl_no\n" + 
	    			"order by sp_money";
	    	PreparedStatement pst = conn.prepareStatement(sql);
	    	ResultSet rs = pst.executeQuery();
	    	while (rs.next()) {
	    		BeanProduct s = new BeanProduct();
	    		s.setspname(rs.getString(1));
	    		s.setflname(rs.getString(2));
	    		s.setspmoney(rs.getFloat(3));
	    		s.setyhmoney(rs.getFloat(4));
	    		s.setspno(rs.getInt(5));
	    		s.setsjname(rs.getString(6));
	    		result.add(s);
	    	}
	    	rs.close();
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
		return result;
	}
	public List<BeanProduct> selectproduct(String name)throws BaseException{
		if(name.isEmpty())
			throw new BusinessException("商品名为空");
		List<BeanProduct> result = new ArrayList<BeanProduct>();
		Connection conn = null;
		try {
	    	conn = DBUtil.getConnection();
	    	String sql = "select sp_name,fl_name,sp_money,sp_yh,sp_no,sj_name\n" + 
	    			"from sp_info,sp_kind,sj_info\n" + 
	    			"where sj_info.sj_no=sp_info.sj_no\n" + 
	    			"and sp_info.fl_no=sp_kind.fl_no\n" + 
	    			"and sp_name like ?\n" + 
	    			"order by sp_money";
	    	PreparedStatement pst = conn.prepareStatement(sql);
	    	pst.setString(1, "%"+name+"%");
	    	ResultSet rs = pst.executeQuery();
	    	while (rs.next()) {
	    		BeanProduct s = new BeanProduct();
	    		s.setspname(rs.getString(1));
	    		s.setflname(rs.getString(2));
	    		s.setspmoney(rs.getFloat(3));
	    		s.setyhmoney(rs.getFloat(4));
	    		s.setspno(rs.getInt(5));
	    		s.setsjname(rs.getString(6));
	    		result.add(s);
	    	}
	    	rs.close();
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
		return result;
	}
	@Override
	public List<BeanProduct> loadProducts(BeanStore store) throws BaseException {
		List<BeanProduct> result = new ArrayList<BeanProduct>();
		Connection conn = null;
	    try {
	    	conn = DBUtil.getConnection();
	    	String sql = "select sp_name,fl_name,sp_money,sp_yh,sp_info.sp_no from sp_info,sp_kind where sj_no=? and sp_info.fl_no=sp_kind.fl_no";
	    	PreparedStatement pst = conn.prepareStatement(sql);
	    	pst.setString(1, store.getsjno());
	    	ResultSet rs = pst.executeQuery();
	    	while (rs.next()) {
	    		BeanProduct s = new BeanProduct();
	    		s.setspname(rs.getString(1));
	    		s.setflname(rs.getString(2));
	    		s.setspmoney(rs.getFloat(3));
	    		s.setyhmoney(rs.getFloat(4));
	    		s.setsjno(store.getsjno());
	    		s.setspno(rs.getInt(5));
	    		result.add(s);
	    	}
	    	rs.close();
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
	    return result;
	}
	public List<BeanProduct> loadProductsbyname(BeanStore store,String name)throws BaseException{
		if(name.isEmpty())
			throw new BusinessException("商品名为空");
		List<BeanProduct> result = new ArrayList<BeanProduct>();
		Connection conn = null;
	    try {
	    	conn = DBUtil.getConnection();
	    	String sql = "select sp_name,fl_name,sp_money,sp_yh,sp_info.sp_no\n" + 
	    			"from sp_info,sp_kind\n" + 
	    			"where sj_no=?\n" + 
	    			"and sp_name like ?\n" + 
	    			"and sp_info.fl_no=sp_kind.fl_no";
	    	PreparedStatement pst = conn.prepareStatement(sql);
	    	pst.setString(1, store.getsjno());
	    	pst.setString(2, "%"+name+"%");
	    	ResultSet rs = pst.executeQuery();
	    	while (rs.next()) {
	    		BeanProduct s = new BeanProduct();
	    		s.setspname(rs.getString(1));
	    		s.setflname(rs.getString(2));
	    		s.setspmoney(rs.getFloat(3));
	    		s.setyhmoney(rs.getFloat(4));
	    		s.setsjno(store.getsjno());
	    		s.setspno(rs.getInt(5));
	    		result.add(s);
	    	}
	    	rs.close();
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
	    return result;
	}
	@Override
	public void deleteProduct(BeanProduct product) throws BaseException {
		// TODO Auto-generated method stub
		String flname = product.getflname();
		int num=0;
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
		    String sql = "delete from sp_info where sj_no=? and sp_no=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, BeanStore.currentLoginstore.getsjno());
		    pst.setInt(2, product.getspno());
		    pst.executeUpdate();
		    pst.close();
		    sql = "select num from sp_kind where fl_name=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, flname);
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				num=rs.getInt(1);
			}
			rs.close();
			pst.close();
			sql ="update sp_kind set num=? where fl_name=?";
		    pst = conn.prepareStatement(sql);
		    pst.setInt(1, num-1);
		    pst.setString(2, flname);
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
	public void updateProduct(BeanProduct product,String name,String kind,Float start,Float end)throws BaseException{
		if(name.isEmpty()||kind.isEmpty())
			throw new BusinessException("请输入完整的商品信息");
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select fl_no,num from sp_kind where fl_name=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, kind);
			ResultSet rs = pst.executeQuery();
			if(!rs.next()) {
				throw new BusinessException("该分类不存在");
			}
			rs.close();
			pst.close();
			sql = "update sp_info\n" + 
					"set sp_name=?,fl_no=(select fl_no from sp_kind where fl_name=?),sp_money=?,sp_yh=?\n" + 
					"where sp_no=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, name);
			pst.setString(2, kind);
			pst.setFloat(3, start);
			pst.setFloat(4, end);
			pst.setInt(5, product.getspno());
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
	public List<BeanProduct> loadProductbykind(BeanKind kind)throws BaseException{
		List<BeanProduct> result = new ArrayList<BeanProduct>();
		Connection conn = null;
	    try {
	    	conn = DBUtil.getConnection();
	    	String sql = "select sp_name,fl_name,sp_money,sp_yh,sj_name\n" + 
	    			"from sp_info,sp_kind,sj_info\n" + 
	    			"where sp_kind.fl_no=?\n" + 
	    			"and sp_info.fl_no=sp_kind.fl_no\n" + 
	    			"and sp_info.sj_no=sj_info.sj_no";
	    	PreparedStatement pst = conn.prepareStatement(sql);
	    	pst.setInt(1, kind.getflno());
	    	ResultSet rs = pst.executeQuery();
	    	while (rs.next()) {
	    		BeanProduct s = new BeanProduct();
	    		s.setspname(rs.getString(1));
	    		s.setflname(rs.getString(2));
	    		s.setspmoney(rs.getFloat(3));
	    		s.setyhmoney(rs.getFloat(4));
	    		s.setsjname(rs.getString(5));
	    		result.add(s);
	    	}
	    	rs.close();
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
	    return result;
	}
	@Override
	public void startStep(BeanProduct product) throws BaseException {
		// TODO Auto-generated method stub
		/*Connection conn=null;
		try {
			conn = DBUtil.getConnection();
		    String sql = "delete from sp_info where sj_no=? and sp_no=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, BeanStore.currentLoginstore.getsjno());
		    pst.setInt(2, product.getspno());
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

	@Override
	public void finishStep(BeanProduct step) throws BaseException {
		// TODO Auto-generated method stub
		/*Connection conn = null;
	    try {
	    	conn = DBUtil.getConnection();
	    	String sql = "UPDATE tbl_step SET real_end_time=? WHERE step_id=?";
	    	java.sql.PreparedStatement pst = conn.prepareStatement(sql);
	    	pst.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
	    	pst.setInt(2, step.getStepid());
	    	if (pst.executeUpdate() == 1) {
	    		System.out.println("已设定" + step.getStepid() + "为结束步骤");
	    	} else {
	    		throw new RuntimeException("查询失败");
	    	}
	    	int finishedstepcount;
	    	sql = "SELECT finished_step_count FROM tbl_plan WHERE plan_id=?";
	    	pst = conn.prepareStatement(sql);
	    	pst.setInt(1, step.getPlanid());
	    	ResultSet rs = pst.executeQuery();
	    	rs.next();
	    	finishedstepcount = rs.getInt(1) + 1;
	    	sql = "UPDATE tbl_plan SET finished_step_count=? WHERE plan_id=?";
	    	pst = conn.prepareStatement(sql);
	    	pst.setInt(1, finishedstepcount);
	    	pst.setInt(2, step.getPlanid());
	    	if (pst.executeUpdate() == 1) {
	    		System.out.println("计划" + step.getPlanid() + "结束步骤设置成功");
	    	} else {
	    		throw new RuntimeException("设置失败");
	    	}
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
	    }*/
	}

	@Override
	public void moveUp(BeanProduct step) throws BaseException {
		// TODO Auto-generated method stub
		/*int oldorder = step.getSteporder();
	    if (oldorder < 2) {
	    	throw new BusinessException("步骤过少无法移动");
	    }
	    int targetorder = oldorder - 1;
	    Connection conn = null;
	    try {
	    	conn = DBUtil.getConnection();
	    	String sql;
	    	java.sql.PreparedStatement pst;
	    	sql = "UPDATE tbl_step SET step_order=? WHERE plan_id=? AND step_order=?";
	    	pst = conn.prepareStatement(sql);
	    	pst.setInt(1, 0);
	    	pst.setInt(2, step.getPlanid());
	    	pst.setInt(3, targetorder);
	    	pst.executeUpdate();
	    	sql = "UPDATE tbl_step SET step_order=? WHERE plan_id=? AND step_order=?";
	    	pst = conn.prepareStatement(sql);
	    	pst.setInt(1, targetorder);
	    	pst.setInt(2, step.getPlanid());
	    	pst.setInt(3, oldorder);
	    	pst.executeUpdate();
	    	sql = "UPDATE tbl_step SET step_order=? WHERE plan_id=? AND step_order=?";
	    	pst = conn.prepareStatement(sql);
	    	pst.setInt(1, oldorder);
	    	pst.setInt(2, step.getPlanid());
	    	pst.setInt(3, 0);
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
	    }*/
	}

	@Override
	public void moveDown(BeanProduct step) throws BaseException {
		/*// TODO Auto-generated method stub
		int oldorder = step.getSteporder();
	    int targetorder = oldorder + 1;
	    Connection conn = null;
	    try {
	    	conn = DBUtil.getConnection();
	    	String sql;
	    	java.sql.PreparedStatement pst;
	    	int orderMax;
	    	sql = "SELECT step_count From tbl_plan WHERE plan_id=?";
	    	pst = conn.prepareStatement(sql);
	    	pst.setInt(1, step.getPlanid());
	    	ResultSet rs = pst.executeQuery();
	    	if (rs.next()) {
	    		orderMax = rs.getInt(1);
	    	} else {
	    		throw new RuntimeException("查询失败");
	    	}
	    	rs.close();
	    	if (targetorder > orderMax) {
	    		throw new BusinessException("目标位置超过限制");
	    	}
	    	sql = "UPDATE tbl_step SET step_order=? WHERE plan_id=? AND step_order=?";
	    	pst = conn.prepareStatement(sql);
	    	pst.setInt(1, 0);
	    	pst.setInt(2, step.getPlanid());
	    	pst.setInt(3, targetorder);
	    	pst.executeUpdate();
	    	sql = "UPDATE tbl_step SET step_order=? WHERE plan_id=? AND step_order=?";
	    	pst = conn.prepareStatement(sql);
	    	pst.setInt(1, targetorder);
	    	pst.setInt(2, step.getPlanid());
	    	pst.setInt(3, oldorder);
	    	pst.executeUpdate();
	    	sql = "UPDATE tbl_step SET step_order=? WHERE plan_id=? AND step_order=?";
	    	pst = conn.prepareStatement(sql);
	    	pst.setInt(1, oldorder);
	    	pst.setInt(2, step.getPlanid());
	    	pst.setInt(3, 0);
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
	    }*/
	}

}
