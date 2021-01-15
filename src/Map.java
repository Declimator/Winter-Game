import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Map {
	public int[][] Map;
	int width;
	int height;
	int trees;
	boolean isTree = false;
	boolean isBerryTree = false;

	Map(int w, int h) {
		Map = new int[36][20];
		this.width = w;
		this.height = h;
	}

	public void drawGrid(Graphics g, BufferedImage BerryTree, BufferedImage Tree, String snowstorm) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1900, 1100);
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 36; j++) {
				if (Map[j][i] == 1) {
					g.setColor(Color.GREEN);
					isTree = true;
				} else if (Map[j][i] == 2) {
					g.setColor(Color.DARK_GRAY);
				} else if (Map[j][i] == 3) {
					g.setColor(Color.CYAN);
				} else if (Map[j][i] == 4) {
					g.setColor(Color.getHSBColor(27, 100, 46));
					isBerryTree = true;
				} else if (Map[j][i] == -1) {
					g.setColor(Color.ORANGE);
				} else if (Map[j][i] == -2) {
					g.setColor(Color.RED);
				} else if (i % 2 == 0) {
					if (j % 2 == 0) {
							g.setColor(Color.LIGHT_GRAY);
					} else {
						g.setColor(Color.WHITE);
					}
				} else {
					if (j % 2 == 1) {
							g.setColor(Color.LIGHT_GRAY);
					} else {
						g.setColor(Color.WHITE);
					}
				}
				if (isTree == true)
					g.drawImage(Tree, j * 50 + 50, i * 50 + 50, 50, 50, null);
				else if (isBerryTree == true)
					g.drawImage(BerryTree, j * 50 + 50, i * 50 + 50, 50, 50, null);
				else
					g.fillRect(j * 50 + 50, i * 50 + 50, 50, 50);
				isTree = false;
				isBerryTree = false;
			}
		}
	}

	public void generateTerrain() {
		Map = new int[36][20];
		generateTrees();
		generateRocks();
	}

	public void generateTrees() {
		int random1 = new Random().nextInt(20) + 60;
		trees = random1;
		int randomx;
		int randomy;
		System.out.println(random1);
		for (int i = 0; i < random1; i++) {
			randomx = new Random().nextInt(36);
			randomy = new Random().nextInt(20);
			while (Map[randomx][randomy] > 0) {
				randomx = new Random().nextInt(36);
				randomy = new Random().nextInt(20);
			}
			Map[randomx][randomy] = 1;
		}
	}

	public void generateRocks() {
		int random1 = new Random().nextInt(20) + 30;
		int randomx;
		int randomy;
		System.out.println(random1);
		for (int i = 0; i < random1; i++) {
			randomx = new Random().nextInt(36);
			randomy = new Random().nextInt(20);
			while (Map[randomx][randomy] > 0) {
				randomx = new Random().nextInt(36);
				randomy = new Random().nextInt(20);
			}
			Map[randomx][randomy] = 2;
		}
	}

	public void freeze(int day) {
		int random1 = new Random().nextInt(10) + 10 + day;
		int randomx;
		int randomy;
		System.out.println(random1);
		for (int i = 0; i < random1; i++) {
			randomx = new Random().nextInt(36);
			randomy = new Random().nextInt(20);
			while (Map[randomx][randomy] > 0) {
				randomx = new Random().nextInt(36);
				randomy = new Random().nextInt(20);
			}
			Map[randomx][randomy] = 3;
		}
	}

	public void hailStorm(int day) {
		int random1 = new Random().nextInt(20) + 10 + 2 * day;
		int randomx;
		int randomy;
		System.out.println(random1);
		for (int i = 0; i < random1; i++) {
			randomx = new Random().nextInt(36);
			randomy = new Random().nextInt(20);
			while (Map[randomx][randomy] > 0) {
				randomx = new Random().nextInt(36);
				randomy = new Random().nextInt(20);
			}
			Map[randomx][randomy] = -1;
		}
	}
}
//1900*1100
