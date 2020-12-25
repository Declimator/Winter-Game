import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener{
	// 100/3, 60000, 10000
	Timer t = new Timer(100 / 3, this);
	Timer z = new Timer(10000, this);
	Timer conditions = new Timer(5000, this);
	int day = 0;
	public static BufferedImage charImg;
	Map map = new Map(36, 20);
	int randomspawnx;
	int randomspawny;
	Character c;
	Font font;
	public GamePanel() {
		/*try {
			charImg = ImageIO.read(this.getClass().getResourceAsStream("download.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		t.start();
		z.start();
		map.generateTerrain();
		randomspawnx = new Random().nextInt(36);
		randomspawny = new Random().nextInt(20);
		while(map.Map[randomspawnx][randomspawny] > 0) {
			randomspawnx = new Random().nextInt(36);
			randomspawny = new Random().nextInt(20);
		}
		c = new Character(randomspawnx, randomspawny);
		font = new Font("Arial", Font.BOLD, 20);
	}
	public void paintComponent(Graphics g) {
		g.drawImage(charImg, 100, 100, 100, 100, null);
		map.drawGrid(g);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("Day: " + day, 20, 30);
		c.update(map.Map);
		g.drawString("Stamina: " + c.stamina, 100, 30);
		c.draw(g);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
		if(arg0.getSource() == z) {
			day++;
			int r = new Random().nextInt(1);
			if(r == 0) {
				snowStorm();
			} else if(r == 1) {
				
			}
		}
	}
	public void snowStorm() {
		System.out.println("snow storm!");
	}
	public void hailStorm() {
		
		
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode() == KeyEvent.VK_W) {
			c.moving = true;
			c.up = true;
		} 
		if(arg0.getKeyCode() == KeyEvent.VK_A) {
			c.moving = true;
			c.left = true;
		} 
		if(arg0.getKeyCode() == KeyEvent.VK_S) {
			c.moving = true;
			c.down = true;
		} 
		if(arg0.getKeyCode() == KeyEvent.VK_D){
			c.moving = true;
			c.right = true;
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode() == KeyEvent.VK_W) {
			c.up = false;
		} else if(arg0.getKeyCode() == KeyEvent.VK_A) {
			c.left = false;
		} else if(arg0.getKeyCode() == KeyEvent.VK_S) {
			c.down = false;
		} else if(arg0.getKeyCode() == KeyEvent.VK_D){
			c.right = false;
		}
		if(c.up == false && c.down == false && c.left == false && c.right == false) {
			c.moving = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
