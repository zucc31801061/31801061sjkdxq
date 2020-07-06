package takeaway.itf;

import java.util.List;

import takeaway.model.BeanStore;
import takeaway.util.BaseException;

public interface IStoreManager {
	/**
	 * ��Ӽƻ�
	 * Ҫ�������ļƻ��������Ϊ��ǰ�û�������������+1
	 * ע�⣺��ǰ��½�û���ͨ�� BeanUser.currentLoginUser��ȡ
	 * @param name  �ƻ�����
	 * @throws BaseException
	 */
	public BeanStore addPlan(String name) throws BaseException;
	/**
	 * ��ȡ��ǰ�û����мƻ�
	 * @return
	 * @throws BaseException
	 */
	public List<BeanStore> loadAll()throws BaseException;
	/**
	 * ɾ���ƻ�������ƻ��´��ڲ��裬������ɾ��
	 * @param plan
	 * @throws BaseException
	 */
	public void deletePlan(BeanStore plan)throws BaseException;

}
