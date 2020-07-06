package takeaway.control;

import java.sql.Connection;
import java.sql.ResultSet;
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
	public BeanStore addPlan(String name) throws BaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BeanStore> loadAll() throws BaseException {
		List<BeanStore> result=new ArrayList<BeanStore>();
		BeanStore p=new BeanStore();
		result.add(p);
		return result;
	}

	@Override
	public void deletePlan(BeanStore plan) throws BaseException {
		
	}
}
