import java.awt.Color;
import java.awt.Graphics;

public class Map {
	public int Map [][];
	public void drawGrid(Graphics g) {
		int disp;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1900, 1100);
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 18; j++) {
				g.setColor(Color.LIGHT_GRAY);
				if(i % 2 == 0) {
					disp = 0;
				} else {
					disp = 50;
				}
				g.fillRect(j * 100 + disp+50, i*50+50, 50, 50);
			}
		}
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 18; j++) {
				g.setColor(Color.WHITE);
				if(i % 2 == 0) {
					disp = 50;
				} else {
					disp = 0;
				}
				g.fillRect(j * 100 + disp+50, i*50+50, 50, 50);
			}
		}
	}
}
//1900*1100
