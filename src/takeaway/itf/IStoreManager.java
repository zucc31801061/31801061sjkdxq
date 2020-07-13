package takeaway.itf;

import java.util.List;

import takeaway.model.BeanStore;
import takeaway.model.BeanUser;
import takeaway.util.BaseException;

public interface IStoreManager {
	/**
	 * ����̼�
	 * ע�⣺��ǰ��½�û���ͨ�� BeanUser.currentLoginUser��ȡ
	 * @param name  �ƻ�����
	 * @throws BaseException
	 */
	public BeanStore addStore(String name) throws BaseException;
	/**
	 *�̼ҵ�¼
	 */
	public BeanStore login() throws BaseException;
	/**
	 * ��ȡ�����̼�
	 * @return
	 * @throws BaseException
	 */
	public List<BeanStore> loadAll()throws BaseException;
	public List<BeanStore> loadbystore()throws BaseException;
	public List<BeanStore> selectstore(String sjno)throws BaseException;
	public List<BeanStore> selectstorebyname(String name)throws BaseException;
	/**
	 * ɾ���̼�
	 * @param store
	 * @throws BaseException
	 */
	public void deleteStore(BeanStore store)throws BaseException;
	/**
	 * �޸��̼���
	 * @param store
	 * @throws BaseException
	 */
	public void updateinfo(String name) throws BaseException;
}
