package ru.iizqm.web.models;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Point implements Serializable {
    private double x;
    private double y;
    private double r;
    private boolean insideArea;
    private Date timestamp;
    private long executionTime;

    public double getX() { return x; }
    public double getY() { return y; }
    public double getR() { return r; }

    public boolean isInsideArea() { return insideArea; }
    public Date getTimestamp() { return timestamp; }
    public long getExecutionTime() { return executionTime; }

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

}