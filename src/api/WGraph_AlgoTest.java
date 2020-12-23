package api;

import api.*;
import gameClient.util.Point3D;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class WGraph_AlgoTest {

    @Test
    public final void testInit() {
        dw_graph_algorithms graph_ = new DWGraph_Algo();
        directed_weighted_graph g= new DWGraph_DS();
        graph_.init(g);
        NodeData node8 = new NodeData(8, new Point3D(0, 0, 0));

        g.addNode(node8);
        graph_.getGraph().addNode(node8);
        assertEquals(8, graph_.getGraph().getNode(8).getKey());
    }

    @Test
    public final void testGetGraph() {
        dw_graph_algorithms graph_ =new DWGraph_Algo();
        directed_weighted_graph g = new DWGraph_DS();

        assertNull(graph_.getGraph());
    }

    @Test
    public final void testCopy() {
        dw_graph_algorithms graph_ =new DWGraph_Algo();
        directed_weighted_graph g=new DWGraph_DS();
        graph_.init(g);

        NodeData node0 = new NodeData(0, new Point3D(0, 0, 0));
        g.addNode(node0);

        NodeData node1 = new NodeData(1, new Point3D(0, 0, 0));
        g.addNode(node1);

        NodeData node2= new NodeData(2, new Point3D(0, 0, 0));
        g.addNode(node2);

        directed_weighted_graph g1 = new DWGraph_DS();
        g1 = (directed_weighted_graph) graph_.copy();
        assertEquals(2, g1.getNode(2).getKey());
    }

    @Test
    public final void testDjikstra() {
        dw_graph_algorithms graph_ = new DWGraph_Algo();
        directed_weighted_graph g= new DWGraph_DS();
        graph_.init(g);

        g.addNode(new NodeData(0, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(1, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(2, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(3, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(4, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(5, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(6, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(7, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(8, new Point3D(0, 0, 0)));

        graph_.getGraph().connect(1, 2, 8);
        graph_.getGraph().connect(1, 0, 6);
        graph_.getGraph().connect(1, 3, 5);
        graph_.getGraph().connect(2, 3, 2);
        graph_.getGraph().connect(3, 5, 7);
        graph_.getGraph().connect(4, 2, 12);
        graph_.getGraph().connect(5, 6, 1);
        graph_.getGraph().connect(1, 6, 4);

        assertEquals(1,1);
    }

    @Test
    public final void testIsConnected() {
        dw_graph_algorithms graph_ = new DWGraph_Algo();
        directed_weighted_graph g = new DWGraph_DS();

        graph_.init(g);

        g.addNode(new NodeData(0, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(1, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(2, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(3, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(4, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(5, new Point3D(0, 0, 0)));

        g.connect(2, 3, 6);
        g.connect(3, 4, 6);


        assertFalse(graph_.isConnected());

        g.connect(0, 1, 6);
        g.connect(1, 2, 6);
        g.connect(2, 3, 6);
        g.connect(4, 5, 6);

        assertFalse(graph_.isConnected());

    }


    @Test
    public final void testShortestPathDist() {
        dw_graph_algorithms graph_ = new DWGraph_Algo();
        directed_weighted_graph g = new DWGraph_DS();
        graph_.init(g);

        g.addNode(new NodeData(0, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(1, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(2, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(3, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(4, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(5, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(6, new Point3D(0, 0, 0)));

        graph_.getGraph().connect(1, 2, 8);
        graph_.getGraph().connect(1, 0, 6);
        graph_.getGraph().connect(1, 3, 90);
        graph_.getGraph().connect(2, 3, 2);
        graph_.getGraph().connect(1, 4, 23);
        graph_.getGraph().connect(3, 5, 7);
        graph_.getGraph().connect(4, 5, 10);
        graph_.getGraph().connect(2, 4, 3);
        graph_.getGraph().connect(5, 6, 9);
        graph_.getGraph().connect(1, 6, 3);

        assertEquals(graph_.shortestPathDist(1, 4) ,11 ,0.01);
    }

    @Test
    public final void testShortestPath() {
        dw_graph_algorithms graph_ = new DWGraph_Algo();
        directed_weighted_graph g = new DWGraph_DS();
        graph_.init(g);

        g.addNode(new NodeData(0, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(1, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(2, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(3, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(4, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(5, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(6, new Point3D(0, 0, 0)));

        graph_.getGraph().connect(1, 2, 8);
        graph_.getGraph().connect(1, 0, 6);
        graph_.getGraph().connect(1, 3, 5);
        graph_.getGraph().connect(2, 3, 2);
        graph_.getGraph().connect(3, 5, 7);
        graph_.getGraph().connect(4, 2, 12);
        graph_.getGraph().connect(5, 6, 1);
        graph_.getGraph().connect(1, 6, 4);

        List<node_data> sp = new ArrayList<>();
        sp = graph_.shortestPath(1, 5);

        int[] checkKey = {1, 3, 5};
        int i = 0;
        for(node_data n: sp) {
            //assertEquals(n.getTag(), checkTag[i]);
            assertEquals(n.getKey(), checkKey[i]);
            i++;
        }

        List<node_data> sp2 = graph_.shortestPath(3, 2);

        assertNull(sp2);


    }



    @Test
    public final void save_load() {

        dw_graph_algorithms graph_ = new DWGraph_Algo();
        directed_weighted_graph g = new DWGraph_DS();

        g.addNode(new NodeData(0, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(1, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(2, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(3, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(4, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(5, new Point3D(0, 0, 0)));
        g.addNode(new NodeData(6, new Point3D(0, 0, 0)));

        g.connect(1, 2, 8);
        g.connect(1, 0, 6);
        g.connect(1, 3, 5);
        g.connect(2, 3, 2);
        g.connect(3, 5, 7);
        g.connect(4, 2, 12);
        g.connect(5, 6, 1);
        g.connect(1, 6, 4);

        graph_.init(g);

        String str = "./data/save_test";
        graph_.save(str);

        directed_weighted_graph g2 = new DWGraph_DS();

        g2.addNode(new NodeData(0, new Point3D(0, 0, 0)));
        g2.addNode(new NodeData(1, new Point3D(0, 0, 0)));
        g2.addNode(new NodeData(2, new Point3D(0, 0, 0)));
        g2.addNode(new NodeData(3, new Point3D(0, 0, 0)));
        g2.addNode(new NodeData(4, new Point3D(0, 0, 0)));
        g2.addNode(new NodeData(5, new Point3D(0, 0, 0)));
        g2.addNode(new NodeData(6, new Point3D(0, 0, 0)));

        g2.connect(1, 2, 8);
        g2.connect(1, 0, 6);
        g2.connect(1, 3, 5);
        g2.connect(2, 3, 2);
        g2.connect(3, 5, 7);
        g2.connect(4, 2, 12);
        g2.connect(5, 6, 1);
        g2.connect(1, 6, 4);

        System.out.println(graph_.load(str));

        System.out.println(graph_.getGraph().getEdge(4, 2));

        assertEquals(g, g2);

        g.removeNode(0);
        assertNotEquals(g, g2);

    }

}
