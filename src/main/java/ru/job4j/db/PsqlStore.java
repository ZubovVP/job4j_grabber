package ru.job4j.db;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.model.Post;

import java.util.List;
import java.util.function.Function;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $.
 * Date: 22.09.2021.
 */
public class PsqlStore implements Store, AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public void save(Post post) {
        this.tx(
                session -> session.save(post)
        );
    }

    @Override
    public List<Post> getAll() {
        return this.tx(
                session -> session.createQuery("from Post").list()
        );
    }

    @Override
    public Post findById(int id) {
        return this.tx(session -> {
                    final Query query = session.createQuery("from Post WHERE id =:id");
                    query.setParameter("id", id);
                    return (Post) query.list().get(0);
                }
        );
    }

    @Override
    public void delete(int id) {
        this.tx(
                session -> {
                    final Query query = session.createQuery("delete Post where id = :id");
                    query.setParameter("id", id);
                    return query.executeUpdate();
                }
        );
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(this.registry);
    }
}
