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
import takeaway.model.BeanProduct;
import takeaway.model.BeanUser;
import takeaway.util.BaseException;
import takeaway.util.BusinessException;
import takeaway.util.DBUtil;
import takeaway.util.DbException;

public class ProductManager implements IProductManager {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public void add(BeanStore plan, String name, String planstartdate,
			String planfinishdate) throws BaseException {
		/*if(name.isEmpty()) throw new BusinessException("�ƻ���Ϊ��");
		try {
			sdf.parse(planstartdate);
		    sdf.parse(planfinishdate);
		} catch (ParseException e) {
		    throw new BusinessException("�ƻ�ʱ�����");
		}
		int step_order;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
		    String sql = "select step_order from tbl_step where plan_id=? order by step_order desc limit 0,1";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setInt(1, plan.getplanid());
		    java.sql.ResultSet rs = pst.executeQuery();
		    if (rs.next()) {
		    	step_order = rs.getInt(1) + 1;
		    } else {
		    	step_order = 1;
		    }
		    sql = "insert into tbl_step(plan_id,step_order,step_name,plan_begin_time,plan_end_time) values(?,?,?,?,?)";
		    pst = conn.prepareStatement(sql);
		    pst.setInt(1, plan.getplanid());
		    pst.setInt(2, step_order);
		    pst.setString(3, name);
		    pst.setString(4, planstartdate);
		    pst.setString(5, planstartdate);
		    if (pst.executeUpdate() == 1) {
		    	System.out.println("������Ϊ: " + name);
		    } else {
		        throw new RuntimeException("��ѯʧ��");
		    }
		    sql = "update tbl_plan set step_count=? where plan_id=?";
		    pst = conn.prepareStatement(sql);
		    pst.setInt(1, step_order);
		    pst.setInt(2, plan.getplanid());
		    if (pst.executeUpdate() == 1) {
		    	System.out.println("�ƻ�" + plan.getplanname() + "�ɹ�������");
		    } else {
		        throw new RuntimeException("��������ʧ��");
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
		}*/
	}

	@Override
	public List<BeanProduct> loadProducts(BeanStore store) throws BaseException {
		List<BeanProduct> result = new ArrayList<BeanProduct>();
		Connection conn = null;
	    try {
	    	conn = DBUtil.getConnection();
	    	String sql = "select sp_name,fl_no,sp_money,sp_yh from sp_info where sj_no=?";
	    	PreparedStatement pst = conn.prepareStatement(sql);
	    	pst.setString(1, store.getsjno());
	    	ResultSet rs = pst.executeQuery();
	    	while (rs.next()) {
	    		BeanProduct s = new BeanProduct();
	    		s.setspname(rs.getString(1));
	    		s.setflno(rs.getInt(2));
	    		s.setspmoney(rs.getFloat(3));
	    		s.setyhmoney(rs.getFloat(4));
	    		s.setsjno(store.getsjno());
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
	public void deleteStep(BeanProduct step) throws BaseException {
		// TODO Auto-generated method stub
		/*Connection conn = null;
	    try {
	      conn = DBUtil.getConnection();
	      String sql = "DELETE from tbl_step WHERE step_id=?";
	      java.sql.PreparedStatement pst = conn.prepareStatement(sql);
	      pst.setInt(1, step.getStepid());
	      if (pst.executeUpdate() == 1) {
	        System.out.println("��ѯ����step_idΪ" + step.getStepid());
	      } else {
	        throw new RuntimeException("��ѯʧ��");
	      }
	      int stepordermax;
	      sql = "SELECT step_order FROM tbl_step WHERE plan_id=? ORDER BY step_order DESC LIMIT 0,1";
	      pst = conn.prepareStatement(sql);
	      pst.setInt(1, step.getPlanid());
	      ResultSet rs = pst.executeQuery();
	      if (rs.next()) {
	        stepordermax = rs.getInt(1);
	      } else {
	        stepordermax = 0;
	      }
	      int finishedstepcount = 0;
	      int startstepcount = 0;
	      sql = "SELECT start_step_count,finished_step_count FROM tbl_plan WHERE plan_id=?";
	      pst = conn.prepareStatement(sql);
	      pst.setInt(1, step.getPlanid());
	      if (rs.next()) {
	        startstepcount = rs.getInt(1);
	        finishedstepcount = rs.getInt(2);
	        if (step.getRealendtime() != null) {
	          finishedstepcount -= 1;
	        }
	        if (step.getRealbegintime() != null) {
	          startstepcount -= 1;
	        }
	      }
	      rs.close();
	      sql = "UPDATE tbl_plan SET step_count=?,start_step_count=?,finished_step_count=? WHERE plan_id=?";
	      pst = conn.prepareStatement(sql);
	      pst.setInt(1, stepordermax);
	      pst.setInt(2, startstepcount);
	      pst.setInt(3, finishedstepcount);
	      pst.setInt(4, step.getPlanid());
	      if (pst.executeUpdate() == 1) {
	        System.out.println("��ɾ���ƻ�" + step.getPlanid() + "�Ĳ���");
	      } else {
	        throw new RuntimeException("ɾ��ʧ��");
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
	public void startStep(BeanProduct step) throws BaseException {
		// TODO Auto-generated method stub
		/*Connection conn = null;
	    try {
	    	conn = DBUtil.getConnection();
	    	String sql = "UPDATE tbl_step SET real_begin_time=? WHERE step_id=?";
	    	java.sql.PreparedStatement pst = conn.prepareStatement(sql);
	    	pst.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
	    	pst.setInt(2, step.getStepid());
	    	if (pst.executeUpdate() == 1) {
	    		System.out.println("���趨" + step.getStepid() + "Ϊ��ʼ����");
	    	} else {
	    		throw new RuntimeException("��ѯʧ��");
	    	}
	    	int startstepcount;
	    	sql = "SELECT start_step_count FROM tbl_plan WHERE plan_id=?";
	    	pst = conn.prepareStatement(sql);
	    	pst.setInt(1, step.getPlanid());
	    	ResultSet rs = pst.executeQuery();
	    	rs.next();
	    	startstepcount = rs.getInt(1) + 1;
	    	sql = "UPDATE tbl_plan SET start_step_count=? WHERE plan_id=?";
	    	pst = conn.prepareStatement(sql);
	    	pst.setInt(1, startstepcount);
	    	pst.setInt(2, step.getPlanid());
	    	if (pst.executeUpdate() == 1) {
	    		System.out.println("�ƻ�" + step.getPlanid() + "��ʼ�������óɹ�");
	    	} else {
	    		throw new RuntimeException("����ʧ��");
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
	    		System.out.println("���趨" + step.getStepid() + "Ϊ��������");
	    	} else {
	    		throw new RuntimeException("��ѯʧ��");
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
	    		System.out.println("�ƻ�" + step.getPlanid() + "�����������óɹ�");
	    	} else {
	    		throw new RuntimeException("����ʧ��");
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
	    	throw new BusinessException("��������޷��ƶ�");
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
	    		throw new RuntimeException("��ѯʧ��");
	    	}
	    	rs.close();
	    	if (targetorder > orderMax) {
	    		throw new BusinessException("Ŀ��λ�ó�������");
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
