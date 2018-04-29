package gameUI;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Ma
 * @author kwankaew
 *
 */
public class MainFrame {
	private final int FRAME_WIDTH = 1280;
	private final int FRAME_HIGHT = 720;
	private static JFrame frame;
	
	
	private static void initialize(JPanel panel) {
		System.out.println("start");
		frame = new JFrame("Lion Villain");
		frame.getContentPane().add(panel);
		frame.setSize(new Dimension(1280, 720));
//		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		initialize(new gameUI.DualPlayUI().getDualPlayModePanel());
//		initialize(new gameUI.SinglePlayUI().getSinglePlayModePanel());
	}
	
}
