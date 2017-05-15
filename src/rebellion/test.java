package rebellion;
import javax.security.auth.x500.X500PrivateCredential;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class test extends JFrame {

private static final long serialVersionUID = 1L;
static int[][] map = new int[Params.MAX_MAP_XVALUE][Params.MAX_MAP_YVALUE];
Graphics g;
private Random randomGenerator = new Random();
    public static void main(String[] args) {
      //  ImageIcon sky = new ImageIcon ("/Users/pro/Desktop/sky.png");
        for(int i =0;i<10;i++)
        {
    //	System.out.println("before");
      //  try{ Thread.sleep(1000);}catch(Exception e){}
      //  System.out.println("after");
       
     //  new test().fillMap();
     //  new test().draw(map);
        }
    }
    public void fillMap()
    {
    	int x= 0, y= 0, z =9, a = 0;
    	//int index = randomGenerator.nextInt(5);
    	  for (int i = 0; i < Params.MAX_MAP_XVALUE; i++) {
              for (int n = 0; n < Params.MAX_MAP_YVALUE; n++)
              {
            	//  map[i][n] = randomGenerator.nextInt(5);
              }
    	  }
    //	  draw(map);+
    	  System.out.println(x+" "+y+" "+z+" "+a);
    	 
    }
    public void draw(int map[][])
    {
    	 JPanel grid = new JPanel();
         grid.setLayout(new GridLayout(Params.MAX_MAP_XVALUE, Params.MAX_MAP_YVALUE));
       
     

         for (int i = 0; i < Params.MAX_MAP_XVALUE; i++) {
             for (int n = 0; n < Params.MAX_MAP_YVALUE; n++) {
            	 String str = ""+map[i][n];
             	JLabel x = new JLabel(str);
             	 x.setOpaque(true);
             	if(map[i][n]>40)
                         	 x.setForeground(Color.RED);            	       	
             
                 x.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                 if(map[i][n]==4)
                	 x.setBackground(Color.blue);
                 if(map[i][n]==1)
                	 x.setBackground(Color.GREEN);
                 if(map[i][n]==2)
                	 x.setBackground(Color.RED);
                 if(map[i][n]==3)
                	 x.setBackground(Color.black);
              
                 grid.add(x);
               //  grid.add(new JLabel("ab"));
             }
         }
         JFrame frame = new JFrame("Map");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setPreferredSize(new Dimension(1000, 1000));
        
         frame.add(grid);
        
         frame.pack();
        
         frame.setVisible(true);
      //   fillMap();
         
        
         frame.repaint();
        
         
    	
    }
}