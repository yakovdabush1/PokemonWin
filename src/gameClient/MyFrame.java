package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a very simple GUI class to present a
 * game on a graph - you are welcome to use this class - yet keep in mind
 * that the code is not well written in order to force you improve the
 * code and not to take it "as is".
 *
 */
public class MyFrame extends JFrame{

	private int _ind;
	private Arena _ar;
	private gameClient.util.Range2Range _w2f;

	MyFrame(String a) {
		super(a);
		int _ind = 0;
	}

	public void update(Arena ar) {
		this._ar = ar;
		updateFrame();
	}

	private void updateFrame() {
		Range rx = new Range(20,this.getWidth()-20);
		Range ry = new Range(this.getHeight()-10,150);
		Range2D frame = new Range2D(rx,ry);
		directed_weighted_graph g = _ar.getGraph();
		_w2f = Arena.w2f(g,frame);
	}

	public void paint(Graphics g) {

		int w = this.getWidth();
		int h = this.getHeight();

		g.clearRect(0, 0, w, h);

		updateFrame();

//		try {
//			boolean b = g.drawImage(ImageIO.read(new File("Images/arena.png")),0,0, w, h, this);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		drawPokemons(g);

		drawGraph(g);

		drawAgants(g);

		drawInfo(g);
		
	}

	private void drawInfo(Graphics g) {
		List<String> str = _ar.get_info();
		String dt = "none";
		for(int i=0;i<str.size();i++) {
			g.drawString(str.get(i)+" dt: "+dt,100,60+i*20);
		}
		
	}

	private void drawGraph(Graphics g) {
		directed_weighted_graph gg = _ar.getGraph();
		Iterator<node_data> iter = gg.getV().iterator();
		while(iter.hasNext()) {
			node_data n = iter.next();
			g.setColor(Color.blue);
			drawNode(n,5,g);
			Iterator<edge_data> itr = gg.getE(n.getKey()).iterator();
			while(itr.hasNext()) {
				edge_data e = itr.next();
				g.setColor(Color.gray);
				drawEdge(e, g);
			}
		}
	}
	private void drawPokemons(Graphics g) {
		List<CL_Pokemon> fs = _ar.getPokemons();
		if(fs!=null) {
//		Iterator<CL_Pokemon> itr = fs.iterator();
		
//		while(itr.hasNext()) {
			int s = fs.size();
			for(int i = 0 ; i < s; i++) {
				System.out.println("size: " + fs.size());
			CL_Pokemon f = fs.get(i);

			Point3D c = f.getLocation();
			int r=10;
			g.setColor(Color.green);
			if(f.getType()<0) {g.setColor(Color.orange);}
			if(c!=null) {

				geo_location fp = this._w2f.world2frame(c);

//				*************************************** Pokemon Image

				g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
//				g.drawImage(Toolkit.getDefaultToolkit().createImage("Images/Pica.gif"),(int)fp.x()-r, (int)fp.y()-r, 4*r, 4*r, this);

////***************************************************************
////				geo_location fp = this.range.world2frame(agent_location);
//				g.drawImage(Toolkit.getDefaultToolkit().createImage("Images/Pica.gif"), (int) fp.x() - 30, (int) fp.y() - 30, 5 * r, 5 * r, this);
////				g.drawImage(blur, (int) fp.x() - 75, (int) fp.y() - 70, 140, 35, this);
//				g.setColor(Color.RED);
//				g.setFont(new Font("Segoe UI", Font.BOLD, 25));
//				g.drawString("Ash", (int) fp.x() - 70, (int) fp.y() - 44);
//				g.setColor(Color.WHITE);
//				g.setFont(new Font("Segoe UI", Font.BOLD, 15));
//				g.drawString("Points:", (int) fp.x() - 20, (int) fp.y() - 7 * r);
////				g.drawString("Speed:" + (int) rs.get(i).getSpeed(), (int) fp.x() - 20, (int) fp.y() - 5 * r);
//				g.setFont(new Font("Segoe UI", Font.BOLD, 20));
////***************************************************************


				g.setColor(Color.BLACK);
				g.drawString("value: " + f.getValue(), (int)fp.x(), (int)fp.y()-4*r);
				
			}
		}
		}
	}
	private void drawAgants(Graphics g) {
		List<CL_Agent> rs = _ar.getAgents();
	//	Iterator<OOP_Point3D> itr = rs.iterator();
		g.setColor(Color.red);
		int i=0;
		while(rs!=null && i<rs.size()) {
			geo_location c = rs.get(i).getLocation();
			int r=8;
			i++;
			if(c!=null) {

				geo_location fp = this._w2f.world2frame(c);
				g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
			}
		}
	}
	private void drawNode(node_data n, int r, Graphics g) {
		geo_location pos = n.getLocation();
		geo_location fp = this._w2f.world2frame(pos);
		g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
		g.drawString(""+n.getKey(), (int)fp.x(), (int)fp.y()-4*r);
	}
	private void drawEdge(edge_data e, Graphics g) {
		directed_weighted_graph gg = _ar.getGraph();
		geo_location s = gg.getNode(e.getSrc()).getLocation();
		geo_location d = gg.getNode(e.getDest()).getLocation();
		geo_location s0 = this._w2f.world2frame(s);
		geo_location d0 = this._w2f.world2frame(d);
		g.drawLine((int)s0.x(), (int)s0.y(), (int)d0.x(), (int)d0.y());
	//	g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);
	}
}
