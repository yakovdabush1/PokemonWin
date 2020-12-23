package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.util.*;

public class Ex2 implements Runnable{

    private static directed_weighted_graph graph;
    private static dw_graph_algorithms algo;
    private static HashMap<Integer, List<node_data>> paths;
    private static HashMap<Integer, Integer> agent_lock; //<agent_id, pok_dest>

    private static MyFrame _win;
    private static Arena _ar;

    private static long id;
    private static int level;

    public static void main(String[] a) {

        if ( a.length > 0) {

            id = Integer.parseInt(a[0]);
            level = Integer.parseInt(a[1]);

            Thread client = new Thread(new Ex2());
            client.start();
        }
        else {

            //gui

//            PokimonGUI l = new PokimonGUI();

//            JFrame frame = new JFrame("login page");

            String id_text = JOptionPane.showInputDialog("Enter your ID", "Your ID");
            id = Long.parseLong(id_text);

            while(true){
                String level_text = JOptionPane.showInputDialog("Enter level number", "Insert a scenario ");

                try {
                    level = Integer.parseInt(level_text);
                    break;
                } catch (Exception e) {
                    JOptionPane.showInputDialog("Invalid level number, Please try again", "Inane warning");
                }
            }



            Thread client = new Thread(new Ex2());
            client.start();
        }


    }

    @Override
    public void run() {

        game_service game = Game_Server_Ex2.getServer(level); // you have [0,23] games

        //	int id = 999;
        game.login(id);
        String g = game.getGraph();
        String pks = game.getPokemons();
        directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();

        init(game);

        game.startGame();

        for (CL_Agent a : Arena.getAgents(game.getAgents(), graph)) {

            agent_lock.put(a.getID(), -1);
            paths.put(a.getID(), new ArrayList<>());

        }

        _win.setTitle("Ex2 - OOP: (NONE trivial Solution) "+game.toString());
        int ind=0;
        long dt=100;

        while(game.isRunning()) {

            moveAgants(game, gg);

            try {
                if(ind%1==0) {

                    _win.repaint();

                }
                Thread.sleep(dt);
                ind++;
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        String res = game.toString();

        System.out.println(res);
        System.exit(0);
    }
    /**
     * Moves each of the agents along the edge,
     * in case the agent is on a node the next destination (next edge) is chosen (randomly).
     * @param game
     * @param gg
     * @param
     */
    private static void moveAgants(game_service game, directed_weighted_graph gg) {

        String lg = game.move();

        List<CL_Agent> agents = Arena.getAgents(lg, gg);
        _ar.setAgents(agents);

        String fs =  game.getPokemons();
        List<CL_Pokemon> list_pokemons = Arena.json2Pokemons(fs);
        _ar.setPokemons(list_pokemons);

        for (CL_Pokemon p : list_pokemons){
            Arena.updateEdge(p,graph);
        }

        for(int i=0; i < agents.size(); i++) {

            CL_Agent ag = agents.get(i);

            int id = ag.getID();
            int dest = ag.getNextNode();
            int src = ag.getSrcNode();
            double v = ag.getValue();
            double s = ag.getSpeed();

            if(dest==-1) {

//                dest = nextNode(gg, src);

                if (paths.get(id).isEmpty() || paths.get(id).size() == 1) {
                    agent_lock.put(id, -1);

                    _ar.setPokemons(Arena.json2Pokemons(game.getPokemons()));
                    findBestNode(ag, list_pokemons, agents);
                }

                dest = paths.get(id).remove(1).getKey();

                game.chooseNextEdge(ag.getID(), dest);

                System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+dest);
            }
        }
    }

    private static void findBestNode(CL_Agent current_agent, List<CL_Pokemon> pokemons, List<CL_Agent> agents) {

        Queue<Double> q = new PriorityQueue<>((x, y) -> Double.compare(y, x));
        HashMap<Double, CL_Pokemon> map = new HashMap<>();

        for (CL_Pokemon p : pokemons) {

            double d = algo.shortestPathDist(current_agent.getSrcNode(), p.get_edge().getSrc()) + 1;

            double a = p.getValue() / d;

//            double c = graph.nodeSize() / pokemons.size();

            q.add(a);
            map.put(p.getValue() / d, p);

        }

        while (!q.isEmpty()) {

            double pr = q.poll();
            CL_Pokemon p = map.get(pr);

            if (!agent_lock.containsValue(p.get_edge().getDest())) {

                List<node_data> shortest_path = algo.shortestPath(current_agent.getSrcNode(), p.get_edge().getSrc());
                shortest_path.add(graph.getNode(p.get_edge().getDest()));

                paths.put(current_agent.getID(), shortest_path);
                agent_lock.put(current_agent.getID(), p.get_edge().getDest());

                break;
            }

        }

    }

    private void init(game_service game) {
        String g = game.getGraph();
        String fs = game.getPokemons();

        algo = new DWGraph_Algo();

        agent_lock = new HashMap<>();
        paths = new HashMap<>();

        //gg.init(g);
        _ar = new Arena();

        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");

            //init algo by load
            String s = ttt.toString();

            String path = s.substring(s.indexOf("data/"), s.indexOf("\",\"agents"));
            System.out.println("path: " + path);

            algo.load(path);
            graph = algo.getGraph();
            _ar.setGraph(graph);

            _ar.setPokemons(Arena.json2Pokemons(fs));
            _win = new MyFrame("test Ex2");
            _win.setSize(1000, 700);
            _win.update(_ar);
            _win.show();

            int rs = ttt.getInt("agents");
            System.out.println(info);
            System.out.println(game.getPokemons());
            int src_node = 0;  // arbitrary node, you should start at one of the pokemon

            Queue<Double> pok_vals = new PriorityQueue<>((x, y) -> Double.compare(y, x));
            HashMap<Double, CL_Pokemon> map = new HashMap<>();

            ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());

            for(int a = 0;a<cl_fs.size();a++) {
                Arena.updateEdge(cl_fs.get(a),graph);

                pok_vals.add(cl_fs.get(a).getValue());
                map.put(cl_fs.get(a).getValue(), cl_fs.get(a));
            }

            for(int a = 0;a<rs;a++) {

                CL_Pokemon pokemon = map.get(pok_vals.poll());

                game.addAgent(pokemon.get_edge().getSrc());
            }
        }
        catch (JSONException e) {e.printStackTrace();}
    }
}
