package hiber.dao;

import hiber.config.AppConfig;
import hiber.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public List<User> getUsersByModelAndSeries(String model, int series) {


      try {
         String HQL = "FROM User user WHERE user.car.model =:model and user.car.series =:series";

         TypedQuery<User> typedQuery = sessionFactory.getCurrentSession()
                 .createQuery(HQL, User.class).setParameter("model", model).setParameter("series", series);
         return typedQuery.getResultList();
      } catch (HibernateException e) {
         e.printStackTrace();
      }

       return null;
   }

}
