import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Character {
	int TL, BR;
	int x, y;
	double speed = 10;
	int d;
	int food = 500;
	int gameState = 0;
	int stamina = 500;
	boolean up, down, left, right, moving;
	Character(int x, int y) {
		this.x = x * 50 + 55;
		this.y = y * 50 + 55;
	}

	public void draw(Graphics g, BufferedImage image, boolean hitboxes) {
		if(hitboxes == true) {
		g.setColor(Color.BLUE);
		g.drawRect(x-5, y-5, 50, 50);
		}
		g.drawImage(image, x, y, 40, 40, null);
	}

	public void update(int[][] map, boolean snow) {
		int gridX, gridY, gridXn, gridYn;
		food--;
		if(food == 0) {
			gameState = 2;
		}
		gridX = (x - 50) / 50;
		gridXn = (x - 5) / 50;
		gridY = (y - 50) / 50;
		gridYn = (y - 5) / 50;
		if (map[gridX][gridY] == -2 || map[gridXn][gridY] == -2 || map[gridX][gridYn] == -2 || map[gridXn][gridYn] == -2) {
			gameState = 2;
			System.out.println("Game Over!");
		}
		/*
		 * if(moving == false && stamina < 500) { stamina+=10; if(stamina > 500) {
		 * stamina = 500; } } if(stamina > 0 && moving == true) { speed = 10; d = 0;
		 * if(up == true) { d++; } if(down == true) { d++; } if(left == true) { d++; }
		 * if(right == true) { d++; } if(d == 2) { speed = 10*Math.sqrt(2)/2; } if(up ==
		 * true) { y-=speed; } if(down == true) { y+=speed; } if(left == true) {
		 * x-=speed; } if(right == true) { x+=speed; } stamina--; }
		 * 
		 */
		if (moving == false && stamina < 500) {
			stamina += 10;
			if (stamina > 500) {
				stamina = 500;
			}
		}
		if (stamina > 0 && moving == true) {
			if (snow == true) {
				speed = 1;
			} else {
				speed = 4;
			}
			if (up == true) {
				if (left == true) {
					for (int i = 0; i < speed; i++) {
						if (checkBounds("up", x, y, map) == false) {
							y--;
						}
						if (checkBounds("left", x, y, map) == false) {
							x--;
						}
					}
				} else if (right == true) {
					for (int i = 0; i < speed; i++) {
						if (checkBounds("right", x, y, map) == false) {
							x++;
						}
						if (checkBounds("up", x, y, map) == false) {
							y--;
						}
					}
				} else {
					for (int i = 0; i < speed; i++) {
						if (checkBounds("up", x, y, map) == false) {
							y--;
						}
					}
				}
			} else if (down == true) {
				if (left == true) {
					for (int i = 0; i < speed; i++) {
						if (checkBounds("left", x, y, map) == false) {
							x--;
						}
						if (checkBounds("down", x, y, map) == false) {
							y++;
						}
					}
				} else if (right == true) {
					for (int i = 0; i < speed; i++) {
						if (checkBounds("right", x, y, map) == false) {
							x++;
						}
						if (checkBounds("down", x, y, map) == false) {
							y++;
						}
					}
				} else {
					for (int i = 0; i < speed; i++) {
						if (checkBounds("down", x, y, map) == false) {
							y++;
						}
					}
				}
			} else {
				if (left == true) {
					for (int i = 0; i < speed; i++) {
						if (checkBounds("left", x, y, map) == false) {
							x--;
						}
					}
				} else if (right == true) {
					for (int i = 0; i < speed; i++) {
						if (checkBounds("right", x, y, map) == false) {
							x++;
						}
					}
				}
			}
			stamina--;
		}
	}

	public boolean checkBounds(String dir, int px, int py, int[][] map) {
		if (dir == "up") {
			py--;
			if (py < 55) {
				return true;
			}
			int gridX = (px - 50) / 50;
			int gridXn = (px - 15) / 50;
			int gridY = (py - 55) / 50;
			if (map[gridX][gridY] > 0) {
				return true;
			} else if (map[gridXn][gridY] > 0) {
				return true;
			}
		} else if (dir == "left") {
			px--;
			if (px < 55) {
				return true;
			}
			int gridX = (px - 55) / 50;
			int gridY = (py - 50) / 50;
			int gridYn = (py - 15) / 50;
			if (map[gridX][gridY] > 0) {
				return true;
			} else if (map[gridX][gridYn] > 0) {
				return true;
			}
		} else if (dir == "right") {
			px++;
			px += 50;
			if (px > 1854) {
				return true;
			}
			int gridX = (px - 55) / 50;
			int gridY = (py - 50) / 50;
			int gridYn = (py - 15) / 50;
			if (map[gridX][gridY] > 0) {
				return true;
			} else if (map[gridX][gridYn] > 0) {
				return true;
			}
		} else {
			py++;
			py += 50;
			if (py > 1054) {
				return true;
			}
			int gridX = (px - 50) / 50;
			int gridXn = (px - 15) / 50;
			int gridY = (py - 55) / 50;
			if (map[gridX][gridY] > 0) {
				return true;
			} else if (map[gridXn][gridY] > 0) {
				return true;
			}
		}
		return false;
	}
}
