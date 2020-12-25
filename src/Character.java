import java.awt.Color;
import java.awt.Graphics;

public class Character {
	int TL, BR;
	int x, y;
	double speed = 10;
	int d;
	int stamina = 500;
	boolean up, down, left, right, moving;
	Character(int x, int y){
		this.x = x*50 + 55;
		this.y = y*50 + 55;
	}
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(x, y, 40, 40);
	}
	public void update(int[][] map) {
		int gridX, gridY;
		/*
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
		
		*/
		if(moving == false && stamina < 500) {
			stamina +=10;
			if(stamina > 500) {
				stamina = 500;
			}
		}
		if(stamina > 0 && moving == true) {
			speed = stamina/75+3;
			if(up == true) {
				if(left == true) {
					x-=(speed/Math.sqrt(2));
					y-=(speed/Math.sqrt(2));
					gridX = (x-55)/50;
					gridY = (y-55)/50;
					while(map[gridX][gridY] > 0) {
						x++;
						y++;
						gridX = (x-55)/50;
						gridY = (y-55)/50;
					}
				} else if(right == true) {
					x+=(speed/Math.sqrt(2));
					y-=(speed/Math.sqrt(2));
					gridX = (x-55)/50;
					gridY = (y-55)/50;
				} else {
					y-=speed;
					gridX = (x-55)/50;
					gridY = (y-55)/50;
				}
			} else if(down == true) {
				if(left == true) {
					x-=(speed/Math.sqrt(2));
					y+=(speed/Math.sqrt(2));
					gridX = (x-55)/50;
					gridY = (y-55)/50;
				} else if(right == true) {
					x+=(speed/Math.sqrt(2));
					y+=(speed/Math.sqrt(2));
					gridX = (x-55)/50;
					gridY = (y-55)/50;
				} else {
					y+=speed;
					gridX = (x-55)/50;
					gridY = (y-55)/50;
				}
			} else {
				if(left == true) {
					x-=speed;
				} else if(right == true) {
					x+=speed;
				}
			}
			stamina--;
		}
	}
}
