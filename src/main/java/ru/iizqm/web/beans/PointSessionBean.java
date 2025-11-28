package ru.iizqm.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ru.iizqm.web.models.Point;

@Named("pointSessionBean")
@SessionScoped
@Transactional
public class PointSessionBean implements Serializable {

    @PersistenceContext(unitName = "myPU")
    private EntityManager em;

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

        em.persist(point);
        points.add(point);
    }

    public void loadPointsFromDB() {
        List<Point> result = em.createQuery("SELECT p FROM Point p ORDER BY p.id", Point.class)
                               .getResultList();
        this.points = new ArrayList<>(result);
    }

    public void clearAll() {
        em.createQuery("DELETE FROM Point").executeUpdate();
        points.clear();
    }
}
