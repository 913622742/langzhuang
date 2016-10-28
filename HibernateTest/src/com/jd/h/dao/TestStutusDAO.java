package com.jd.h.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jd.h.bean.Master;
import com.jd.h.util.BaseHibernateDAO;

/**
 * ����Hibernate ����״̬
 * @author asus
 *
 */
public class TestStutusDAO extends BaseHibernateDAO {
	/**
	 * ������ʱ״̬���־�״̬��ת��
	 */
	public void testT2P()
	{
		Master master=new Master();
		//��ʱ̫
		master.setName("�Ǻ�");
		master.setSex(1);
		Session session=getSession();
		Transaction tran=session.beginTransaction();
		session.save(master);
		tran.commit();
		//�־�̬
		master.setSex(0);
		session.close();
		//����̬
		master.setName("a");
				
		//��ʱ̬
		master.setId(10);
	}
	/**
	 * ������ʱ״̬���־�״̬�󣬸��������ύ��
	 */
	public void testT2P2Update()
	{
		Master master=new Master();
		master.setName("��");
		master.setSex(0);
		Session session=getSession();
		Transaction tran=session.beginTransaction();
		session.save(master);
		master.setName("��");
		tran.commit();
		session.close();
		//�ع�
		//tran.rollback();
		
	}
	/**
	 * ���Գ־�״̬�޸ĺ��ύ
	 */
	public void testP2Update()
	{
		Session session = getSession();
		Master master=(Master)session.get(Master.class, 2);
		Transaction tran=session.getTransaction();
		//�˴�����������
		session.save(master);
		session.save(master);
		master.setSex(1);
		master.setName("һ����");
		tran.commit();
		session.close();
		
	}
	/**
	 * ���־�̬ת��Ϊ����̬������ύ
	 * clear �� evict �� session�ر�
	 */
	public void testP2D2Update()
	{
		Session session = getSession();
		Master master=(Master)session.get(Master.class, 2);
		//�־�ת��Ϊ����̬
		session.evict(master);
		Transaction tran=session.getTransaction();
		//����̬ ���������ã����ڷ�Χ�ڣ�
		master.setName("ssssssssssss");
		tran.commit();
		session.close();
	}
	/**
	 * ���־�̬ת��Ϊ��ʱ̬
	 */
	/**
	 * 
	 */
	public void testP2T()
	{
		Session session = getSession();
		//�־�̬
		Master master=(Master)session.get(Master.class, 2);
		Transaction tran=session.getTransaction();
		session.delete(master);
		master.setName("666666");
		tran.commit();
		session.close();
	}
	/**
	 * ͬ���־û�����
	 */
	public void testRefresh()
	{
		Session session=getSession();
		Master master=(Master)session.get(Master.class, 1);
		System.out.println("ǰ"+master.getName());
		Transaction tran=session.beginTransaction();
		tran.commit();
		session.refresh(master);
		System.out.println("��"+master.getName());
		session.close();
	}
	/**
	 * ������̬ת���ɳ־�̬
	 */
	public void testD2P()
	{
		Session session=getSession();
		
		Transaction trans=session.beginTransaction();
		//��ʱ̬
		Master master=new Master();
		//����̬
		master.setId(3);
		master.setName("55555");
		master.setSex(1);
		session.update(master);
		//�־�̬
		String name=master.getName();
		trans.commit();
		session.close();
		
	}
	/**
	 * �־�״̬�޸�id      ������
	 */
	public void testP2EditId()
	{
        Session session=getSession();
		Transaction trans=session.beginTransaction();
		Master master=(Master)session.get(Master.class, 2);
		master.setId(1000);
		trans.commit();
		session.close();
	}
	/**
	 * ����̬ת��Ϊ��ʱ״̬
	 */
	public void testD2S()
	{
		 Session session=getSession();
		Transaction trans=session.beginTransaction();
		//��ʱ̬
		Master master=new Master();
		//����̬
		master.setId(3);
		session.delete(master);
		
		trans.commit();
		session.close();
		
	}
	/**
	 * �����ظ��ĳ־û����� ������
	 */
	public void testDuplicateP()
	{
		 Session session=getSession();
	     Transaction trans=session.beginTransaction();
	     //�־�̬
	     Master master=(Master)session.get(Master.class, 2);
	     //��ʱ̬
	     Master master2=new Master();
	     //����̬
	     master2.setId(2);
	     master2.setName("����");
	     session.update(master2);
	     //�־�̬  
	     trans.commit();
	     session.close();
	}
	/**
	 * �����ظ��ĳ־û�����
	 */
	public void testRemoveDupli()
	{
		Session session=getSession();
	     Transaction trans=session.beginTransaction();
	     //�־�̬
	     Master master=(Master)session.get(Master.class, 2);
	     //��ʱ̬
	     Master master2=new Master();
	     //����̬
	     master2.setId(2);
	     master2.setName("����");
	     session.merge(master2);
	     //�־�̬  
	     System.out.println(master2);
	     trans.commit();
	     session.close();
	}
public static void main(String[] args) {
	
}

}
