import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Map {
	public int[][] Map;
	int width;
	int height;
	Map(int w, int h){
		Map = new int[36][20];
		this.width = w;
		this.height = h;
	}
	public void drawGrid(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1900, 1100);
		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < 36; j++) {
				if(Map[j][i] == 1) {
					g.setColor(Color.GREEN);
				} else if(Map[j][i] == 2) {
					g.setColor(Color.DARK_GRAY);
				}
				else if(i % 2 == 0) {
					if(j % 2 == 0) {
						g.setColor(Color.LIGHT_GRAY);
					} else {
						g.setColor(Color.WHITE);
					}
				} else {
					if(j % 2 == 1) {
						g.setColor(Color.LIGHT_GRAY);
					} else {
						g.setColor(Color.WHITE);
					}
				}
				g.fillRect(j * 50 + 50, i*50 + 50, 50, 50);
			}
		}
		/*
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 18; j++) {
				g.setColor(Color.LIGHT_GRAY);
				if(Map[i][j] == 1) {
					g.setColor(Color.GREEN);
				}
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
				if(Map[i][j] == 1) {
					g.setColor(Color.GREEN);
				}
				if(i % 2 == 0) {
					disp = 50;
				} else {
					disp = 0;
				}
				g.fillRect(j * 100 + disp+50, i*50+50, 50, 50);
			}
		}
		*/
	}
	public void generateTerrain() {
		generateTrees();
		generateRocks();
	}
	public void generateTrees() {
		int random1 = new Random().nextInt(20)+60;
		int randomx;
		int randomy;
		System.out.println(random1);
		for(int i = 0; i < random1; i++) {
			randomx = new Random().nextInt(36);
			randomy = new Random().nextInt(20);
			while(Map[randomx][randomy] > 0) {
				randomx = new Random().nextInt(36);
				randomy = new Random().nextInt(20);
			}
			Map[randomx][randomy] = 1;
		}
	}
	public void generateRocks() {
		int random1 = new Random().nextInt(20)+30;
		int randomx;
		int randomy;
		System.out.println(random1);
		for(int i = 0; i < random1; i++) {
			randomx = new Random().nextInt(36);
			randomy = new Random().nextInt(20);
			while(Map[randomx][randomy] > 0) {
				randomx = new Random().nextInt(36);
				randomy = new Random().nextInt(20);
			}
			Map[randomx][randomy] = 2;
		}
	}
}
//1900*1100
