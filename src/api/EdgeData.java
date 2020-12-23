package api;

import java.io.Serializable;
import java.util.Objects;

public class EdgeData implements edge_data {

    int src, dest;
    double w;
    String info;
    int tag;

   public EdgeData(int src,int dest,double w){
       this.src = src;
       this.dest = dest;
       this.w = w;
   }

    /**
     * The id of the source node of this edge.
     * @return
     */

    public int getSrc(){
        return src;
    }

    /**
     * The id of the destination node of this edge
     * @return
     */
    public int getDest(){
        return dest;
    }
    /**
     * @return the weight of this edge (positive value).
     */
    public double getWeight(){
        return w;
    }
    /**
     * Returns the remark (meta data) associated with this edge.
     * @return
     */
    public String getInfo(){
        return info;
    }
    /**
     * Allows changing the remark (meta data) associated with this edge.
     * @param s
     */
    public void setInfo(String s){
        info = s;
    }
    /**
     * Temporal data (aka color: e,g, white, gray, black)
     * which can be used be algorithms
     * @return
     */
    public int getTag(){
        return tag;
    }
    /**
     * This method allows setting the "tag" value for temporal marking an edge - common
     * practice for marking by algorithms.
     * @param t - the new value of the tag
     */
    public void setTag(int t){
        tag = t;
    }

    @Override
    public String toString() {
        return "{s:"+getSrc()+" -> d:"+getDest()+", w:"+getWeight()+"}";
    }
}