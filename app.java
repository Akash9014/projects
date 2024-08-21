import java.awt.Image;

import javax.swing.*;
public class app{
    public static void main(String[] args) throws Exception{
        int boardWidth=360;
        int boardHeight=640;

        
        JFrame f=new JFrame("FlappyBird");
        //f.setVisible(true);
        f.setSize(boardWidth,boardHeight);
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Flappybird flappybird=new Flappybird();
        f.add(flappybird);
        f.pack();
        flappybird.requestFocus();  
        f.setVisible(true);
    }
}       