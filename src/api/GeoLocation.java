package api;

import java.io.Serializable;
import java.util.*;

public class GeoLocation implements geo_location, Serializable {
    double x,y,z;

    public GeoLocation(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double x() {
        return x;
    }
    public double y() {
        return y;
    }
    public double z() {
        return z;
    }
    public double distance(geo_location g){

        double xx = Math.pow(this.x - ((GeoLocation)g).x, 2);
        double yy = Math.pow(this.y - ((GeoLocation)g).y, 2);
        double zz = Math.pow(this.z - ((GeoLocation)g).z, 2);

        return Math.sqrt(xx + yy + zz);
    }
}
