package game;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;

public class main extends JFrame {

	private JPanel contentPane;
	private boolean uppressed=false;
	private boolean downpressed=false;
	private boolean spacepressed=false;
	private int acceleration = 15;
	private int time =0;
	private int X = 10;
	private int Y = 124;
	private boolean crashed=false;
	private static File file = new File("score");
	private ArrayList<Integer> scores = new ArrayList<Integer>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main frame = new main();
					frame.setVisible(true);
					ImageIcon icon = new ImageIcon("greater.jpg");
					
					frame.setIconImage(icon.getImage());
					frame.setTitle("VarDodge.exe");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel high = new JLabel(" ");
		high.setForeground(Color.GREEN);
		high.setBounds(334, 2, 92, 13);
		contentPane.add(high);
		
		Scanner myReader;
		try {
			myReader = new Scanner(file);
			while (myReader.hasNextLine()) {
		        scores.add(Integer.parseInt(myReader.nextLine()));
		       high.setText(Integer.toString(Collections.max(scores)));
		      }
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		JLabel player = new JLabel(">");
		player.setForeground(Color.CYAN);
		player.setFont(new Font("Tahoma", Font.BOLD, 17));
		player.setBounds(X, Y, 22, 32);
		contentPane.add(player);
		
		JLabel obsticle = new JLabel("X");
		obsticle.setForeground(new Color(255, 127, 80));
		obsticle.setFont(new Font("Tahoma", Font.BOLD, 17));
		obsticle.setBounds(334, 221, 22, 32);
		contentPane.add(obsticle);
		
		JLabel clock = new JLabel("e");
		clock.setFont(new Font("Tahoma", Font.BOLD, 14));
		clock.setBackground(Color.WHITE);
		clock.setForeground(Color.GRAY);
		clock.setEnabled(false);
		clock.setBounds(0, 0, 45, 13);
		contentPane.add(clock);
		
		JLabel end = new JLabel(" ");
		end.setFont(new Font("Tahoma", Font.PLAIN, 23));
		end.setForeground(Color.RED);
		end.setBounds(158, 80, 227, 82);
		contentPane.add(end);
		
		
		UpAction w = new UpAction();
		UpReleased nonw = new UpReleased();
		contentPane.getInputMap().put(KeyStroke.getKeyStroke("W"), "w");
		contentPane.getActionMap().put("w", w);
		contentPane.getInputMap().put(KeyStroke.getKeyStroke("released W"), "nonw");
		contentPane.getActionMap().put("nonw", nonw);
		DownAction s = new DownAction();
		DownReleased nons = new DownReleased();
		contentPane.getInputMap().put(KeyStroke.getKeyStroke("S"), "s");
		contentPane.getActionMap().put("s", s);
		contentPane.getInputMap().put(KeyStroke.getKeyStroke("released S"), "nons");
		contentPane.getActionMap().put("nons", nons);
		SpacePressed space = new SpacePressed();
		SpaceReleased nonspace = new SpaceReleased();
		contentPane.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "space");
		contentPane.getActionMap().put("space", space);
		contentPane.getInputMap().put(KeyStroke.getKeyStroke("released SPACE"), "nonspace");
		contentPane.getActionMap().put("nonspace", nonspace);
		
		Timer timer = new Timer(1, new ActionListener() {
	        public void actionPerformed(ActionEvent a) {
	        	if(spacepressed==true) {
	        		end.setText(" ");
        			if(crashed==true) {
        				player.setLocation(X, Y);
        				acceleration=15;
        				crashed=false;
        			}
	        	}
	        	if(crashed==false) {
	        	if(uppressed==true) {
	        		if(player.getY()>0) {
	        			player.setLocation(player.getX(), player.getY()-5);
	        			
	        		}
	        	}
	        	if(downpressed==true) {
	        		if(player.getY()<230) {
	        		player.setLocation(player.getX(), player.getY()+5);
	        		}
	        	}
	        	if(obsticle.getX()<0) {
	        		
	        		int randy = (int) (Math.random()*((30-0)))*10;
	        		obsticle.setLocation(400, randy);
	        	}
	        	
	        	
	        		obsticle.setLocation(obsticle.getX()-acceleration, obsticle.getY());
	        		time++;
	        	}
	        	clock.setText(Integer.toString(time));
	        	if((obsticle.getY()<player.getY()+10)&&obsticle.getY()>player.getY()-10) {
	        		if(obsticle.getX()<player.getX()+10&&obsticle.getX()<player.getX()-10) {
	        			end.setText("CRASH ('SPACEBAR')");
	        			scores.add(time);
	        			time = 0;
	        			acceleration = 0;
	        			crashed=true;
	        			
	        			high.setText(Integer.toString(Collections.max(scores)));
	        			try {
	  				      FileWriter myWriter = new FileWriter("score");
	  				      myWriter.write(Integer.toString(Collections.max(scores)));
	  				      myWriter.close();
	  				}catch(Exception e1) {
	  					System.out.println(e1);
	  				}
	        		}
	        		
	        	}
	        	if(time%400==0) {
	        		acceleration++;
	        	}
	        	
	        }
		});timer.start();
	        }
	
	

class UpAction extends AbstractAction{
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		uppressed=true;
		//leftpressed=false;
	}
	
}
class UpReleased extends AbstractAction{

public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	uppressed=false;
	//leftpressed=false;
}

}
class DownAction extends AbstractAction{
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		downpressed=true;
		//leftpressed=false;
	}
	
}
class DownReleased extends AbstractAction{

public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	downpressed=false;
	//leftpressed=false;
}

}
class SpacePressed extends AbstractAction{

public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	spacepressed=true;
	//leftpressed=false;
}

}
class SpaceReleased extends AbstractAction{

public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	spacepressed=false;
	//leftpressed=false;
}

}
}