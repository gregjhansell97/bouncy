import javax.swing.JFrame;
//import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Driver{
  public static final int WIDTH = 1925, HEIGHT = 1100;
  public static void main(String [] args){

    JFrame frame = new JFrame("Bouncy"); //title of the page
    frame.setSize(WIDTH,HEIGHT);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Animation a = new Animation(WIDTH,HEIGHT);
    frame.add(a);
    frame.setVisible(true);
    
    //Timer adds a new ball every 10 seconds or so
    javax.swing.Timer level_creator  = new javax.swing.Timer(6000, new ActionListener(){
      public void actionPerformed(ActionEvent e){
        a.addBall();
      }
    });

    //Timer updates all the balls and repains the window
    javax.swing.Timer clock = new javax.swing.Timer(15, new ActionListener(){
      public void actionPerformed(ActionEvent e){
        if(!Ball.hold){
          a.tick();
          a.repaint();
        }
      }
    });

    clock.start();
    level_creator.start();

  }
}
