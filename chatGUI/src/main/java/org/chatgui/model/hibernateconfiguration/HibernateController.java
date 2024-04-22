package org.chatgui.model.hibernateconfiguration;

import jakarta.persistence.Query;
import org.chatgui.model.hibernateconfiguration.hibernaterealization.IDataInteraction;
import org.chatgui.model.hibernateconfiguration.hibernaterealization.IGetData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class HibernateController implements IGetData, IDataInteraction {

    private SessionFactory factory;

    public HibernateController() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    @Override
    public void saveData(Object object) {
        Session session = factory.openSession();
        session.beginTransaction();

        session.save(object);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateData(Object object) {
        Session session = factory.openSession();
        session.beginTransaction();

        session.update(object);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteData(int index) {
        Session session = factory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("delete from UserEntity where id = :index");
        query.setParameter("index", index);
        query.executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Object getData(int index) {
        Session session = factory.openSession();
        session.beginTransaction();

        Object object = session.get(Object.class, index);
        session.getTransaction().commit();
        session.close();

        return object;
    }

    @Override
    public List<Object> getAllData() {
        Query query = factory.openSession().createQuery("from UserEntity");
        List<Object> objects = query.getResultList();
        return objects;
    }
}
