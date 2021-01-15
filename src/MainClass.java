import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
public class MainClass {
JFrame frame;
GamePanel panel;
public final static int framewidth = 1900;
public final static int frameheight = 1100;
void start() {
	
	frame = new JFrame();

	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setTitle("Survive the Winter");
	frame.setBackground(Color.BLACK);
	panel = new GamePanel();
	panel.setPreferredSize(new Dimension(framewidth, frameheight));
	frame.add(panel);
	frame.addKeyListener(panel);
	frame.pack();
	panel.t.start();
}
public static void main(String[] args) {
	MainClass m = new MainClass();
	m.start();
}
}
