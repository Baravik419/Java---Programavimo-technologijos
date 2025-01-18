package courseWork.hibernateControllers;

import javafx.scene.control.Alert;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;

import courseWork.Utils.FxUtils;
import courseWork.Model.User;

public class UserHibernate {


    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public UserHibernate(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public User getEntityByLogin(String login, String password) {
        entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            cq.select(root).where(cb.and(cb.like(root.get("login"), login), cb.like(root.get("password"), password)));
            Query q = entityManager.createQuery(cq);
            return (User) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            FxUtils.generateAlert(Alert.AlertType.ERROR, "LOGIN error", "Error during LOGIN operation");
            return null;
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }
}
