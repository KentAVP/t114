package t113;


import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserHibernateDAO implements UserDAO {

    public UserHibernateDAO() {
    }

    @Override
    public List<User> getAll() {
        Transaction transaction = null;
        List <User> listOfUser = null;
        try {
            // start a transaction
            Session session = DBHelper.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            // get an user object

            listOfUser = session.createQuery("from User").list(); // забераем из наименование класса

            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return listOfUser;
    }

    @Override
    public void delete(User user) {
        Transaction transaction = null;

        try{
            Session session = DBHelper.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            if(user!=null){
                session.delete(user);
                System.out.println("Вы удалили пользователя!");
            }
            transaction.commit();

        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void add(User user) {
        Transaction transaction = null;
        try{
            Session session = DBHelper.getSessionFactory().openSession();
            transaction=session.beginTransaction();
            session.save(user);
            transaction.commit();

        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        Transaction transaction = null;
        try{
            Session session = DBHelper.getSessionFactory().openSession();
            transaction=session.beginTransaction();
            session.merge(user);
            transaction.commit();

        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public User getbyID(int id) {
        Transaction transaction = null;
        User user = null;
        try{
            Session session = DBHelper.getSessionFactory().openSession();
            transaction=session.beginTransaction();
            user = (User)session.get(User.class,id);
            transaction.commit();
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return user;
    }
}
