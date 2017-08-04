import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import java.util.Random;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.lang.Math;
import java.util.*;

public class Animation extends JPanel implements MouseMotionListener
{
    private BufferedImage screen;
    private int length, height;
    private MainBall mb; //The black ball the user controls
    private ArrayList<Letter> word = new ArrayList<Letter>(); //Spells: bouncy at the start
    public static boolean game_active = false; //whether or not the timer does anything
    private Random rand = new Random();

    public Animation(int length, int height){
        super();
        game_active = false;
        screen = new BufferedImage(length,height,BufferedImage.TYPE_INT_ARGB);//get the max length and height
        addMouseMotionListener(this);

        boolean[][] p = {{true, false, false},{true, false, false},{true, true, true},{true, false, true},{true, true, true}};
        word.add(new Letter(Color.RED, 60, 60, p)); //b

        p[0][0] = false; p[0][1] = false; p[0][2] = false;
        p[1][0] = false; p[1][1] = false; p[1][2] = false;
        p[2][0] = true; p[2][1] = true; p[2][2] = true;
        p[3][0] = true; p[3][1] = false; p[3][2] = true;
        p[4][0] = true; p[4][1] = true; p[4][2] = true;
        word.add(new Letter(Color.GREEN, 365, 60, p)); //o

        p[0][0] = false; p[0][1] = false; p[0][2] = false;
        p[1][0] = false; p[1][1] = false; p[1][2] = false;
        p[2][0] = true; p[2][1] = false; p[2][2] = true;
        p[3][0] = true; p[3][1] = false; p[3][2] = true;
        p[4][0] = true; p[4][1] = true; p[4][2] = true;
        word.add(new Letter(Color.BLUE, 670, 60, p)); //u

        p[0][0] = false; p[0][1] = false; p[0][2] = false;
        p[1][0] = false; p[1][1] = false; p[1][2] = false;
        p[2][0] = true; p[2][1] = true; p[2][2] = true;
        p[3][0] = true; p[3][1] = false; p[3][2] = true;
        p[4][0] = true; p[4][1] = false; p[4][2] = true;
        word.add(new Letter(Color.YELLOW, 975, 60, p)); //n

        p[0][0] = false; p[0][1] = false; p[0][2] = false;
        p[1][0] = false; p[1][1] = false; p[1][2] = false;
        p[2][0] = true; p[2][1] = true; p[2][2] = true;
        p[3][0] = true; p[3][1] = false; p[3][2] = false;
        p[4][0] = true; p[4][1] = true; p[4][2] = true;
        word.add(new Letter(Color.PINK, 1280, 60, p)); //c

        p[0][0] = false; p[0][1] = false; p[0][2] = false;
        p[1][0] = true; p[1][1] = false; p[1][2] = true;
        p[2][0] = true; p[2][1] = true; p[2][2] = true;
        p[3][0] = false; p[3][1] = false; p[3][2] = true;
        p[4][0] = true; p[4][1] = true; p[4][2] = true;
        word.add(new Letter(Color.ORANGE, 1585, 60, p)); //y

        mb = new MainBall(length/2 - 50, height/2 + 150);
    }

    public void addBall(){
      if(!game_active) return; //Has the game started?
      double ux, uy, mag; //the unit vector and the magnitude, randomly assigned
      setBackground(get_random_color()); //Change the color of the background
      int x, y, vx, vy; //The velocity and location that will be set into the ball
      Color c = get_random_color();
      while(c == Color.WHITE){
        c = get_random_color();
      }
      do{
        vx = rand.nextInt()%10; //there is a maximum velocity
        vy = rand.nextInt()%10;
        //While statment prevents ball from being created in same direction as the mainball or stationary
      }while(vx*vy == 0 || vx*mb.get_velocity_x() > 0 || vy*mb.get_velocity_y() > 0);
      mag = Math.sqrt(vx*vx + vy*vy);
      ux = vx/mag;
      uy = vy/mag;
      //places near mainball in the right direction based on unit vectors
      x = (int)Math.round(115.0*(ux) + .5) + mb.v.x;
      y = (int)Math.round(115.0*(uy) + .5) + mb.v.y;
      new Ball(c, x, y, vx, vy); //creates the new ball
    }

    public Dimension getPrefferedSize(){
        return new Dimension(length,height);
    }

    private void update_size(){
      length = getWidth();
      height = getHeight();
      if(length > 0 && height > 0){
        screen = new BufferedImage(length, height, BufferedImage.TYPE_INT_ARGB);
      }
    }

    private Color get_random_color(){
      return new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
    }

    //goes through and updates the graphics on all the balls
    public void update(){
      Graphics g = screen.getGraphics();
      for(Ball b : Ball.balls){
        b.update(g);
      }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        update_size();
        update();
        g.drawImage(screen,0,0,null);
        g.dispose();
    }

    //goes through and moves the balls based on the clock
    public void tick(){
      for(Ball b : Ball.balls){
        b.tick(length, height);
      }
    }

//Mouse listener stuff:
    public void mouseMoved(MouseEvent e) {
      if(game_active){
        mb.change_location(e.getX() - 50, e.getY() - 50); //The -50 ensures the mouse is in center of ball
      }
    }

    public void mouseDragged(MouseEvent e) {
      if(game_active){
          mb.change_location(e.getX() - 50, e.getY() - 50);
          return;
      }
      if(e.getX() > length/2 - 50 && e.getX() < length/2 + 150 && e.getY() > height/2 + 50 && e.getY() < height/2 + 250){
        mb.change_location(e.getX() - 50, e.getY() - 50);
        for(Letter l : word){
          l.start();
        }
        game_active = true; //starts the game
      }
    }
}
