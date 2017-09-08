package Classes.XmlTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Сергей on 10.05.2017.
 */
public class Area {
    private String inn;
    private String name;
    private String timeZone;
    private List<MeasuringPoint> measPointList = new ArrayList<>();

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MeasuringPoint> getMeasPointList() {
        return measPointList;
    }

    public void addMeasPoint(MeasuringPoint measPoint) {
        measPointList.add(measPoint);
    }
}
