package DAO;

import Entity.UsersEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pack.HibernateSessionFactory;
import javax.persistence.NoResultException;
import java.util.List;

public class UsersDAO {

    public UsersEntity findById(int id){
        return HibernateSessionFactory.getSessionFactory().openSession().get(UsersEntity.class,id);
    }

    public UsersEntity findByLoginAndPassword(String login, String password){

        UsersEntity usersEntity;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            usersEntity = (UsersEntity)
                    session.createQuery(
                            "from UsersEntity user where user.login = '"
                                    + login + "' and user.password = '"
                                    + password + "'").getSingleResult();
        }catch (NoResultException e){usersEntity = null;}
        session.close();
        return usersEntity;
    }

    public void save(UsersEntity usersEntity){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(usersEntity);
        transaction.commit();
        session.close();
    }

    public void update(UsersEntity usersEntity){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(usersEntity);
        transaction.commit();
        session.close();
    }

    public void delete(UsersEntity usersEntity){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(usersEntity);
        transaction.commit();
        session.close();
    }

    public List<UsersEntity> findAll(){
        List<UsersEntity> usersEntities = HibernateSessionFactory.getSessionFactory().openSession().createQuery("FROM UsersEntity").list();
        return usersEntities;
    }




}
