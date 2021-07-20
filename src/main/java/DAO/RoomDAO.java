package DAO;

import Entity.RoomEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pack.HibernateSessionFactory;

import javax.persistence.NoResultException;
import java.util.List;

public class RoomDAO {

    public RoomEntity findById(int id) {
        return HibernateSessionFactory
                .getSessionFactory().openSession()
                .get(RoomEntity.class, id);
    }

    public void save(RoomEntity roomEntity) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(roomEntity);
        transaction.commit();
        session.close();
    }

    public void update(RoomEntity roomEntity) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(roomEntity);
        transaction.commit();
        session.close();
    }

    public void delete(RoomEntity roomEntity) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(roomEntity);
        transaction.commit();
        session.close();
    }

    public List<RoomEntity> findAll() {
        List<RoomEntity> roomEntities = HibernateSessionFactory.getSessionFactory()
                .openSession().createQuery("FROM RoomEntity ").list();
        return roomEntities;
    }
    public RoomEntity findRoomByName(String name){
        RoomEntity roomEntity;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            roomEntity = (RoomEntity)
                    session.createQuery("from RoomEntity room where room.name= '"+name+"'")
                            .getSingleResult();
        }catch (NoResultException e){
            roomEntity = null;
        }
        session.close();
        return roomEntity;
    }
    public List<RoomEntity> findPublicRoom(){
        List<RoomEntity> roomEntities = HibernateSessionFactory.getSessionFactory()
                .openSession().createQuery("from RoomEntity room where isPublic=1").list();
        return roomEntities;
    }
}
