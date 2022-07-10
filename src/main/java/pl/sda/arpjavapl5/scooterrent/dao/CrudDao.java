package pl.sda.arpjavapl5.scooterrent.dao;

import pl.sda.arpjavapl5.scooterrent.exception.RollbackException;
import javax.persistence.*;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class CrudDao<T,K> {
    protected final Class<T> clazz;
    protected final EntityManagerFactory factory;
    public CrudDao(String unit, Class<T> clazz) {
        this.clazz = clazz;
        factory = Persistence.createEntityManagerFactory(unit);
    }

    public void transaction(Consumer<EntityManager> func){
        EntityManager em = factory.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            func.accept(em);        //em.persist(entity) dla insert
            transaction.commit();
        } catch (RollbackException e){
            transaction.rollback();
        } finally {
            em.close();
        }
    }

    public <R> Optional<R> resultTransaction(Function<EntityManager, R> func){
        EntityManager em = factory.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            final R apply = func.apply(em);
            transaction.commit();
            return Optional.ofNullable(apply);
        } catch (RollbackException e){
            transaction.rollback();
            return Optional.empty();
        } finally {
            em.close();
        }
    }


    public void save(T entity){
        transaction(em -> em.persist(entity));
    }

    public void delete(K id){
        transaction(em -> {
            final T entity = em.find(clazz, id);
            em.remove(entity);
        });
    }

    public Optional<T> findById(K id){
        final Optional<T> t = resultTransaction(em -> em.find(clazz, id));
        return t;
    }


}
