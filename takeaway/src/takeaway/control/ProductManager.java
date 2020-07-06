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
	@Override
	public void add(BeanStore plan, String name, String planstartdate,
			String planfinishdate) throws BaseException {
		
		
	}

	@Override
	public List<BeanProduct> loadProducts(BeanStore plan) throws BaseException {
		List<BeanProduct> result=new ArrayList<BeanProduct>();
		BeanProduct p=new BeanProduct();
		result.add(p);
		return result;
	}

	@Override
	public void deleteProduct(BeanProduct step) throws BaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startProduct(BeanProduct step) throws BaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finishProduct(BeanProduct step) throws BaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveUp(BeanProduct step) throws BaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveDown(BeanProduct step) throws BaseException {
		// TODO Auto-generated method stub
		
	}

}
