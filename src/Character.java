import java.awt.Color;
import java.awt.Graphics;

public class Character {
	int TL, BR;
	int x = 55; int y = 55;
	double speed = 10;
	int d;
	int stamina = 500;
	boolean up, down, left, right, moving;
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(x, y, 40, 40);
	}
	public void update() {
		if(moving == false && stamina < 500) {
			stamina+=10;
			if(stamina > 500) {
				stamina = 500;
			}
		} 
		if(stamina > 0 && moving == true) {
			speed = 10;
			d = 0;
			if(up == true) {
				d++;
			} 
			if(down == true) {
				d++;
			}
			if(left == true) {
				d++;
			}
			if(right == true) {
				d++;
			}
			if(d == 2) {
				speed = 10*Math.sqrt(2)/2;
			}
			if(up == true) {
				y-=speed;
			} 
			if(down == true) {
				y+=speed;
			}
			if(left == true) {
				x-=speed;
			}
			if(right == true) {
				x+=speed;
			}
			stamina--;
		}
	}
}
