package gameUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;


import game.ObjectPool;
import game.Villager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class test extends JFrame implements Observer {

	private ObjectPool objectPool;
	private Renderer renderer;
	
	public test() {
		objectPool = new ObjectPool();
		objectPool.addObserver(this);
		
		renderer = new Renderer();
		JTextField textField = new JTextField();
		textField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println(e.getKeyCode());
				try {
					int offSet = 30;
					objectPool.burstVillagers(e.getKeyCode() + offSet);
					
				} catch (Exception e2) {
					e2.getMessage();
				}
			}
		});
		textField.setLocation(500, 500);
		renderer.add(textField);
		
		setLayout(new BorderLayout());
		add(renderer);

		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pack();
	}
	
	public void start() {
		setVisible(true);
	}
	
	public static void main(String[] args) {
		System.out.println("start");
		test test = new test();
		test.start();
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}
	
	class Renderer extends JPanel {
		public Renderer() {
			setDoubleBuffered(true);
			setPreferredSize(new Dimension(objectPool.getWidth(), objectPool.getHeight()));
		}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			
			BufferedImage img = null;
			try {
				img = ImageIO.read(this.getClass().getResource("/res/push.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Draw space
			
			for (Villager villager : objectPool.getVillager()) {
//				g.fillOval(bullet.getX(), bullet.getY(), 100, 100);
//				System.out.println(villager.getX() + " " + villager.getY());
				g.drawImage(img, 1200 + villager.getX(), 375+villager.getY(),111,120,null);
			}
		}
	}
	
	
	
	
//	@Override
//	public void start(Stage primaryStage) {
//		Parent root;
//		try {
//			root = FXMLLoader.load(getClass().getResource("/gameUI/indexUI.fxml"));
//			Scene scene = new Scene(root);
//			primaryStage.setScene(scene);
//			primaryStage.setTitle("test");
//			primaryStage.show();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void main(String[] args) {
//		launch(args);
//	}

}
