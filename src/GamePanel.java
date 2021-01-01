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
	Timer conditions = new Timer(1000, this);
	Timer hailStorm = new Timer(500, this);
	int day = 0;
	String events = "";
	public static BufferedImage charImg;
	Map map = new Map(36, 20);
	int randomspawnx;
	int randomspawny;
	Character c;
	Font font;
	int gameState = 1;
	boolean win;
	Timer[] trees;
	Timer[] treestwo;
	int[] treeX;
	int[] treeY;
	int[] treeSize;
	int treecounter;

	public GamePanel() {
		/*
		 * try { charImg =
		 * ImageIO.read(this.getClass().getResourceAsStream("download.png")); } catch
		 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
		if (gameState == 1) {
			t.start();
			z.start();
			map.generateTerrain();
			trees = new Timer[map.trees];
			treeX = new int[map.trees];
			treeY = new int[map.trees];
			treeSize = new int[map.trees];
			treestwo = new Timer[map.trees];
			randomspawnx = new Random().nextInt(35);
			randomspawny = new Random().nextInt(19);
			while (map.Map[randomspawnx][randomspawny] > 0) {
				randomspawnx = new Random().nextInt(36);
				randomspawny = new Random().nextInt(20);
			}
			c = new Character(randomspawnx, randomspawny);
			font = new Font("Arial", Font.BOLD, 20);
		}
	}

	public void paintComponent(Graphics g) {
		if (gameState == 1) {
			g.drawImage(charImg, 100, 100, 100, 100, null);
			map.drawGrid(g);
			for (int i = 0; i < treecounter; i++) {
				if (trees[i].isRunning()) {
					g.setColor(Color.MAGENTA);
					g.fillRect(treeX[i] * 50 + 50 , treeY[i] * 50 + 50, treeSize[i], treeSize[i]);
					System.out.println(trees[i].getInitialDelay());
				}
			}
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString("Day: " + day, 20, 30);
			c.update(map.Map);
			g.drawString("Stamina: " + c.stamina, 100, 30);
			g.drawRect(250, 10, 100, 25);
			g.setColor(Color.ORANGE);
			g.fillRect(251, 11, c.food / 3, 24);
			c.draw(g);
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
			if (win == false) {
				g.setColor(Color.RED);
				g.fillRect(0, 0, 1900, 1100);
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
				if(trees[j].isRunning()) {
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
				c.snow = false;
				for (int i = 0; i < 20; i++) {
					for (int j = 0; j < 36; j++) {
						if (map.Map[j][i] == -2) {
							map.Map[j][i] = 0;
						}
					}
				}
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
		conditions.restart();
		System.out.println("snow storm!");
		events = "snow storm";
		c.snow = true;
	}

	public void hailStorm() {
		conditions.restart();
		hailStorm.restart();
		events = "hail storm";
		map.hailStorm();
	}

	public void freeze() {
		events = "permafrost";
		map.freeze();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (gameState == 1) {
			if (arg0.getKeyCode() == KeyEvent.VK_Q) {
				boolean harvest = false;
				int gridX = (c.x - 50) / 50;
				int gridY = (c.y - 50) / 50;
				int gridXn = (c.x - 10) / 50;
				int gridYn = (c.y - 10) / 50;
				int treenumber = treecounter;
				System.out.println(gridX + " " + gridY);
				System.out.println(gridXn + " " + gridYn);
				if (gridXn < 35) {
					if (map.Map[gridX + 1][gridY] == 1 && harvest == false) {
						c.food += 25;
						map.Map[gridX + 1][gridY] = 4;
						harvest = true;
						for(int i = 0; i < trees.length; i++) {
							if(treeX[i] == gridX + 1 && treeY[i] == gridY) {
								treenumber = i;
							} else if(i+1 == trees.length) {
							treeX[treecounter] = gridX + 1;
							treeY[treecounter] = gridY;
							}
						}
					}

					else if (map.Map[gridXn + 1][gridYn] == 1 && harvest == false) {
						c.food += 25;
						map.Map[gridXn + 1][gridYn] = 4;
						harvest = true;
						for(int i = 0; i < trees.length; i++) {
							if(treeX[i] == gridXn + 1 && treeY[i] == gridYn) {
								treenumber = i;
							} else if(i+1 == trees.length) {
							treeX[treecounter] = gridXn + 1;
							treeY[treecounter] = gridYn;
							}
						}
					}
				}
				if (gridYn < 19) {
					if (map.Map[gridX][gridY + 1] == 1 && harvest == false) {
						c.food += 25;
						map.Map[gridX][gridY + 1] = 4;
						harvest = true;
						for(int i = 0; i < trees.length; i++) {
							if(treeX[i] == gridX && treeY[i] == gridY + 1) {
								treenumber = i;
							} else if(i+1 == trees.length) {
							treeX[treecounter] = gridX;
							treeY[treecounter] = gridY + 1;
							}
						}
					} else if (map.Map[gridXn][gridYn + 1] == 1 && harvest == false) {
						c.food += 25;
						map.Map[gridXn][gridYn + 1] = 4;
						harvest = true;
						for(int i = 0; i < trees.length; i++) {
							if(treeX[i] == gridXn && treeY[i] == gridYn + 1) {
								treenumber = i;
							} else if(i+1 == trees.length) {
							treeX[treecounter] = gridXn;
							treeY[treecounter] = gridYn + 1;
							}
						}
					}
				}
				if (gridX > 0) {
					if (map.Map[gridX - 1][gridY] == 1 && harvest == false) {
						c.food += 25;
						map.Map[gridX - 1][gridY] = 4;
						harvest = true;
						for(int i = 0; i < trees.length; i++) {
							if(treeX[i] == gridX - 1 && treeY[i] == gridY) {
								treenumber = i;
							} else if(i+1 == trees.length) {
							treeX[treecounter] = gridX - 1;
							treeY[treecounter] = gridY;
							}
						}
					} else if (map.Map[gridXn - 1][gridYn] == 1 && harvest == false) {
						c.food += 25;
						map.Map[gridXn - 1][gridYn] = 4;
						harvest = true;
						for(int i = 0; i < trees.length; i++) {
							if(treeX[i] == gridXn - 1 && treeY[i] == gridYn) {
								treenumber = i;
							} else if(i+1 == trees.length) {
							treeX[treecounter] = gridXn - 1;
							treeY[treecounter] = gridYn;
							}
						}
					}
				}
				if (gridY > 0) {
					if (map.Map[gridX][gridY - 1] == 1 && harvest == false) {
						c.food += 25;
						map.Map[gridX][gridY - 1] = 4;
						harvest = true;
						for(int i = 0; i < trees.length; i++) {
							if(treeX[i] == gridX && treeY[i] == gridY - 1) {
								treenumber = i;
							} else if(i+1 == trees.length) {
							treeX[treecounter] = gridX;
							treeY[treecounter] = gridY - 1;
							}
						}
					} else if (map.Map[gridXn][gridYn - 1] == 1 && harvest == false) {
						c.food += 25;
						map.Map[gridXn][gridYn - 1] = 4;
						harvest = true;
						for(int i = 0; i < trees.length; i++) {
							if(treeX[i] == gridXn && treeY[i] == gridYn - 1) {
								treenumber = i;
							} else if(i+1 == trees.length) {
							treeX[treecounter] = gridXn;
							treeY[treecounter] = gridYn - 1;
							}
						}
					}
				}
				if (harvest == true) {
					trees[treenumber] = new Timer(10000, this);
					treestwo[treenumber] = new Timer(10000/50, this);
					trees[treenumber].restart();
					treestwo[treenumber].restart();
					treeSize[treenumber] = 0;
					if(treenumber == treecounter) {
						treecounter++;
					}
					
				}
				if (c.food > 300) {
					c.food = 300;
				}
			}
			if (arg0.getKeyCode() == KeyEvent.VK_W) {
				c.moving = true;
				c.up = true;
			}
			if (arg0.getKeyCode() == KeyEvent.VK_A) {
				c.moving = true;
				c.left = true;
			}
			if (arg0.getKeyCode() == KeyEvent.VK_S) {
				c.moving = true;
				c.down = true;
			}
			if (arg0.getKeyCode() == KeyEvent.VK_D) {
				c.moving = true;
				c.right = true;
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
