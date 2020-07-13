package takeaway.itf;

import java.util.List;

import takeaway.model.BeanStore;
import takeaway.model.BeanKind;
import takeaway.model.BeanProduct;
import takeaway.util.BaseException;

public interface IProductManager {
	public List<BeanProduct> loadProducts(BeanStore store)throws BaseException;
	public List<BeanProduct> loadProductsbyname(BeanStore store,String name)throws BaseException;
	public List<BeanProduct> loadProductbykind(BeanKind kind)throws BaseException;
	public List<BeanProduct> loadAll()throws BaseException;
	public List<BeanProduct> loadAllbyuser()throws BaseException;
	public List<BeanProduct> selectproduct(String name)throws BaseException;
	/**
	 * 添加商品
	 * 新填的商品序号为当前计划最大商品序号+1
	 * 注意：需完成字符串和时间类型的转换，添加后需调整计划表中相应的数量值
	 * @param plan
	 * @param name
	 * @param planstartdate
	 * @param planfinishdate
	 * @throws BaseException
	 */
	public BeanProduct addproduct(String name,String kind,Float start,Float end)throws BaseException;
	/**
	 * 删除商品，
	 * 注意删除后需调整计划表中对应的商品数量
	 * @param step
	 * @throws BaseException
	 */
	public void deleteProduct(BeanProduct product)throws BaseException;
	public void updateProduct(BeanProduct product,String name,String kind,Float start,Float end)throws BaseException;
	/**
	 * 设置当前商品的实际开始时间，及对应的计划表中已开始商品数量
	 * 
	 * @param step
	 * @throws BaseException
	 */
	public void startStep(BeanProduct step)throws BaseException;
	/**
	 * 设置当前商品的实际完成时间，及对应的计划表中已完成商品数量
	 * @param step
	 * @throws BaseException
	 */
	public void finishStep(BeanProduct step)throws BaseException;
	/**
	 * 调整当前商品的顺序号
	 * 注意：数据库表中，plan_id,step_order上建立了唯一索引，调整当前商品的序号值和上一商品的序号值时不能出现序号值一样的情况
	 * @param step
	 * @throws BaseException
	 */
	public void moveUp(BeanProduct step)throws BaseException;
	public void moveDown(BeanProduct step)throws BaseException;
	

}
