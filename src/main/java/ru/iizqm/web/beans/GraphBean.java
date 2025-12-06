package ru.iizqm.web.beans;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ru.iizqm.web.models.Point;

@Named("graphBean")
@RequestScoped
public class GraphBean implements Serializable {
    @Inject
    private PointSessionBean pointSessionBean;

    @Inject
    private FormBean formBean;

    public String getFullSvgElement() {
        List<Point> points = pointSessionBean.getPoints();

        StringBuilder svg = new StringBuilder();
        svg.append("<svg viewBox=\"-125 -125 250 250\" ")
        .append("xmlns=\"http://www.w3.org/2000/svg\" id=\"graph\" ")
        .append("style=\"pointer-events: auto\" onclick=\"handleSvgClick(evt)\">");

        if (formBean.getrValue() != null) 
        {
            svg.append("<polygon fill=\"green\" points=\"" + (20 * formBean.getrValue()) + ",0 0,0 0,-" + (20 * formBean.getrValue()) + "\" id=\"figure1\"/>");
            svg.append("<polygon fill=\"green\" points=\"-" + (20 * formBean.getrValue()) + ",0 -" + (20 * formBean.getrValue()) + 
                       ",-" + (10 * formBean.getrValue()) + " 0,-" + (10 * formBean.getrValue()) + " 0,0\" id=\"figure2\"/>");
            svg.append("<path d=\"M 0 0 L -" + (20 * formBean.getrValue()) + " 0 A " + (20 * formBean.getrValue()) + 
                       " " + (20 * formBean.getrValue()) + " 0 0 0 0 " + (20 * formBean.getrValue()) + " Z\" fill=\"green\" id=\"figure3\"/>");
        }

        svg.append("<line stroke=\"#fff\" x1=\"-125\" x2=\"115\" y1=\"0\" y2=\"0\" stroke-width=\"1\"/>");
        svg.append("<line stroke=\"#fff\" x1=\"0\" x2=\"0\" y1=\"-115\" y2=\"125\" stroke-width=\"1\"/>");

        /*//svg.append("<line stroke=\"#fff\" x1=\"50\" x2=\"50\" y1=\"5\" y2=\"-5\" stroke-width=\"1\"/>");
        svg.append("<line stroke=\"#fff\" x1=\"100\" x2=\"100\" y1=\"5\" y2=\"-5\" stroke-width=\"1\"/>");
        //svg.append("<line stroke=\"#fff\" x1=\"-50\" x2=\"-50\" y1=\"5\" y2=\"-5\" stroke-width=\"1\"/>");
        svg.append("<line stroke=\"#fff\" x1=\"-100\" x2=\"-100\" y1=\"5\" y2=\"-5\" stroke-width=\"1\"/>");

        //svg.append("<line stroke=\"#fff\" x1=\"5\" x2=\"-5\" y1=\"50\" y2=\"50\" stroke-width=\"1\"/>");
        svg.append("<line stroke=\"#fff\" x1=\"5\" x2=\"-5\" y1=\"100\" y2=\"100\" stroke-width=\"1\"/>");
        //svg.append("<line stroke=\"#fff\" x1=\"5\" x2=\"-5\" y1=\"-50\" y2=\"-50\" stroke-width=\"1\"/>");
        svg.append("<line stroke=\"#fff\" x1=\"5\" x2=\"-5\" y1=\"-100\" y2=\"-100\" stroke-width=\"1\"/>");*/

        for (int i = 1; i < 6; i++) {
            svg.append("<line stroke=\"#fff\" x1=\""  + 20*i + "\" x2=\""  + 20*i + "\" y1=\"3\" y2=\"-3\" stroke-width=\"1\"/>");
            svg.append("<line stroke=\"#fff\" x1=\"-" + 20*i + "\" x2=\"-" + 20*i + "\" y1=\"3\" y2=\"-3\" stroke-width=\"1\"/>");
            svg.append("<line stroke=\"#fff\" x1=\"3\" x2=\"-3\" y1=\""  + 20*i + "\" y2=\""  + 20*i + "\" stroke-width=\"1\"/>");
            svg.append("<line stroke=\"#fff\" x1=\"3\" x2=\"-3\" y1=\"-" + 20*i + "\" y2=\"-" + 20*i + "\" stroke-width=\"1\"/>");
        }

        svg.append("<polygon fill=\"#fff\" points=\"0,-125 -5,-114 0,-115 5,-114\"/>");
        svg.append("<polygon fill=\"#fff\" points=\"125,0 114,5 115,0 114,-5\"/>");

        svg.append("<text x=\"100\" y=\"14\" font-size=\"12\" fill=\"#fff\" text-anchor=\"middle\">5</text>");
        svg.append("<text x=\"-100\" y=\"14\" font-size=\"12\" fill=\"#fff\" text-anchor=\"middle\">-5</text>");
        svg.append("<text x=\"6\" y=\"-100\" font-size=\"12\" fill=\"#fff\" dominant-baseline=\"middle\">5</text>");
        svg.append("<text x=\"6\" y=\"100\" font-size=\"12\" fill=\"#fff\" dominant-baseline=\"middle\">-5</text>");
        /*svg.append("<text x=\"50\" y=\"20\" font-size=\"12\" fill=\"#fff\" text-anchor=\"middle\">R/2</text>");
        svg.append("<text x=\"-50\" y=\"20\" font-size=\"12\" fill=\"#fff\" text-anchor=\"middle\">-R/2</text>");
        svg.append("<text x=\"8\" y=\"-50\" font-size=\"12\" fill=\"#fff\" dominant-baseline=\"middle\">R/2</text>");
        svg.append("<text x=\"8\" y=\"50\" font-size=\"12\" fill=\"#fff\" dominant-baseline=\"middle\">-R/2</text>");*/

        for (int i = points.size() - 1; i >= 0; i--) {
            Point p = points.get(i);
            svg.append("<circle cx='" + (p.getX() / 5.f * 100) +
                "' cy='" + (-p.getY() / 5.f * 100) +
                "' r='2' fill='" + (p.isInsideArea() ? "#fff" : "#f00") + "'/>");
        }

        svg.append("</svg>");
        return svg.toString();
    }
}
