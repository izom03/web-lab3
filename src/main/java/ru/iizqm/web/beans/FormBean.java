package ru.iizqm.web.beans;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ru.iizqm.web.models.Point;

@Named("formBean")
@ViewScoped
public class FormBean implements Serializable {
    @Inject
    private PointSessionBean pointSessionBean;

    private Float xValue;
    private List<Integer> xRange;
    private Float yValue;
    private Float rValue;
    private List<Integer> rRange;
    private Float xClick;
    private Float yClick;
    
    @PostConstruct
    public void init() {
        xRange = IntStream.rangeClosed(-3, 5)
                .boxed()
                .collect(Collectors.toList());
        rRange = IntStream.rangeClosed(1, 5)
                .boxed()
                .collect(Collectors.toList());
    }

    public void addNewPoint() {
        Point point = new Point(xValue, yValue, rValue);
        pointSessionBean.addPoint(point);
    }

    public void addNewClickPoint() {
        if (rValue == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка", "Значение R не выбрано!"));
            return;
        }
        Point point = new Point(xClick * rValue, yClick * rValue, rValue);
        pointSessionBean.addPoint(point);
    }

    public void clearAllPoints() {
        pointSessionBean.clearAll();
    }

    public void nop() {}

    public Float getxValue() { return xValue; }
    public void setxValue(Float value) { xValue = value; }

    public List<Integer> getxRange() { return xRange; }

    public Float getyValue() { return yValue; }
    public void setyValue(Float value) { yValue = value; }

    public Float getrValue() { return rValue; }
    public void setrValue(Float value) { rValue = value; }

    public List<Integer> getrRange() { return rRange; }

    public Float getxClick() { return xClick; }
    public void setxClick(Float value) { xClick = value; }

    public Float getyClick() { return yClick; }
    public void setyClick(Float value) { yClick = value; }
}