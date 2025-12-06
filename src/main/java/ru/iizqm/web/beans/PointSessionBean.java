package ru.iizqm.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.transaction.Transactional;
import ru.iizqm.web.models.Point;

@Named("pointSessionBean")
@SessionScoped
@Transactional
public class PointSessionBean implements Serializable {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPU");
    private final EntityManager em = emf.createEntityManager();

    private List<Point> points = new ArrayList<>();

    public List<Point> getPoints() {
        if (points == null || points.isEmpty()) {
            loadPointsFromDB();
        }
        List<Point> reversed = new ArrayList<>(points);
        Collections.reverse(reversed);
        return reversed;
    }

    public void addPoint(Point point) {
        point.calc();

        if (point.getTimestamp() == null) {
            point.setTimestamp(new Date());
        }

        em.getTransaction().begin();
        em.persist(point);
        em.getTransaction().commit();
        points.add(point);
    }

    public void loadPointsFromDB() {
        List<Point> result = em.createQuery("SELECT p FROM Point p ORDER BY p.id", Point.class)
                               .getResultList();
        this.points = new ArrayList<>(result);
    }

    public void clearAll() {
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Point").executeUpdate();
        em.getTransaction().commit();
        points.clear();
    }
}
