package ru.iizqm.web.models;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "point")
public class Point implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private double x;
    private double y;
    private double r;

    @Column(name = "inside_area")
    private boolean insideArea;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp")
    private Date timestamp;

    @Column(name = "execution_time")
    private long executionTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public double getR() { return r; }
    public void setR(double r) { this.r = r; }

    public boolean isInsideArea() { return insideArea; }
    public void setInsideArea(boolean insideArea) { this.insideArea = insideArea; }

    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }

    public long getExecutionTime() { return executionTime; }
    public void setExecutionTime(long executionTime) { this.executionTime = executionTime; }

    public Point() {}

    public Point(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public Point(boolean insideArea, double x, double y, double r, long executionTime, Date timestamp) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.insideArea = insideArea;
        this.executionTime = executionTime;
        this.timestamp = timestamp;
    }

    public void calc() {
        long now = System.nanoTime();
        insideArea = (x >= 0.f && y >= 0.f && x + y <= r) ||
                (x <= 0 && y >= 0 && x >= -r && y <= r / 2.f) ||
                (x <= 0 && y <= 0 && (Math.pow(x, 2.f) + Math.pow(y, 2.f) <= Math.pow(r, 2.f)));

        timestamp = new Date(System.currentTimeMillis());
        executionTime = System.nanoTime() - now;
    }

    public String getFormattedX() {
        return String.format("%.2f", x);
    }

    public String getFormattedY() {
        return String.format("%.2f", y);
    }
}