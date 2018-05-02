package gameUI;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Ma
 * 
 * @author kwankaew
 *
 */
public class MainFrame extends JFrame{
	private final int FRAME_WIDTH = 1280;
	private final int FRAME_HIGHT = 720;
	private static JFrame frame;

	private static void initialize() {
		System.out.println("start");
		frame = new JFrame("Lion Villain");
		frame.setSize(new Dimension(1280, 720));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//		setPanel(new gameUI.IndexUI().getPanel());
		setPanel(new gameUI.InsertNameUI().getPanel());
//		setPanel(new gameUI.SinglePlayUI().getSinglePlayModePanel());
	}

//	public static void show() {
//		frame.setVisible(true);
//	}
	
	public static void setPanel(JPanel panel) {
		frame.getContentPane().removeAll();
		frame.repaint();
		panel.setBounds(0, 0, 1280, 720);
		frame.getContentPane().add(panel);
		panel.setVisible(true);
		panel.setFocusable(true);
		panel.requestFocusInWindow();
	}

	public static void main(String[] args) {
//		initialize(new gameUI.DualPlayUI().getDualPlayModePanel());
		initialize();
		frame.setVisible(true);
//		show();
//		test test = new test();
//		test.show();
	}

}
