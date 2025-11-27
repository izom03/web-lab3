package ru.iizqm.web.beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import ru.iizqm.web.models.Point;

@Named("pointSessionBean")
@SessionScoped
public class PointSessionBean implements Serializable {
    private final String url = System.getenv("DB_URL");
    private final String user = System.getenv("DB_USER");
    private final String password = System.getenv("DB_PASS");

    private List<Point> points = new ArrayList<>();

    public List<Point> getPoints() {
        if (points.isEmpty()) {
            loadPointsFromDB();
        }
        List<Point> reversed = new ArrayList<>(points);
        Collections.reverse(reversed);
        return reversed;
    }

    public void addPoint(Point point) {
        point.calc();
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO point (x, y, r, inside_area, timestamp, execution_time) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setDouble(1, point.getX());
                ps.setDouble(2, point.getY());
                ps.setDouble(3, point.getR());
                ps.setBoolean(4, point.isInsideArea());
                ps.setTimestamp(5, new Timestamp(point.getTimestamp().getTime()));
                ps.setLong(6, point.getExecutionTime());
                ps.executeUpdate();
            }
            points.add(point);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadPointsFromDB() {
        points.clear();
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT x, y, r, inside_area, timestamp, execution_time FROM point ORDER BY id";
            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Point p = new Point(
                        rs.getBoolean("inside_area"),
                        rs.getDouble("x"),
                        rs.getDouble("y"),
                        rs.getDouble("r"),
                        rs.getLong("execution_time"),
                        rs.getTimestamp("timestamp")
                    );
                    points.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearAll() {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            String sql = "DELETE FROM point";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.executeUpdate();
            }
            points.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
