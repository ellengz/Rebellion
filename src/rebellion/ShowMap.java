package rebellion;
import javax.security.auth.x500.X500PrivateCredential;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ShowMap extends JFrame {

private static final long serialVersionUID = 1L;
   
    public void draw(int map[][])
    {
    	 JPanel grid = new JPanel();
         grid.setLayout(new GridLayout(Params.MAX_MAP_XVALUE, Params.MAX_MAP_YVALUE));       
     

         for (int i = 0; i < Params.MAX_MAP_XVALUE; i++) {
             for (int n = 0; n < Params.MAX_MAP_YVALUE; n++) {
            	 String str = ""+map[i][n];
             	JLabel x = new JLabel(str);
             	 x.setOpaque(true);
             	          	       	
             
                 x.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                 if(map[i][n]==4)
                	 x.setBackground(Color.blue);
                 if(map[i][n]==1)
                	 x.setBackground(Color.GREEN);
                 if(map[i][n]==2)
                	 x.setBackground(Color.RED);
                 if(map[i][n]==3)
                	 x.setBackground(Color.black);
                 if(map[i][n]>30)
                	 x.setBackground(Color.pink);
                 if(map[i][n]>40)
                 	 x.setBackground(Color.ORANGE);  
              
                 grid.add(x);
               }
         }
         JFrame frame = new JFrame("Map");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setPreferredSize(new Dimension(1000, 1000));        
         frame.add(grid);        
         frame.pack();        
         frame.setVisible(true);        
            	
    }
}
