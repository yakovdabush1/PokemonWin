package api;
//import com.google.gson.*;
//
//import java.awt.*;
//import java.io.*;
//import java.util.*;

import gameClient.util.Point3D;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class DWGraph_Algo implements dw_graph_algorithms {
    directed_weighted_graph graph;

    private HashMap<Integer, Double> distance;
    private HashMap<Integer, Integer> prev;

    @Override
    public void init(directed_weighted_graph g) {

        this.graph = g ;
        this.distance = new HashMap<>();
        this.prev = new HashMap<>();
    }

    @Override
    public directed_weighted_graph getGraph() { return graph; }

    @Override
    public directed_weighted_graph copy() {

        directed_weighted_graph copy = new DWGraph_DS();

        for (node_data v : getGraph().getV()) {
            copy.addNode(new NodeData(v.getKey(), v.getLocation()));
        }

        for (node_data v : getGraph().getV()) {
            for (edge_data e : getGraph().getE(v.getKey())) {

                copy.connect(e.getSrc(), e.getDest(), e.getWeight());
            }
        }

        return copy;
    }

    @Override
        public boolean isConnected() {
            Collection<node_data> v = graph.getV();
            Iterator<node_data> iter = v.iterator();
            while(iter.hasNext()) {
                node_data n = iter.next();
                clear();
                // start DFS from first node
                DFS(graph, n.getKey());

                for (node_data node: graph.getV())
                    if (node.getTag()!= new Color(1).getRGB())
                        return false;
            }
            return true;
        }
        private void clear() {
            Collection<node_data> v = graph.getV();
            Iterator<node_data> it = v.iterator();
            while(it.hasNext()) {
                node_data n = it.next();
                n.setWeight(Double.MAX_VALUE);
                n.setTag(0);
                n.setInfo(null);
            }
        }
        private static void DFS(directed_weighted_graph d2, int v){
            // mark current node as visited
            d2.getNode(v).setTag(1);
            Collection<edge_data> edges = d2.getE(v);
            // do for every edge (v -> u)
            for (edge_data u : edges){
                // u is not visited
                if (d2.getNode(u.getDest()).getTag() != new Color(1).getRGB())
                    DFS(d2, u.getDest());
            }
        }

    private void djikstra(int source) {

        PriorityQueue<node_data> queue = new PriorityQueue<>();

        Collection<node_data> l = this.graph.getV();
        Iterator<node_data> it = l.iterator();

        while (it.hasNext()) {

            node_data n = it.next();

            if (n.getKey() != source) {
                n.setWeight(Double.MAX_VALUE);
                this.distance.put(n.getKey(), Double.MAX_VALUE);
                this.prev.put(n.getKey(), null);
            }
            else {
                n.setWeight(0.0);
                this.distance.put(n.getKey(), 0.0);
                this.prev.put(n.getKey(), source);
            }

            queue.add(n);
        }

        while ( !queue.isEmpty()) {

            node_data father = queue.poll();

            Collection<edge_data> neighbors = this.graph.getE(father.getKey());
            Iterator<edge_data> it_n = neighbors.iterator();

            while (it_n.hasNext()) {

                edge_data edge_child = it_n.next();

                double alt = this.distance.get(father.getKey()) + this.graph.getEdge(edge_child.getSrc(), edge_child.getDest()).getWeight();

                if(alt < this.distance.get(edge_child.getDest())) {

                    this.distance.put(edge_child.getDest(), alt);
                    this.prev.put(edge_child.getDest(), father.getKey());

                    this.graph.getNode(edge_child.getDest()).setWeight(alt);

                    queue.remove(this.graph.getNode(edge_child.getDest()));
                    queue.add(this.graph.getNode(edge_child.getDest()));

                }

            }

        }

    }

    //dijkstra algorithem
    @Override
    public double shortestPathDist(int src, int dest) {
        djikstra(src);
        return this.distance.get(dest) == Double.MAX_VALUE ? -1 : this.distance.get(dest);
    }

//    @Override
//    public double shortestPathDist(int src, int dest) {
//        clear();
//        Queue <node_data> pq = new LinkedList<node_data>();
//        node_data source = graph.getNode(src);
//        source.setWeight(0);
//        pq.add(source)
//        while(source != null) {
//            //mark visit
//            source.setTag(1);
//            //array list of edges getting out from source
//            Collection<edge_data> eSrc = graph.getE(source.getKey());
//            //checking every node weight and change it if needed
//            for (edge_data e : eSrc) {
//                node_data neighbor = graph.getNode(e.getDest());
//                if (neighbor.getWeight() > source.getWeight() + e.getWeight()) {
//                    neighbor.setWeight(source.getWeight() + e.getWeight());
//                    neighbor.setInfo("" + source.getKey());
//                    pq.add(neighbor);
//                }
//            }
//            //remove first from queue
//            pq.poll();
//            source = pq.peek();
//
//        }
//        if (graph.getNode(dest).getWeight() == Double.MAX_VALUE) {
//            System.out.println("there is no path between "+ src + " and " + dest);
//            return Double.POSITIVE_INFINITY;
//        }
//        else {
//            return graph.getNode(dest).getWeight();
//        }
//    }



    //use shortestpathDist
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        // TODO Auto-generated method stub

        djikstra(src);

        //if no path between src -> dest return null.
        if (this.distance.get(dest) == Double.MAX_VALUE) return null;

        List<node_data> back_res = new ArrayList<>();
        List<node_data> res = new ArrayList<>();

        back_res.add(this.graph.getNode(dest));

        int current = dest;

        while (current != src) {

            back_res.add(this.graph.getNode(this.prev.get(current)));
            current = this.prev.get(current);

        }

        for (int i = back_res.size() - 1; i >= 0; i--) {
            res.add(back_res.get(i));
        }

        return res;
    }

    @Override
       public boolean save(String file) {

        JSONArray nodes = new JSONArray();
        JSONArray edges = new JSONArray();
        try {

        for (node_data vertex : getGraph().getV()) {

            JSONObject node = new JSONObject();

            node.put("pos", vertex.getLocation().toString());
            node.put("id", vertex.getKey());

            nodes.put(node);

            for (edge_data e : getGraph().getE(vertex.getKey())) {

                JSONObject edge = new JSONObject();

                edge.put("src", e.getSrc());
                edge.put("w", e.getWeight());
                edge.put("dest", e.getDest());

                edges.put(edge);
            }

        }

            FileWriter fw = new FileWriter(file);

            JSONObject temp = new JSONObject();
            temp.put("Edges", edges);
            temp.put("Nodes", nodes);

            fw.write(temp.toString());
            fw.close();


        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return true;

        }

    @Override
    public boolean load(String file) {

        directed_weighted_graph new_graph = new DWGraph_DS();

        try {
            String json = new String(Files.readAllBytes(Paths.get(file)));

            JSONObject object = new JSONObject(json);

            JSONArray nodes = object.getJSONArray("Nodes");
            JSONArray edges = object.getJSONArray("Edges");

            for (int i = 0; i < nodes.length(); i++) {

                String pos = nodes.getJSONObject(i).getString("pos");
                int id = nodes.getJSONObject(i).getInt("id");

                new_graph.addNode(new NodeData(id, new Point3D(pos)));

            }

            for (int i = 0; i < edges.length(); i++) {

                int src = edges.getJSONObject(i).getInt("src");
                int dest = edges.getJSONObject(i).getInt("dest");
                int w = edges.getJSONObject(i).getInt("w");

                new_graph.connect(src, dest, w);
            }

            init(new_graph);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
