import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Flappybird extends JPanel implements ActionListener,KeyListener,MouseListener {
    int boardHeight=640;
    int boardWidth=360;

    //images
        Image backgrounImg;
        Image birdImg;
        Image toppipeImg;
        Image bottompipeImg;

     //bird class
     int birdx =boardWidth/8;
     int birdy=boardHeight/2;
     int birdWidth=34;
     int birdHeight=24;

     class Bird{
        int x=birdx;
        int y=birdy;
        int width= birdWidth;
        int height=birdHeight;
        Image img;

        Bird(Image img){
            this.img=img;
        }
     } 
     //pipesi
     int pipex=boardWidth;
     int pipey=0;
     int pipewidth=64;
     int pipeheight=512;
      
     class Pipe{
        int x=pipex;
        int y=pipey;
        int width=pipewidth;
        int height=pipeheight;
        Image img;
        boolean passed=false;

        Pipe(Image img){
            this.img=img;
        }
     }
     //game logic
     Bird bird;
     int velocityX=-4;
     int velocityY=0;
     int gravity=1;

     ArrayList<Pipe>pipes;
     Random random=new Random();
     
     Timer gameLoop;
     Timer placepipesTimer;
      boolean gameOver=false;
      double score=0;

    Flappybird(){
        setPreferredSize(new Dimension(boardWidth,boardHeight));
        //setBackground(Color.blue);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);

        backgrounImg=new ImageIcon(getClass().getResource("./ground.jpg")).getImage();
        birdImg=new ImageIcon(getClass().getResource("./flappybird.jpg")).getImage();
        toppipeImg=new ImageIcon(getClass().getResource("./up.jpg")).getImage();
        bottompipeImg=new ImageIcon(getClass().getResource("./down.jpg")).getImage();
       
        //bird
        bird=new Bird(birdImg);
        pipes=new ArrayList<Pipe>();

        //pipe timer
        placepipesTimer=new Timer(1500,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                placepipes();
            }
        });
        placepipesTimer.start();


        //game timer
        gameLoop=new Timer(1000/60,this);
        gameLoop.start();
    }
    public void placepipes(){
        int randomPipeY=(int)(pipey -pipeheight/4-Math.random()*(pipeheight/2));
        int openingSpace=boardHeight/4;

        Pipe toppipe=new Pipe(toppipeImg);
        toppipe.y=randomPipeY;
        pipes.add(toppipe);

        Pipe bottompipe=new Pipe(bottompipeImg);
        bottompipe.y=toppipe.y+pipeheight+openingSpace;
        pipes.add(bottompipe);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        g.drawImage(backgrounImg, 0, 0, boardWidth,boardHeight,null);
        g.drawImage(bird.img, bird.x, bird.y,bird.width, bird.height, null);
        for(int i=0;i<pipes.size();i++){
            Pipe pipe=pipes.get(i);
            g.drawImage(pipe.img,pipe.x ,pipe.y, pipe.width, pipe.height, null);
        }
    
    //score
    g.setColor(Color.white);
    g.setFont(new Font("Arial",Font.PLAIN,32));
    if(gameOver){
        g.drawString("Game Over:"+String.valueOf((int)score),10,35);
    }
    else
    {
        g.drawString(String.valueOf((int)score),10,35);
    
    }
    }
    public void move(){
        //bird
        velocityY+=gravity;
        bird.y += velocityY;
        bird.y= Math.max(bird.y,0);
        //pipes
        for(int i=0;i<pipes.size();i++){
        Pipe pipe=pipes.get(i);
        pipe.x+=velocityX;

        if(!pipe.passed&&bird.x>pipe.x+pipe.width){
            score+=0.5;
            pipe.passed=true;
        }
        if(collision(bird,pipe)){
            gameOver=true;
        
        }
    }
    if(bird.y>boardHeight){
        gameOver=true;
    }
    }
    boolean collision(Bird a,Pipe b){
        return a.x<b.x+b.width &&
               a.x+a.width>b.x &&
               a.y<b.y+b.height &&
               a.y+a.height>b.y;
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
     move();
     repaint();
     if(gameOver){
     placepipesTimer.stop();
     gameLoop.stop();
   }
    }
  
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
        velocityY=-12;
        if(gameOver){
            bird.y=birdy;
            velocityY=0;
            pipes.clear();
            gameOver=false;
            score=0;
            gameLoop.start();
            placepipesTimer.start();
        }
    }
}
    @Override
    public void keyTyped(KeyEvent e) {
       
    }
    @Override
    public void keyReleased(KeyEvent e) {}
    //mouse events
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton()==MouseEvent.MOUSE_CLICKED){
            velocityY=-12;
            if(gameOver){
                bird.y=birdy;
                velocityY=0;
                pipes.clear();
                gameOver=false;
                score=0;
                gameLoop.start();
                placepipesTimer.start();
            }
        }    
     }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) {}
    
}
