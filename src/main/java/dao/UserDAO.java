package dao;

import models.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class UserDAO {
    protected SessionFactory sessionFactory;

    public void setup() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception ex) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public void exit() {
        sessionFactory.close();
    }

    public void create() {
        UserEntity user = new UserEntity();
        user.setUserName("admin");
        user.setPassword("1qaz2wsx");
        user.setLastName("Tennakoon");
        user.setFirstName("Mahendra");
        user.setAccType("admin");
        user.setAccessPrivileges("101010");
        user.setEmail("mahendrathennakoon@gmail.com");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(user);

        session.getTransaction().commit();
        session.close();
    }

    public void read() {
        // code to get a book
    }

    public void update() {
        // code to modify a book
    }

    public void delete() {
        // code to remove a book
    }
}
