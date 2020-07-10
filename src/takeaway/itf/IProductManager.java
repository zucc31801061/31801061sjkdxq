package takeaway.itf;

import java.util.List;

import takeaway.model.BeanStore;
import takeaway.model.BeanProduct;
import takeaway.util.BaseException;

public interface IProductManager {
	public List<BeanProduct> loadProducts(BeanStore store)throws BaseException;
	public List<BeanProduct> loadAll()throws BaseException;
	/**
	 * ��Ӳ���
	 * ����Ĳ������Ϊ��ǰ�ƻ���������+1
	 * ע�⣺������ַ�����ʱ�����͵�ת������Ӻ�������ƻ�������Ӧ������ֵ
	 * @param plan
	 * @param name
	 * @param planstartdate
	 * @param planfinishdate
	 * @throws BaseException
	 */
	public BeanProduct addproduct(String name,String kind,Float start,Float end)throws BaseException;

	
	
	/**
	 * ɾ�����裬
	 * ע��ɾ����������ƻ����ж�Ӧ�Ĳ�������
	 * @param step
	 * @throws BaseException
	 */
	public void deleteProduct(BeanProduct product)throws BaseException;

	/**
	 * ���õ�ǰ�����ʵ�ʿ�ʼʱ�䣬����Ӧ�ļƻ������ѿ�ʼ��������
	 * 
	 * @param step
	 * @throws BaseException
	 */
	public void startStep(BeanProduct step)throws BaseException;
	/**
	 * ���õ�ǰ�����ʵ�����ʱ�䣬����Ӧ�ļƻ���������ɲ�������
	 * @param step
	 * @throws BaseException
	 */
	public void finishStep(BeanProduct step)throws BaseException;
	/**
	 * ������ǰ�����˳���
	 * ע�⣺���ݿ���У�plan_id,step_order�Ͻ�����Ψһ������������ǰ��������ֵ����һ��������ֵʱ���ܳ������ֵһ�������
	 * @param step
	 * @throws BaseException
	 */
	public void moveUp(BeanProduct step)throws BaseException;
	public void moveDown(BeanProduct step)throws BaseException;
	

}
