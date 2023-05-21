package com.javarush.uzienko.todolist.dao;

import com.javarush.uzienko.todolist.domain.Task;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class TaskDAO {

    private final SessionFactory sessionFactory;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Task> getAll(int offset, int limit) {
        String hql = "SELECT t FROM Task t";
        Query<Task> query = getSession().createQuery(hql, Task.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public int getAllCount() {
        String hql = "SELECT count(t) FROM Task t";
        Query<Long> query = getSession().createQuery(hql, Long.class);
        return Math.toIntExact(query.uniqueResult());
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Optional<Task> getById(int id) {
        String hql = "SELECT t FROM Task t WHERE t.id = :id";
        Query<Task> query = getSession().createQuery(hql, Task.class);
        query.setParameter("id", id);
        return query.uniqueResultOptional();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdate(Task task) {
        getSession().persist(task);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Task task) {
        getSession().remove(task);
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
