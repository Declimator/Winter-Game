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

public class GamePanel extends JPanel implements ActionListener, KeyListener {
	// 100/3, 60000, 10000
	Timer t = new Timer(100 / 3, this);
	Timer z = new Timer(10000, this);
	Timer snowStorm = new Timer(5000, this);
	Timer conditions = new Timer(1000, this);
	Timer hailStorm = new Timer(200, this);
	int day = 0;
	String events = "";
	boolean facingRight = true;
	public static BufferedImage charLeft;
	public static BufferedImage charRight;
	public static BufferedImage bush;
	public static BufferedImage berryBush;
	Map map = new Map(36, 20);
	int randomspawnx;
	int randomspawny;
	Character c;
	Font font;
	Font titleFont;
	Font subFont;
	Font visualFont;
	Font minisubFont;
	int gameState = 0;
	boolean win;
	Timer[] trees;
	Timer[] treestwo;
	int[] treeX;
	int[] treeY;
	int[] treeSize;
	int treecounter;
	boolean gameStart = true;
	boolean hitboxes = false;
	boolean snow = false;
	boolean eventInfo = false;

	public GamePanel() {

		try {
			charLeft = ImageIO.read(this.getClass().getResourceAsStream("char_left.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block e.printStackTrace();
		}
		try {
			charRight = ImageIO.read(this.getClass().getResourceAsStream("char_right.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block e.printStackTrace();
		}
		try {
			bush = ImageIO.read(this.getClass().getResourceAsStream("bush.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block e.printStackTrace();
		}
		try {
			berryBush = ImageIO.read(this.getClass().getResourceAsStream("berry_bush.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block e.printStackTrace();
		}
		createMap();
		c = new Character(randomspawnx, randomspawny);
		font = new Font("Arial", Font.BOLD, 20);
		subFont = new Font("Arial", Font.PLAIN, 80);
		titleFont = new Font("Arial", Font.BOLD, 150);
		visualFont = new Font("Arial", Font.BOLD, 70);
		minisubFont = new Font("Arial", Font.PLAIN, 40);

	}

	void createMap() {
		snow = false;
		day = 0;
		treecounter = 0;
		map.generateTerrain();
		trees = new Timer[map.trees];
		treeX = new int[map.trees];
		treeY = new int[map.trees];
		treeSize = new int[map.trees];
		treestwo = new Timer[map.trees];
		randomspawnx = new Random().nextInt(30);
		randomspawny = new Random().nextInt(15);
		while (map.Map[randomspawnx][randomspawny] > 0) {
			randomspawnx = new Random().nextInt(36);
			randomspawny = new Random().nextInt(20);
		}
		c = new Character(randomspawnx, randomspawny);
	}

	void startGame() {
		t.restart();
		z.restart();
		System.out.println("started");
	}

	public void paintComponent(Graphics g) {
		if (gameState == 0) {
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, 1900, 1100);
				g.setColor(Color.getHSBColor(11, 200, 99));
				for (int i = 0; i < 22; i++) {
					for (int j = 0; j < 19; j++) {
						if (i % 2 == 0)
							g.fillRect(j * 100, i * 50, 50, 50);
						else
							g.fillRect(j * 100 + 50, i * 50, 50, 50);
					}
				}
				if (eventInfo == true) {
					g.setColor(Color.CYAN);
					
				} else {
				g.setColor(Color.CYAN);
				g.setFont(titleFont);
				g.drawString("Survive the Winter", 280, 200);
				g.setFont(subFont);
				g.drawString("Survive for 20 days", 555, 330);
				g.drawString("Press SPACE to play", 535, 1000);
				for (int i = 0; i < 5; i++) {
					g.drawRect(225 + i, 425 + i, 100 - 2 * i, 100 - 2 * i);
					g.drawRect(225 + i, 535 + i, 100 - 2 * i, 100 - 2 * i);
					g.drawRect(115 + i, 535 + i, 100 - 2 * i, 100 - 2 * i);
					g.drawRect(335 + i, 535 + i, 100 - 2 * i, 100 - 2 * i);
					g.drawRect(850 + i, 535 + i, 100 - 2 * i, 100 - 2 * i);
					g.drawRect(1500 + i, 535 + i, 100 - 2 * i, 100 - 2 * i);
				}
				g.setFont(visualFont);
				g.drawString("W", 242, 505);
				g.drawString("S", 250, 612);
				g.drawString("A", 140, 612);
				g.drawString("D", 360, 612);
				g.drawString("Q", 870, 612);
				g.drawString("H", 1525, 612);
				g.setFont(subFont);
				g.drawString("Movement", 90, 750);
				g.drawString("Eat near Trees", 650, 750);
				g.drawString("Show hitboxes", 1300, 750);
			}
		} else if (gameState == 1) {
			if (facingRight == true)
				g.drawImage(charRight, 100, 100, 100, 100, null);
			else
				g.drawImage(charLeft, 100, 100, 100, 100, null);
			map.drawGrid(g, bush, berryBush, events);
			if (gameStart == false) {
				for (int i = 0; i < treecounter; i++) {
					if (trees[i].isRunning()) {
						g.setColor(Color.RED);
						g.drawRect(treeX[i] * 50 + 50 + (50 - treeSize[i]) / 2,
								treeY[i] * 50 + 50 + (50 - treeSize[i]) / 2, treeSize[i], treeSize[i]);
					}
				}
			}
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString("Day: " + day, 20, 30);
			c.update(map.Map, snow);
			g.drawString("Stamina: " + c.stamina, 100, 30);
			g.drawRect(250, 10, 100, 25);
			g.setColor(Color.ORANGE);
			g.fillRect(251, 11, c.food / 5, 24);
			if (facingRight == true)
				c.draw(g, charRight, hitboxes);
			else
				c.draw(g, charLeft, hitboxes);
			g.setColor(Color.WHITE);
			g.drawRect(400, 10, 150, 25);
			if (events != "") {
				g.setColor(Color.RED);
				g.drawString(events + "!", 410, 30);
			} else {
				g.setColor(Color.BLACK);
				g.fillRect(401, 11, 149, 24);
			}
		} else if (gameState == 2) {
			t.stop();
			z.stop();
			snowStorm.stop();
			hailStorm.stop();
			conditions.stop();
			for (int i = 0; i < treecounter; i++) {
				trees[i].stop();
			}
			events = "";

			gameStart = true;
			if (win == false) {
				g.setColor(Color.RED);
				g.fillRect(0, 0, 1900, 1100);
				g.setColor(Color.BLACK);
				g.setFont(titleFont);
				g.drawString("You Died", 630, 300);
				g.setFont(subFont);
				g.drawString("Days Survived: " + day, 660, 700);
				g.drawString("Press SPACE to restart", 535, 1000);
			} else {
				g.setColor(Color.BLUE);
				g.fillRect(0, 0, 1900, 1100);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (c.gameState == 2) {
			gameState = 2;
			win = false;
		}
		for (int j = 0; j < treestwo.length; j++) {
			if (arg0.getSource() == treestwo[j]) {
				if (trees[j].isRunning()) {
					treeSize[j]++;
				}
			}
		}
		// TODO Auto-generated method stub
		repaint();

		if (gameState == 1) {
			if (arg0.getSource() == z) {
				day++;
				if (day == 30) {
					gameState = 2;
					win = true;
				}
				int r = new Random().nextInt(3);
				if (r == 0) {
					snowStorm();
				} else if (r == 1) {
					hailStorm();
				} else if (r == 2) {
					freeze();
				}
			}
			if (arg0.getSource() == hailStorm) {
				for (int i = 0; i < 20; i++) {
					for (int j = 0; j < 36; j++) {
						if (map.Map[j][i] == -1) {
							map.Map[j][i] = -2;
						}
					}
				}

			}
			if (arg0.getSource() == conditions) {
				for (int i = 0; i < 20; i++) {
					for (int j = 0; j < 36; j++) {
						if (map.Map[j][i] == -2) {
							map.Map[j][i] = 0;
						}
					}
				}
			}
			if (arg0.getSource() == snowStorm) {
				snow = false;
				System.out.println("end");
			}
			for (int i = 0; i < trees.length; i++) {
				if (arg0.getSource() == trees[i]) {
					map.Map[treeX[i]][treeY[i]] = 1;
					treeSize[i] = 0;
					treestwo[i].stop();
					trees[i].stop();
				}
			}
		}
	}

	public void snowStorm() {
		snowStorm.restart();
		events = "snow storm";
		snow = true;
	}

	public void hailStorm() {
		conditions.restart();
		hailStorm.restart();
		events = "hail storm";
		map.hailStorm(day);
	}

	public void freeze() {
		events = "permafrost";
		map.freeze(day);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (gameStart == true) {
			if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
				startGame();
				createMap();
				gameState = 1;
				gameStart = false;
			}
		}
		// TODO Auto-generated method stub
		if (gameState == 1) {
			if (arg0.getKeyCode() == KeyEvent.VK_Q) {
				boolean harvest = false;
				int gridX = (c.x - 50) / 50;
				int gridY = (c.y - 50) / 50;
				int gridXn = (c.x - 10) / 50;
				int gridYn = (c.y - 10) / 50;
				int treenumber = treecounter;
				if (gridXn < 35) {
					if (map.Map[gridX + 1][gridY] == 1 && harvest == false) {
						c.food += 50;
						map.Map[gridX + 1][gridY] = 4;
						harvest = true;
						for (int i = 0; i < trees.length; i++) {
							if (treeX[i] == gridX + 1 && treeY[i] == gridY) {
								treenumber = i;
							} else if (i + 1 == trees.length) {
								treeX[treecounter] = gridX + 1;
								treeY[treecounter] = gridY;
							}
						}
					}

					else if (map.Map[gridXn + 1][gridYn] == 1 && harvest == false) {
						c.food += 50;
						map.Map[gridXn + 1][gridYn] = 4;
						harvest = true;
						for (int i = 0; i < trees.length; i++) {
							if (treeX[i] == gridXn + 1 && treeY[i] == gridYn) {
								treenumber = i;
							} else if (i + 1 == trees.length) {
								treeX[treecounter] = gridXn + 1;
								treeY[treecounter] = gridYn;
							}
						}
					}
				}
				if (gridYn < 19) {
					if (map.Map[gridX][gridY + 1] == 1 && harvest == false) {
						c.food += 50;
						map.Map[gridX][gridY + 1] = 4;
						harvest = true;
						for (int i = 0; i < trees.length; i++) {
							if (treeX[i] == gridX && treeY[i] == gridY + 1) {
								treenumber = i;
							} else if (i + 1 == trees.length) {
								treeX[treecounter] = gridX;
								treeY[treecounter] = gridY + 1;
							}
						}
					} else if (map.Map[gridXn][gridYn + 1] == 1 && harvest == false) {
						c.food += 50;
						map.Map[gridXn][gridYn + 1] = 4;
						harvest = true;
						for (int i = 0; i < trees.length; i++) {
							if (treeX[i] == gridXn && treeY[i] == gridYn + 1) {
								treenumber = i;
							} else if (i + 1 == trees.length) {
								treeX[treecounter] = gridXn;
								treeY[treecounter] = gridYn + 1;
							}
						}
					}
				}
				if (gridX > 0) {
					if (map.Map[gridX - 1][gridY] == 1 && harvest == false) {
						c.food += 50;
						map.Map[gridX - 1][gridY] = 4;
						harvest = true;
						for (int i = 0; i < trees.length; i++) {
							if (treeX[i] == gridX - 1 && treeY[i] == gridY) {
								treenumber = i;
							} else if (i + 1 == trees.length) {
								treeX[treecounter] = gridX - 1;
								treeY[treecounter] = gridY;
							}
						}
					} else if (map.Map[gridXn - 1][gridYn] == 1 && harvest == false) {
						c.food += 50;
						map.Map[gridXn - 1][gridYn] = 4;
						harvest = true;
						for (int i = 0; i < trees.length; i++) {
							if (treeX[i] == gridXn - 1 && treeY[i] == gridYn) {
								treenumber = i;
							} else if (i + 1 == trees.length) {
								treeX[treecounter] = gridXn - 1;
								treeY[treecounter] = gridYn;
							}
						}
					}
				}
				if (gridY > 0) {
					if (map.Map[gridX][gridY - 1] == 1 && harvest == false) {
						c.food += 50;
						map.Map[gridX][gridY - 1] = 4;
						harvest = true;
						for (int i = 0; i < trees.length; i++) {
							if (treeX[i] == gridX && treeY[i] == gridY - 1) {
								treenumber = i;
							} else if (i + 1 == trees.length) {
								treeX[treecounter] = gridX;
								treeY[treecounter] = gridY - 1;
							}
						}
					} else if (map.Map[gridXn][gridYn - 1] == 1 && harvest == false) {
						c.food += 50;
						map.Map[gridXn][gridYn - 1] = 4;
						harvest = true;
						for (int i = 0; i < trees.length; i++) {
							if (treeX[i] == gridXn && treeY[i] == gridYn - 1) {
								treenumber = i;
							} else if (i + 1 == trees.length) {
								treeX[treecounter] = gridXn;
								treeY[treecounter] = gridYn - 1;
							}
						}
					}
				}
				if (harvest == true) {
					trees[treenumber] = new Timer(10000, this);
					treestwo[treenumber] = new Timer(10000 / 50, this);
					trees[treenumber].restart();
					treestwo[treenumber].restart();
					treeSize[treenumber] = 0;
					if (treenumber == treecounter) {
						treecounter++;
					}

				}
				if (c.food > 500) {
					c.food = 500;
				}
			}
			if (arg0.getKeyCode() == KeyEvent.VK_W) {
				c.moving = true;
				c.up = true;
			}
			if (arg0.getKeyCode() == KeyEvent.VK_A) {
				c.moving = true;
				c.left = true;
				facingRight = false;
			}
			if (arg0.getKeyCode() == KeyEvent.VK_S) {
				c.moving = true;
				c.down = true;
			}
			if (arg0.getKeyCode() == KeyEvent.VK_D) {
				c.moving = true;
				c.right = true;
				facingRight = true;
			}
			if (arg0.getKeyCode() == KeyEvent.VK_H) {
				if (hitboxes == false)
					hitboxes = true;
				else
					hitboxes = false;
			}
			if (arg0.getKeyCode() == KeyEvent.VK_I) {
				gameState = 2;
				win = false;
				System.out.println("test");
			}
			if (arg0.getKeyCode() == KeyEvent.VK_O) {
				gameState = 2;
				win = true;
			}
			repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (gameState == 1) {
			if (arg0.getKeyCode() == KeyEvent.VK_W) {
				c.up = false;
			} else if (arg0.getKeyCode() == KeyEvent.VK_A) {
				c.left = false;
			} else if (arg0.getKeyCode() == KeyEvent.VK_S) {
				c.down = false;
			} else if (arg0.getKeyCode() == KeyEvent.VK_D) {
				c.right = false;
			}
			if (c.up == false && c.down == false && c.left == false && c.right == false) {
				c.moving = false;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
