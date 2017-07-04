import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.Random;

public class MainBall extends Ball{
  private Vector last_v;
  public MainBall(int x, int y){
    super(Color.BLACK, x, y, 0, 0);
    last_v = new Vector(v.x, v.y, 0, 0);
  }

  public void change_location(int x, int y){
    last_v.x = v.x;
    last_v.y = v.y;
    v.x = x;
    v.y = y;
  }
  public int get_velocity_x(){
    return v.x - last_v.x;
  }
  public int get_velocity_y(){
    return v.y - last_v.y;
  }
  public void update(Graphics g){
    super.update(g);
    g.setColor(Color.RED);
    g.setFont(new Font("SansSerit", Font.PLAIN, 25));
    if(Animation.game_active){
      g.drawString("" + (Ball.balls.size() - 49), v.x + 50, v.y + 50);
    }else{
      g.drawString("Drag", v.x + 20, v.y + 50);
    }
  }
}