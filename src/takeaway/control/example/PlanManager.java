package takeaway.control.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeaway.itf.IPlanManager;
import takeaway.model.BeanPlan;
import takeaway.model.BeanUser;
import takeaway.util.BaseException;
import takeaway.util.BusinessException;
import takeaway.util.DBUtil;
import takeaway.util.DbException;

public class PlanManager implements IPlanManager {
	@Override
	public BeanPlan addPlan(String name) throws BaseException {
		// TODO Auto-generated method stub
		if(name.isEmpty()) throw new BusinessException("计划名为空");
		BeanPlan result=new BeanPlan();
		String userid = BeanUser.currentLoginUser.getUserid();
		int planorder;
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql = "select plan_order from tbl_plan where user_id=? order by plan_order desc limit 0,1";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, userid);
		    java.sql.ResultSet rs = pst.executeQuery();
		    if (rs.next()) {
		    	planorder = rs.getInt(1) + 1;
		    } 
		    else {
		        planorder = 1;
		    }
		    rs.close();
		    sql = "insert into tbl_plan(user_id,plan_order,plan_name,create_time,start_step_count,step_count,finished_step_count) values(?,?,?,?,0,0,0)";
		    pst = conn.prepareStatement(sql);
		    pst.setString(1, userid);
		    pst.setInt(2, planorder);
		    pst.setString(3, name);
		    pst.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
		    if (pst.executeUpdate() == 1) {
		    	System.out.println("创建成功");
		    	result = new BeanPlan();
		    	result.setplanname(name);
		        result.setplanorder(planorder);
		        result.setstepcount(0);
		        result.setfinishstep(0);
		    } 
		    else {
		   		throw new RuntimeException("创建失败");
		    }
		    pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.rollback();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}

	@Override
	public List<BeanPlan> loadAll() throws BaseException {
		List<BeanPlan> result=new ArrayList<BeanPlan>();
		BeanPlan p=new BeanPlan();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select plan_name,plan_order,step_count,finished_step_count,plan_id from tbl_plan where user_id=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, BeanUser.currentLoginUser.getUserid());
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		        p.setplanname(rs.getString(1));
		        p.setplanorder(rs.getInt(2));
		        p.setstepcount(rs.getInt(3));
		        p.setfinishstep(rs.getInt(4));
		        p.setplanid(rs.getInt(5));
		        p.setuserid(BeanUser.currentLoginUser.getUserid());
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
	public void deletePlan(BeanPlan plan) throws BaseException {
		Connection conn=null;
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
		}
	}

}
