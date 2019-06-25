package flappyfin;



import java.awt.*;

import java.awt.Font;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;

import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;

import java.util.List;

import java.util.Random;



import javax.imageio.ImageIO;

import javax.swing.ImageIcon;

import javax.swing.JFrame;

import javax.swing.JLabel;

import javax.swing.Timer;



public class FlappyFin implements ActionListener, KeyListener, MouseListener {

	public static FlappyFin flappyfin;
	//sets the size of the panel
	public final int WIDTH = 500, HEIGHT = 500;
	public Renderer renderer;
	public Rectangle fish;
	public int score;
	//creates all the images for the trash and the fish
	BufferedImage imgCola; 
	BufferedImage imgBag;
	BufferedImage imgRing;
	BufferedImage imgTire ;
	BufferedImage imgWine ;
	BufferedImage imgBubble;
	BufferedImage imgOscar;
	BufferedImage imgChips;
	BufferedImage imgSeaweed;
	BufferedImage imgCoral;
	BufferedImage imgStar;
	BufferedImage imgSponge;
	int bubbleHeight;
	int currentBKG;
	public int ticks, yMotion;
	
	public boolean gameOver, started;

	public int motionR, motionL, motionU, motionD;
	public ArrayList<Rectangle> trash;
	//array list of images
	List<Image> trashImgs = new ArrayList<>();
	public BufferedImage imgFish;
	public int level = 45;

	public FlappyFin() {

		JFrame jframe = new JFrame();
		//sets the speed of the fish
		Timer timer = new Timer(level, this);
		renderer = new Renderer(this);
		jframe.add(renderer);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//sets the size of the panel
		jframe.setSize(WIDTH,HEIGHT);
		//allows panel to be resized
		jframe.setResizable(true);
		//mouse and keys can be used
		jframe.addKeyListener(this);
		jframe.addMouseListener(this);

		try {
			//creates file paths for the image
			imgFish = ImageIO.read(new File("/Users/shirleysu/Downloads/fish.png"));
			imgBubble = ImageIO.read(new File("/Users/shirleysu/Downloads/bubble.png"));
			imgCola = ImageIO.read(new File("/Users/shirleysu/Downloads/FinalGraphics/Cola.png"));
			imgBag = ImageIO.read(new File("/Users/shirleysu/Downloads/FinalGraphics/Bag.png"));
			imgRing = ImageIO.read(new File("/Users/shirleysu/Downloads/FinalGraphics/Rings.png"));
			imgTire = ImageIO.read(new File("/Users/shirleysu/Downloads/FinalGraphics/Tire.png"));
			imgWine = ImageIO.read(new File("/Users/shirleysu/Downloads/FinalGraphics/Wine.png"));
			imgOscar = ImageIO.read(new File("/Users/shirleysu/Downloads/Oscar.png"));
			imgChips = ImageIO.read(new File("/Users/shirleysu/Downloads/chips.png"));
			imgSeaweed = ImageIO.read(new File("/Users/shirleysu/Downloads/seaweed.png"));
			imgCoral = ImageIO.read(new File("/Users/shirleysu/Downloads/coral.png"));
			imgStar = ImageIO.read(new File("/Users/shirleysu/Downloads/star.png"));
			imgSponge = ImageIO.read(new File("/Users/shirleysu/Downloads/sponge.png"));

		} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}
		//panel can be seen
		jframe.setVisible(true);
		//initalizes the rectangle and array of trash
		fish = new Rectangle(WIDTH / 2 - 5, HEIGHT / 2 - 5, imgFish.getWidth(), imgFish.getHeight());
		trash = new ArrayList<Rectangle>();

		addTrash(true);
		addTrash(true);
		addTrash(true);
		addTrash(true);
		//starts timer
		timer.start();



	}



	public void addTrash(boolean start) {
		
		int space = 300;
		int width = 100;
		//randomly generates the height of the trash
		Random ran = new Random();
		int height = 50 + ran.nextInt(200);
		//replace these rectangles with trash
		if (start) {
			//adds two columns
			trash.add(new Rectangle(WIDTH + width + trash.size() * 200, HEIGHT - height - 120, width, height));
			trash.add(new Rectangle(WIDTH + width + (trash.size() - 1) * 200, 0,width, HEIGHT   - height - space));
			
		}

		else {
			//adds columns of trash for beginning of game

			trash.add(new Rectangle(trash.get(trash.size()-1).x + 600, HEIGHT - height - 120, width, height));

			trash.add(new Rectangle(trash.get(trash.size()-1).x,0, width, HEIGHT- height - space));

		}
		
		trashImgs.add(getTrash());
		trashImgs.add(getTrash());
		
		

	}



	public void paintTrash(Graphics g, Rectangle trash) {

		//sets color of column
		g.setColor(Color.green.darker());
		g.setColor(Color.black);
		//draws rectangle based on the dimensions of the trash
		g.drawRect(trash.x, trash.y, trash.width, trash.height);
		g.fillRect(trash.x, trash.y, trash.width, trash.height);
		
	}

	public void jump() {

		if(gameOver) {
			//if game is over, fish is created
			fish = new Rectangle(WIDTH / 2 - 15, HEIGHT / 2 - 15, 10, 10);
			//trash is cleared
			trash.clear();
			//motions become 0(no motion)
			yMotion = 0;
			//score resets to zero
			score = 0;

			addTrash(true);

			addTrash(true);

			addTrash(true);

			addTrash(true);

			gameOver = false;



		}

		if(!started) {

			started = true;

		}

		else if(!gameOver) {
			//lowers motion once mouse is clicked
			if(yMotion > 0) {

				yMotion = 0;

			}

			yMotion -= 10;

		}

	}

	@Override

	public void actionPerformed(ActionEvent e) {

		// TODO Auto-generated method stub

		int speed = 20;

		ticks++;

		if(started) {


			for(int i = 0; i < trash.size(); i++) {

				Rectangle trashes = trash.get(i);

				trashes.x -= speed;

			}

			if(ticks % 2 == 0 && yMotion < 15) {

				yMotion += 2;

			}

			for( int i = 0; i < trash.size(); i++) {

				Rectangle trashes = trash.get(i);

				if(trashes.x + trashes.width < 0) {
					//removes trash as it goes across the screen
					trash.remove(trashes);
					trashImgs.remove(trashImgs.get(i));

					if(trashes.y == 0) {

						addTrash(false);

					}

				}

			}

			fish.y += yMotion;

			for( Rectangle trashes : trash) {
				//checks if fish is not touching trash
				if(trashes.y == 0 && fish.x + fish.width / 2 > trashes.width / 2 - 10 && fish.x + fish.width < trashes.x + trashes.width / 2 ) {

					score++;

				}

				if(trashes.intersects(fish)) {

					gameOver = true;

					if(fish.x <= trashes.x) {

						fish.x = trashes.x - fish.width;

					}

					else {

						if(trashes.y != 0) {

							fish.y = trashes.y -fish.height;

						}

						else if(fish.y < trashes.height) {

							fish.y = trashes.height;

						}

					}



				}

			}

			if(fish.y > HEIGHT - 120 || fish.y < 0) {
				//if the fish goes above or below the screen, it die
				gameOver = true;

			}

			if(fish.y + yMotion >= HEIGHT - 120) {
				
				fish.y = HEIGHT - 120 - fish.height;

				gameOver = true;

			}

		}

		renderer.repaint(); 
	

	}



	public void repaint(Graphics g) {

		// TODO Auto-generated method stub

		g.setColor(Color.cyan);
		g.fillRect(0, 0 ,WIDTH, HEIGHT);

		//colors the ocean floor
		g.setColor(Color.ORANGE);
		g.fillRect(0, HEIGHT - 150, WIDTH, 150);
		
		//Image[] backGroundStuff = {imgStarfish, imgCoral, imgSeaweed};

		g.drawImage(imgFish, fish.x, fish.y, null);
		g.setColor(Color.black);
		
		for(int i = 0; i < trash.size(); i++) {
			Rectangle t = trash.get(i);
			Image img = trashImgs.get(i);
			//draws the image returned from trash.get method
			g.drawImage(img, t.x, t.y,t.width, t.height, null);
			g.drawImage(imgSeaweed, t.x - 200, WIDTH -120, null);
			g.drawImage(imgCoral, t.x - 150, WIDTH - 100, null);
			g.drawImage(imgStar, t.x - 100, WIDTH - 150 , null);
			g.drawImage(imgSponge,t.x - 75, WIDTH - 120, null);
			g.drawImage(imgBubble, t.x - 100, bubbleHeight , null);

		}

		g.setColor(Color.GRAY);

		//size and font of click to start
		g.setFont(new Font("Arial", 1, 50));
		

		if(!started) {
			//if the game hasn't started yet, the title "Click to Start" will appear
			g.drawString("Click to Start!", 50, HEIGHT / 2 - 50);

		}

		if(gameOver) {
			//if the game is over, the title "Game Over" will appear
			g.drawString("Game over", 100, HEIGHT / 2 - 50);

		}

		if(!gameOver && started) {
			
			g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);

		}

	}
	private Image getTrash() {

		//creates an array of the image
		BufferedImage[] imageArr = {imgCola, imgBag, imgRing, imgOscar, imgChips}; 
		//randomly selects the images 
		int random = (int) (Math.random() * imageArr.length) ;
		System.out.println(random);
		//returns the image
		bubbleHeight = (int)(Math.random() * 200);
		
		return (BufferedImage) imageArr[random];
	}



	public static void main(String[] args) {
		//creates instance of flappy fin
		flappyfin = new FlappyFin();

	}

	@Override

	public void keyTyped(KeyEvent e) {

		// TODO Auto-generated method stub

	}

	@Override

	public void keyPressed(KeyEvent e) {

		// TODO Auto-generated method stub

	}

	@Override

	public void keyReleased(KeyEvent e) {

		// TODO Auto-generated method stub
		//if space key is pressed, it will then call the jump method
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			
			jump();

		}

	}



	@Override

	public void mouseClicked(MouseEvent e) {

		// TODO Auto-generated method stub
		//if mouse is clicked it will call the jump method
		jump();

	}

	@Override

	public void mousePressed(MouseEvent e) {

		// TODO Auto-generated method stub

	}

	@Override

	public void mouseReleased(MouseEvent e) {

		// TODO Auto-generated method stub

	}

	@Override

	public void mouseEntered(MouseEvent e) {

		// TODO Auto-generated method stub



	}

	@Override

	public void mouseExited(MouseEvent e) {

		// TODO Auto-generated method stub

	}



}