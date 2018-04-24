package gameUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SinglePlayUI {

	private JFrame frame = new JFrame();

	private JPanel singlePlayPanel;
	private JLabel lion,distance,time,distanceLabel,timeLabel ;
	private JLabel label = new JLabel();
	private JTextField textfield = new JTextField();

	public static void main(String[] args) {
		new SinglePlayUI();
	}

	public SinglePlayUI() {
		initialize();
	}

	private void initialize() {
		singlePlayPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				try {
					BufferedImage img = ImageIO.read(this.getClass().getResourceAsStream("/res/single_mode.png"));
					g.drawImage(img, 0, 0, 1280, 720, null);
				} catch (IOException e) {

				}
			}
		};
		singlePlayPanel.setBounds(0, 0, 1280, 720);
		singlePlayPanel.setLayout(null);
		
		timeLabel = new JLabel();
		timeLabel.setFont(new Font("Andale Mono",Font.PLAIN,20));
		timeLabel.setText("Time: ");
		timeLabel.setBounds(44, 35, 80, 25);
		singlePlayPanel.add(timeLabel);
		
		distanceLabel = new JLabel();
		distanceLabel.setFont(new Font("Andale Mono",Font.PLAIN,20));
		distanceLabel.setText("Distance: ");
		distanceLabel.setBounds(44,70,300,25);
		singlePlayPanel.add(distanceLabel);

		label.setFont(new Font("LayijiMahaniyomV105", Font.PLAIN, 45));
		label.setText("label");
		label.setBounds(497, 178, 213, 57);
		singlePlayPanel.add(label);

		textfield.setFont(new Font("LayijiMahaniyomV105", Font.PLAIN, 45));
		textfield.setBounds(711, 168, 106, 75);
		singlePlayPanel.add(textfield);
		
		ImageIcon lion_in_cage = new ImageIcon(getClass().getResource("/res/lion_in_cage.png"));
		lion = new JLabel(lion_in_cage);
		lion.setBounds(493, 375, 333, 264);
		singlePlayPanel.add(lion);

		frame.getContentPane().add(singlePlayPanel);
		frame.setSize(new Dimension(1280, 720));
		frame.setLocationRelativeTo(null);
		frame.setTitle("Lion Villain");
		frame.setResizable(false);
		frame.setVisible(true);
	}

	// /**
	// * shows game-over pop-up.
	// * @param controller
	// */
	// private void showGameOverPopup(MainFXMLDocumentController controller) {
	// @SuppressWarnings("deprecation")
	// BorderPane content =BorderPaneBuilder.create()
	// .minWidth(230).minHeight(130)
	// .bottom(getBottomBox(controller))
	// .center(getCenterBox())
	// .style( "-fx-background-color:linear-gradient(darkslategrey, wheat, white);"
	// + "-fx-background-radius:7;"
	// + "-fx-border-radius:7")
	// .build();
	// pp = new Popup();
	// pp.setAutoHide(true);
	// pp.getContent().add(content);
	// pp.show(controller.DOWN.getScene().getWindow());
	// }

}
