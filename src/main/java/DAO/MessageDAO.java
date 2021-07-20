package DAO;
import Entity.MessageEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pack.HibernateSessionFactory;

import java.util.List;

public class MessageDAO {
    
    public MessageEntity findById(int id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(MessageEntity.class, id);
    }

    public void save(MessageEntity messageEntity) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(messageEntity);
        transaction.commit();
        session.close();
    }

    public void update(MessageEntity messageEntity) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(messageEntity);
        transaction.commit();
        session.close();
    }

    public void delete(MessageEntity messageEntity) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(messageEntity);
        transaction.commit();
        session.close();
    }

    public List<MessageEntity> findAll() {
        List<MessageEntity> messageEntities = HibernateSessionFactory.getSessionFactory()
                .openSession().createQuery("FROM MessageEntity ").list();
        return messageEntities;
    }

    public List<MessageEntity> findAllFromRoomId(int id){
        List<MessageEntity> messageEntities = HibernateSessionFactory.getSessionFactory()
                .openSession().createQuery("from MessageEntity msg where msg.toRoomId = '"+id+"'").list();
        return messageEntities;
    }
}
