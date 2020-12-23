package api;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public class NodeData implements node_data, Comparator<node_data>, Comparable<node_data>{

    private int key, tag;
    private String info;
    private double w;
    private geo_location location;

    public NodeData(int key, geo_location location){
        this.key = key;
        this.info = "White";
        this.tag = -1;
        this.w = 0.0;
        this.location = location;
    }

    @Override
    public int getKey() {
        return key;
    }

    @Override
    public geo_location getLocation() {
        return location;
    }

    @Override
    public void setLocation(geo_location p) {
        location = p;
    }

    @Override
    public double getWeight() {
        return w;
    }

    @Override
    public void setWeight(double w) {
        this.w = w;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return tag;
    }

    @Override
    public void setTag(int t) {
        tag = t;
    }

    @Override
    public int compareTo(node_data o) {
        Double w1 = this.getWeight();
        Double w2 = o.getWeight();

        return w1.compareTo(w2);
    }

    /**
     * compare 2 nodes
     * based on their weights
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(node_data o1, node_data o2) {
        double w1 = o1.getWeight();
        double w2 = o2.getWeight();

        return Double.compare(w1, w2);
    }

    @Override
    public String toString() {
        return "{k: " + getKey() + ", i: " + getInfo() + ", w: " + getWeight() + ", tag: " + getTag() + "}";
    }
}
