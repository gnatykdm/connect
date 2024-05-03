package org.chatgui.model.hibernateconfiguration;

import org.hibernate.Session;

public class HibernateController implements IGetData, IDataInteraction {
    @Override
    public void saveData(Object object) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.save(object);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateData(Object object) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.update(object);
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public void deleteData(int index) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.delete(getData(index));
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Object getData(int index) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Object object = session.get(Object.class, index);
        session.getTransaction().commit();
        session.close();

        return object;
    }
}
