package api;

import org.junit.platform.engine.support.hierarchical.Node;

import java.io.Serializable;
import java.util.*;

public class DWGraph_DS implements directed_weighted_graph {

//    /**
//     *
//     */
//    private static final long serialVersionUID = 1L;

    private HashMap<Integer, HashMap<Integer,edge_data>> Ni;
    private HashMap<Integer, node_data> graphMap;

    private int EdgeCount;
    private int MC;

    public DWGraph_DS(){
        this.graphMap = new HashMap<Integer, node_data>();
        this.Ni = new HashMap<Integer,HashMap<Integer,edge_data>>();

        EdgeCount = 0;
        MC = 0;
    }


    @Override
    public node_data getNode(int key) {
        if(graphMap.containsKey(key)){
            return graphMap.get(key);
        }
        return null;
    }

    private boolean hasEdge(int node1, int node2) {
        return Ni.get(node1).containsKey(node2);
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        if(hasEdge(src,dest) && src != dest){
            return Ni.get(src).get(dest);
        }
        return null;
    }

    @Override
    public void addNode(node_data n) {
        if(!graphMap.containsKey(n.getKey())){

            MC++;

            graphMap.put(n.getKey(), n);
            Ni.put(n.getKey(), new HashMap<Integer, edge_data>());
        }
    }

    @Override
    public void connect(int src, int dest, double w) {
        if(graphMap.containsKey(src) && graphMap.containsKey(dest) && src != dest && w >= 0) {

            if (!hasEdge(src, dest)) {
                EdgeData newEdge = new EdgeData(src, dest, w);
                Ni.get(src).put(dest, newEdge);
                MC++;
                EdgeCount++;
            }
        }
    }

    @Override
    public Collection<node_data> getV() {
        return graphMap.values();
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        if(graphMap.containsKey(node_id)) {
            return Ni.get(node_id).values();
        }
        return null;
    }

    @Override
    public node_data removeNode(int key) {
        if(graphMap.containsKey(key)){

            if(Ni.containsKey(key)) {

                Iterator<node_data> iterator = getV().iterator();
                while ((iterator.hasNext())) {
                    int destKey = iterator.next().getKey();
                    removeEdge(key, destKey);
                    removeEdge(destKey, key);
                }
                Ni.remove(key);
            }

            return graphMap.remove(key);
        }

        return null;
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
        if(this.Ni.get(src).containsKey(dest) && hasEdge(src,dest) && src != dest){

//            edge_data edge = Ni.get(src).get(dest);

            EdgeCount--;
            MC++;

            return Ni.get(src).remove(dest);
        }
        return null;
    }

    @Override
    public int nodeSize() {
        return graphMap.size();
    }

    @Override
    public int edgeSize() {
        return EdgeCount;
    }

    @Override
    public int getMC() {
        return MC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWGraph_DS that = (DWGraph_DS) o;
        boolean flag = false;
        if (graphMap.size() == that.nodeSize() &&
                EdgeCount == that.EdgeCount) {
            flag = true;
            for (int src : graphMap.keySet())
                for (Integer dest : Ni.get(src).keySet())
                    if (((DWGraph_DS) o).getEdge(src, dest).getSrc() != src || ((DWGraph_DS) o).getEdge(src, dest).getDest() != dest)
                        flag = false;
        }

        return flag;

    }

}