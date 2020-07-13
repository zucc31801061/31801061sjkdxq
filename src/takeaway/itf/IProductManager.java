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
	 * �����Ʒ
	 * �������Ʒ���Ϊ��ǰ�ƻ������Ʒ���+1
	 * ע�⣺������ַ�����ʱ�����͵�ת������Ӻ�������ƻ�������Ӧ������ֵ
	 * @param plan
	 * @param name
	 * @param planstartdate
	 * @param planfinishdate
	 * @throws BaseException
	 */
	public BeanProduct addproduct(String name,String kind,Float start,Float end)throws BaseException;
	/**
	 * ɾ����Ʒ��
	 * ע��ɾ����������ƻ����ж�Ӧ����Ʒ����
	 * @param step
	 * @throws BaseException
	 */
	public void deleteProduct(BeanProduct product)throws BaseException;
	public void updateProduct(BeanProduct product,String name,String kind,Float start,Float end)throws BaseException;
	/**
	 * ���õ�ǰ��Ʒ��ʵ�ʿ�ʼʱ�䣬����Ӧ�ļƻ������ѿ�ʼ��Ʒ����
	 * 
	 * @param step
	 * @throws BaseException
	 */
	public void startStep(BeanProduct step)throws BaseException;
	/**
	 * ���õ�ǰ��Ʒ��ʵ�����ʱ�䣬����Ӧ�ļƻ������������Ʒ����
	 * @param step
	 * @throws BaseException
	 */
	public void finishStep(BeanProduct step)throws BaseException;
	/**
	 * ������ǰ��Ʒ��˳���
	 * ע�⣺���ݿ���У�plan_id,step_order�Ͻ�����Ψһ������������ǰ��Ʒ�����ֵ����һ��Ʒ�����ֵʱ���ܳ������ֵһ�������
	 * @param step
	 * @throws BaseException
	 */
	public void moveUp(BeanProduct step)throws BaseException;
	public void moveDown(BeanProduct step)throws BaseException;
	

}
