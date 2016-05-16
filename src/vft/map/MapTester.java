package vft.map;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;


public class MapTester extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	private Layout layout;
	private Point2D p;
	private Point2D o;
	private int size = 5;
	public MapTester() {
		setPreferredSize(new Dimension(800, 800));
		setLayout(null);
		
		layout = Layout.genPgram(5);
		layout.setOrigin(new Point2D.Double(400, 400));
		o = layout.getOrigin();
		MouseAdapter ma = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				p = e.getPoint();
				if(e.isShiftDown()) {
					o = p;
					layout.setOrigin(p);
					repaint();
				}
			}
			public void mouseReleased(MouseEvent e) {
				o = layout.getOrigin();
			}
			

			@Override
			public void mouseDragged(MouseEvent e) {
				double dx = e.getX() - p.getX();
				double dy = e.getY() - p.getY();
				
				layout.setOrigin(new Point2D.Double(o.getX() + dx, o.getY() + dy));

				dx = 0;
				dy = 0;
				repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				FractionalHex fh = layout.pixelToHexRaw(e.getPoint());
				Hex hex = fh.round();
				for(Hex h : layout.map) {
					h.setSelected(h.equals(hex));
				}
				repaint();
			}
			
		};
		addMouseMotionListener(ma);
		addMouseListener(ma);

		JLabel sizeLabel = new JLabel(""+size);
		sizeLabel.setBounds(112, 15, 46, 14);
		add(sizeLabel);
		
		JButton incSize = new JButton("+");
		incSize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				size++;
				layout = Layout.genPgram(size);
				layout.setOrigin(o);
				sizeLabel.setText(""+size);
				repaint();
			}
		});
		incSize.setBounds(10, 11, 41, 23);
		add(incSize);
		
		JButton decSize = new JButton("-");
		decSize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				size--;
				layout = Layout.genPgram(size);
				sizeLabel.setText(""+size);
				layout.setOrigin(o);
				repaint();
			}
		});
		decSize.setBounds(61, 11, 41, 23);
		add(decSize);
		
		
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.BLACK);
		layout.paint(g2d);
		
	}
	
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					MapTester m = new MapTester();
					frame.getContentPane().add(m);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.pack();
					m.repaint();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
