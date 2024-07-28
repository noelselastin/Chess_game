//A chess game with player playing as white and computer playing as black
/*Before executing the program, create a folder where you have chessout.java, move all the .png images to it, and whereever the images' location is
*referenced in this program, change the src/game_images to your folder name*/
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

//JPanel class
class chess extends JPanel implements ActionListener
{
    //Initialising class variables
    Image black_square_image,blue_square_image,light_purple_square_image,dark_purple_square_image;

    char current_position[][];
    char chkpos[][];
    char turn;

    int move_to_x;int move_to_y;
    int mouse_click_x;int mouse_click_y;

    int move_from_x;int move_from_y;

    int movable_squares[][];
    int movable_squares2[][];

    int movesahead[];
    int blackscore;

    int i1;int j1;

    int state;
    int end1;
    int black_in_check;
    int white_king_attacked;
    int checktemp;

    Timer timer;

    JButton play_button;
     chess()
     {
            addMouseListener(new mouse_input_handler());

            //Setting up the JPanel
            setPreferredSize(new Dimension(640,640));  //Size of Jpanel
            setVisible(true);  //Make the Jpanel visible
            setFocusable(true);
            setLayout(null);  //Setting the layout of Jpanel

            //Defining the 2-D arrays
            current_position=new char[8][8];
            movable_squares=new int[8][8];
            movable_squares2=new int[8][8];
            chkpos=new char[8][8];
            //Setting up the initial position
            //Making all squares empty initially
            int i,j;
            for(i=0;i<8;i++)
            for(j=0;j<8;j++)
            current_position[i][j]='s';

            //Setting pieces on squares that are necessary
            current_position[0][0]='r';current_position[1][0]='n';current_position[2][0]='b';current_position[3][0]='q';current_position[4][0]='k';current_position[5][0]='b';current_position[6][0]='n';current_position[7][0]='r';
            current_position[0][1]='p';current_position[1][1]='p';current_position[2][1]='p';current_position[3][1]='p';current_position[4][1]='p';current_position[5][1]='p';current_position[6][1]='p';current_position[7][1]='p';
            current_position[0][6]='P';current_position[1][6]='P';current_position[2][6]='P';current_position[3][6]='P';current_position[4][6]='P';current_position[5][6]='P';current_position[6][6]='P';current_position[7][6]='P';
            current_position[0][7]='R';current_position[1][7]='N';current_position[2][7]='B';current_position[3][7]='Q';current_position[4][7]='K';current_position[5][7]='B';current_position[6][7]='N';current_position[7][7]='R';

            turn='w';  //turn is made to white as white starts first
            move_from_x=9;move_from_y=9;  //x and y coordinates of moving from square are initialised to 9, which means no square. Because squares exist from 0 to 7 in x and y
            state=1;  //state 1 is home page of the game
            black_in_check=0;  //set to 0 means that black is not in check
            white_king_attacked=0;
            checktemp=0;
            end1=0;

            Color home_page_purple = new Color(186,85,211);  //Creating color for home page
            play_button=new JButton("Play");  //Creating play button in the home page
            play_button.setBounds(250,150,150,50); //Setting coordinates and dimensions of play button
            play_button.addActionListener(this);
            this.add(play_button);
            setBackground(home_page_purple);

            load();
     }
     public void load()
    {
        black_square_image=(new ImageIcon("src/game_images/black.png")).getImage();
        blue_square_image=(new ImageIcon("src/game_images/blue.png")).getImage();
        light_purple_square_image=(new ImageIcon("src/game_images/lpurple.png")).getImage();
        dark_purple_square_image=(new ImageIcon("src/game_images/dpurple.png")).getImage();

        int i,j;

        //Setting all elements of movable_squares matrix to 0 which means that there are no movable squares
        for(i=0;i<8;i++)
        for(j=0;j<8;j++)
        movable_squares[i][j]=0;

        timer=new Timer(50,this);

    }

    //When the button is pressed, this method gets executed
    public void actionPerformed(ActionEvent e)
     {
        if(e.getSource()==play_button)
        state=2;
        repaint();  //Calls paintComponent()
     }

   //This method will keep on running, from when repaint() is called
    public void paintComponent(Graphics graphics)
     {
         super.paintComponent(graphics);

         int i,j;
        if(state==2)  //state 2 means in-game(after pressing play button)
         {
            setBackground(Color.LIGHT_GRAY);  //for the light squares
            play_button.setVisible(false);  //removing the play button as it's not needed after it's press(while in-game)

         for(i=0;i<8;i++)
         for(j=0;j<8;j++)
         {
             if((i+1)%2!=0)
             {
                if((j+1)%2==0)
                {
                graphics.drawImage(black_square_image,(i*80),(j*80),80,80,this);  //painting dark squares
                }
             }
             else
             {
                if((j+1)%2!=0)
                {
                   graphics.drawImage(black_square_image,(i*80),(j*80),80,80,this);  //painting dark squares
                }
             }
         }

        if(move_from_x!=9)
        {
            for(i=0;i<8;i++)
            for(j=0;j<8;j++)
            {
                //Checking for movable squares of the selected piece and highlighting them with purple colour
                if(movable_squares[i][j]==1)
                {
                if((i+1)%2!=0)
                {
                   if((j+1)%2==0)
                   {
                graphics.drawImage(dark_purple_square_image,(i*80),(j*80),80,80,this);  //movable squares on the dark squares with dark purple
                   }
                   else
                   {
                graphics.drawImage(light_purple_square_image,(i*80),(j*80),80,80,this);  //movable squares on the light squares with light purple
                   }
                }
                else
                {
                   if((j+1)%2!=0)
                   {
                graphics.drawImage(dark_purple_square_image,(i*80),(j*80),80,80,this);
                   }
                   else
                   {

                graphics.drawImage(light_purple_square_image,(i*80),(j*80),80,80,this);
                   }
                }
                }
                else if(movable_squares[i][j]==2)
                graphics.drawImage(blue_square_image,(i*80),(j*80),80,80,this);
            }
        }


         //Displaying the pieces according to the current positon matrix.
         char temp;
         for(i=0;i<8;i++)
         for(j=0;j<8;j++)
         {
             temp=current_position[i][j];
             //PNG images of the pieces are stored in a directory. Those png images are converted to Image Icons using ImageIcon() from java swing. Those Image Icons are converted to Image using getImage() and then using drawImage(), those images are displayed.
              if(temp=='k')
              graphics.drawImage(((new ImageIcon("src/game_images/bkp.png")).getImage()),(i*80),(j*80),80,80,this);
              if(temp=='q')
              graphics.drawImage(((new ImageIcon("src/game_images/bqp.png")).getImage()),(i*80),(j*80),80,80,this);
              if(temp=='r')
              graphics.drawImage(((new ImageIcon("src/game_images/brp.png")).getImage()),(i*80),(j*80),80,80,this);
              if(temp=='b')
              graphics.drawImage(((new ImageIcon("src/game_images/bbp.png")).getImage()),(i*80),(j*80),80,80,this);
              if(temp=='n')
              graphics.drawImage(((new ImageIcon("src/game_images/bnp.png")).getImage()),(i*80),(j*80),80,80,this);
              if(temp=='p')
              graphics.drawImage(((new ImageIcon("src/game_images/bpp.png")).getImage()),(i*80),(j*80),80,80,this);
              if(temp=='K')
              graphics.drawImage(((new ImageIcon("src/game_images/wkp.png")).getImage()),(i*80),(j*80),80,80,this);
              if(temp=='Q')
              graphics.drawImage(((new ImageIcon("src/game_images/wqp.png")).getImage()),(i*80),(j*80),80,80,this);
              if(temp=='R')
              graphics.drawImage(((new ImageIcon("src/game_images/wrp.png")).getImage()),(i*80),(j*80),80,80,this);
              if(temp=='B')
              graphics.drawImage(((new ImageIcon("src/game_images/wbp.png")).getImage()),(i*80),(j*80),80,80,this);
              if(temp=='N')
              graphics.drawImage(((new ImageIcon("src/game_images/wnp.png")).getImage()),(i*80),(j*80),80,80,this);
              if(temp=='P')
              graphics.drawImage(((new ImageIcon("src/game_images/wpp.png")).getImage()),(i*80),(j*80),80,80,this);
         }
        }
        if(state==3)  //state 3 means that black is checkmated
        {
             if(black_in_check==1)
             {
                graphics.setFont(new Font("Serif", Font.ITALIC, 36));
                graphics.setColor(new Color(0,153,0));
                graphics.drawString("Checkmate! You Won!",150,320);
            }
        }

     }
     public int[][] setMovableSquares2(char current_position2[][],int movable_squares2[][],int move_from_x2,int move_from_y2,int turn)
    {
        int i,j;
         char piece=current_position2[move_from_x2][move_from_y2];
         char temppiece;
         if(piece!='s')  //to check if the selected square is not empty
         {
             if(turn=='w')
             {
             if(piece=='P')
             {
                 //move
                 if(current_position2[move_from_x2][move_from_y2-1]=='s')
                movable_squares2[move_from_x2][move_from_y2-1]=1;
                if(move_from_y2==6)
                if(current_position2[move_from_x2][move_from_y2-2]=='s'&&current_position2[move_from_x2][move_from_y2-1]=='s')
                movable_squares2[move_from_x2][move_from_y2-2]=1;
                //capture
                if(move_from_x2==0)
                {
                    if(current_position2[move_from_x2+1][move_from_y2-1]=='p'||current_position2[move_from_x2+1][move_from_y2-1]=='r'||current_position2[move_from_x2+1][move_from_y2-1]=='n'||current_position2[move_from_x2+1][move_from_y2-1]=='b'||current_position2[move_from_x2+1][move_from_y2-1]=='q'||current_position2[move_from_x2+1][move_from_y2-1]=='k')
                    movable_squares2[move_from_x2+1][move_from_y2-1]=1;
                }
                if(move_from_x2==7)
                {
                    if(current_position2[move_from_x2-1][move_from_y2-1]=='p'||current_position2[move_from_x2-1][move_from_y2-1]=='r'||current_position2[move_from_x2-1][move_from_y2-1]=='n'||current_position2[move_from_x2-1][move_from_y2-1]=='b'||current_position2[move_from_x2-1][move_from_y2-1]=='q'||current_position2[move_from_x2-1][move_from_y2-1]=='k')
                    movable_squares2[move_from_x2-1][move_from_y2-1]=1;
                }
                if(move_from_x2>0&&move_from_x2<7)
                {
                    if(current_position2[move_from_x2+1][move_from_y2-1]=='p'||current_position2[move_from_x2+1][move_from_y2-1]=='r'||current_position2[move_from_x2+1][move_from_y2-1]=='n'||current_position2[move_from_x2+1][move_from_y2-1]=='b'||current_position2[move_from_x2+1][move_from_y2-1]=='q'||current_position2[move_from_x2+1][move_from_y2-1]=='k')
                    movable_squares2[move_from_x2+1][move_from_y2-1]=1;
                    if(current_position2[move_from_x2-1][move_from_y2-1]=='p'||current_position2[move_from_x2-1][move_from_y2-1]=='r'||current_position2[move_from_x2-1][move_from_y2-1]=='n'||current_position2[move_from_x2-1][move_from_y2-1]=='b'||current_position2[move_from_x2-1][move_from_y2-1]=='q'||current_position2[move_from_x2-1][move_from_y2-1]=='k')
                    movable_squares2[move_from_x2-1][move_from_y2-1]=1;
                }
             }
             else if(piece=='R')
             {
                  //upward
                  for(j=move_from_y2-1;j>=0;j--)
                  {
                      temppiece=current_position2[move_from_x2][j];
                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                    movable_squares2[move_from_x2][j]=1;
                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                  }
                  //downward
                  for(j=move_from_y2+1;j<=7;j++)
                  {
                      temppiece=current_position2[move_from_x2][j];
                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                    movable_squares2[move_from_x2][j]=1;
                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                  }
                  //right
                  for(i=move_from_x2+1;i<=7;i++)
                  {
                    temppiece=current_position2[i][move_from_y2];
                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                    movable_squares2[i][move_from_y2]=1;
                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                  }
                   //left
                   for(i=move_from_x2-1;i>=0;i--)
                   {
                     temppiece=current_position2[i][move_from_y2];
                     if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                     {
                         break;
                     }
                     movable_squares2[i][move_from_y2]=1;
                     if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                     {
                         break;
                     }
                   }
             }
             else if(piece=='B')
             {

                 //upright
                 for(i=move_from_x2+1,j=move_from_y2-1;i<=7&&j>=0;i++,j--)
                 {
                    temppiece=current_position2[i][j];
                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                    movable_squares2[i][j]=1;
                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                 }
                 //upleft
                 for(i=move_from_x2-1,j=move_from_y2-1;i>=0&&j>=0;i--,j--)
                 {
                    temppiece=current_position2[i][j];
                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                    movable_squares2[i][j]=1;
                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                 }
                 //downright
                 for(i=move_from_x2+1,j=move_from_y2+1;i<=7&&j<=7;i++,j++)
                 {
                    temppiece=current_position2[i][j];
                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                    movable_squares2[i][j]=1;
                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                 }
                 //downleft
                 for(i=move_from_x2-1,j=move_from_y2+1;i>=0&&j<=7;i--,j++)
                 {
                    temppiece=current_position2[i][j];
                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                    movable_squares2[i][j]=1;
                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                 }
             }
             else if(piece=='Q')
             {

                  //upward
                  for(j=move_from_y2-1;j>=0;j--)
                  {
                      temppiece=current_position2[move_from_x2][j];
                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                    movable_squares2[move_from_x2][j]=1;
                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                  }
                  //downward
                  for(j=move_from_y2+1;j<=7;j++)
                  {
                      temppiece=current_position2[move_from_x2][j];
                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                    movable_squares2[move_from_x2][j]=1;
                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                  }
                  //right
                  for(i=move_from_x2+1;i<=7;i++)
                  {
                    temppiece=current_position2[i][move_from_y2];
                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                    movable_squares2[i][move_from_y2]=1;
                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                  }
                   //left
                   for(i=move_from_x2-1;i>=0;i--)
                   {
                     temppiece=current_position2[i][move_from_y2];
                     if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                     {
                         break;
                     }
                     movable_squares2[i][move_from_y2]=1;
                     if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                     {
                         break;
                     }
                   }

                 //upright
                 for(i=move_from_x2+1,j=move_from_y2-1;i<=7&&j>=0;i++,j--)
                 {
                    temppiece=current_position2[i][j];
                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                    movable_squares2[i][j]=1;
                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                 }
                 //upleft
                 for(i=move_from_x2-1,j=move_from_y2-1;i>=0&&j>=0;i--,j--)
                 {
                    temppiece=current_position2[i][j];
                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                    movable_squares2[i][j]=1;
                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                 }
                 //downright
                 for(i=move_from_x2+1,j=move_from_y2+1;i<=7&&j<=7;i++,j++)
                 {
                    temppiece=current_position2[i][j];
                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                    movable_squares2[i][j]=1;
                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                 }
                 //downleft
                 for(i=move_from_x2-1,j=move_from_y2+1;i>=0&&j<=7;i--,j++)
                 {
                    temppiece=current_position2[i][j];
                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                    movable_squares2[i][j]=1;
                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                 }
             }
             else if(piece=='N')
             {
                 for(i=0;i<8;i++)
                 for(j=0;j<8;j++)
                 {
                     if((i==move_from_x2+2&&j==move_from_y2+1)||(i==move_from_x2+2&&j==move_from_y2-1)||(i==move_from_x2-2&&j==move_from_y2+1)||(i==move_from_x2-2&&j==move_from_y2-1)||(i==move_from_x2+1&&j==move_from_y2+2)||(i==move_from_x2+1&&j==move_from_y2-2)||(i==move_from_x2-1&&j==move_from_y2+2)||(i==move_from_x2-1&&j==move_from_y2-2))
                     {
                           if(current_position2[i][j]!='K'&&current_position2[i][j]!='Q'&&current_position2[i][j]!='B'&&current_position2[i][j]!='N'&&current_position2[i][j]!='R'&&current_position2[i][j]!='P')
                           movable_squares2[i][j]=1;
                     }
                 }
             }
             }
             else if(turn=='b')
             {
                if(piece=='p')
                {
                    //move
                    if(current_position2[move_from_x2][move_from_y2+1]=='s')
                    movable_squares2[move_from_x2][move_from_y2+1]=1;
                    if(move_from_y2==1)
                   if(current_position2[move_from_x2][move_from_y2+2]=='s'&&current_position2[move_from_x2][move_from_y2+1]=='s')
                    movable_squares2[move_from_x2][move_from_y2+2]=1;
                    //capture
                    if(move_from_x2==0)
                {
                    if(current_position2[move_from_x2+1][move_from_y2+1]=='P'||current_position2[move_from_x2+1][move_from_y2+1]=='R'||current_position2[move_from_x2+1][move_from_y2+1]=='N'||current_position2[move_from_x2+1][move_from_y2+1]=='B'||current_position2[move_from_x2+1][move_from_y2+1]=='Q'||current_position2[move_from_x2+1][move_from_y2+1]=='K')
                    movable_squares2[move_from_x2+1][move_from_y2+1]=1;
                }
                if(move_from_x2==7)
                {
                    if(current_position2[move_from_x2-1][move_from_y2+1]=='P'||current_position2[move_from_x2-1][move_from_y2+1]=='R'||current_position2[move_from_x2-1][move_from_y2+1]=='N'||current_position2[move_from_x2-1][move_from_y2+1]=='B'||current_position2[move_from_x2-1][move_from_y2+1]=='Q'||current_position2[move_from_x2-1][move_from_y2+1]=='K')
                    movable_squares2[move_from_x2-1][move_from_y2+1]=1;
                }
                if(move_from_x2>0&&move_from_x2<7)
                {
                    if(current_position2[move_from_x2+1][move_from_y2+1]=='P'||current_position2[move_from_x2+1][move_from_y2+1]=='R'||current_position2[move_from_x2+1][move_from_y2+1]=='N'||current_position2[move_from_x2+1][move_from_y2+1]=='B'||current_position2[move_from_x2+1][move_from_y2+1]=='Q'||current_position2[move_from_x2+1][move_from_y2+1]=='K')
                    movable_squares2[move_from_x2+1][move_from_y2+1]=1;
                    if(current_position2[move_from_x2-1][move_from_y2+1]=='P'||current_position2[move_from_x2-1][move_from_y2+1]=='R'||current_position2[move_from_x2-1][move_from_y2+1]=='N'||current_position2[move_from_x2-1][move_from_y2+1]=='B'||current_position2[move_from_x2-1][move_from_y2+1]=='Q'||current_position2[move_from_x2-1][move_from_y2+1]=='K')
                    movable_squares2[move_from_x2-1][move_from_y2+1]=1;
                }
                }
                else if(piece=='r')
             {
                  //downward
                  for(j=move_from_y2-1;j>=0;j--)
                  {
                      temppiece=current_position2[move_from_x2][j];
                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                    movable_squares2[move_from_x2][j]=1;
                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                  }
                  //upward
                  for(j=move_from_y2+1;j<=7;j++)
                  {
                      temppiece=current_position2[move_from_x2][j];
                      if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                    movable_squares2[move_from_x2][j]=1;

                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                  }
                  //right
                  for(i=move_from_x2+1;i<=7;i++)
                  {
                    temppiece=current_position2[i][move_from_y2];
                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                    movable_squares2[i][move_from_y2]=1;

                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                  }
                   //left
                   for(i=move_from_x2-1;i>=0;i--)
                   {
                     temppiece=current_position2[i][move_from_y2];
                     if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                     {
                         break;
                     }
                     movable_squares2[i][move_from_y2]=1;
                     if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                     {
                         break;
                     }
                   }
             }
             else if(piece=='b')
             {
                 //downright
                 for(i=move_from_x2+1,j=move_from_y2-1;i<=7&&j>=0;i++,j--)
                 {
                    temppiece=current_position2[i][j];
                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                    movable_squares2[i][j]=1;
                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                 }
                 //downleft
                 for(i=move_from_x2-1,j=move_from_y2-1;i>=0&&j>=0;i--,j--)
                 {
                    temppiece=current_position2[i][j];
                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                    movable_squares2[i][j]=1;
                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                 }
                 //upright
                 for(i=move_from_x2+1,j=move_from_y2+1;i<=7&&j<=7;i++,j++)
                 {
                    temppiece=current_position2[i][j];
                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                    movable_squares2[i][j]=1;
                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                 }
                 //upleft
                 for(i=move_from_x2-1,j=move_from_y2+1;i>=0&&j<=7;i--,j++)
                 {
                    temppiece=current_position2[i][j];
                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                    movable_squares2[i][j]=1;
                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                 }
             }
             else if(piece=='q')
             {
                  for(j=move_from_y2-1;j>=0;j--)
                  {
                      temppiece=current_position2[move_from_x2][j];
                      if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                    movable_squares2[move_from_x2][j]=1;

                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                  }
                  for(j=move_from_y2+1;j<=7;j++)
                  {
                      temppiece=current_position2[move_from_x2][j];

                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                    movable_squares2[move_from_x2][j]=1;
                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                  }

                  for(i=move_from_x2+1;i<=7;i++)
                  {
                    temppiece=current_position2[i][move_from_y2];
                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                    movable_squares2[i][move_from_y2]=1;

                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                  }
                   for(i=move_from_x2-1;i>=0;i--)
                   {
                     temppiece=current_position2[i][move_from_y2];

                     if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                     {
                         break;
                     }
                     movable_squares2[i][move_from_y2]=1;
                     if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                     {
                         break;
                     }
                   }
                 for(i=move_from_x2+1,j=move_from_y2-1;i<=7&&j>=0;i++,j--)
                 {
                    temppiece=current_position2[i][j];
                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                    movable_squares2[i][j]=1;

                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                 }
                 for(i=move_from_x2-1,j=move_from_y2-1;i>=0&&j>=0;i--,j--)
                 {
                    temppiece=current_position2[i][j];

                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                    movable_squares2[i][j]=1;
                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                 }
                 for(i=move_from_x2+1,j=move_from_y2+1;i<=7&&j<=7;i++,j++)
                 {
                    temppiece=current_position2[i][j];
                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                    movable_squares2[i][j]=1;
                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                 }
                 for(i=move_from_x2-1,j=move_from_y2+1;i>=0&&j<=7;i--,j++)
                 {
                    temppiece=current_position2[i][j];
                    if(temppiece=='p'||temppiece=='k'||temppiece=='q'||temppiece=='r'||temppiece=='b'||temppiece=='n')
                    {
                        break;
                    }
                    movable_squares2[i][j]=1;
                    if(temppiece=='P'||temppiece=='K'||temppiece=='Q'||temppiece=='R'||temppiece=='B'||temppiece=='N')
                    {
                        break;
                    }
                 }
             }
             else if(piece=='n')
             {
                 for(i=0;i<8;i++)
                 for(j=0;j<8;j++)
                 {
                     if((i==move_from_x2+2&&j==move_from_y2+1)||(i==move_from_x2+2&&j==move_from_y2-1)||(i==move_from_x2-2&&j==move_from_y2+1)||(i==move_from_x2-2&&j==move_from_y2-1)||(i==move_from_x2+1&&j==move_from_y2+2)||(i==move_from_x2+1&&j==move_from_y2-2)||(i==move_from_x2-1&&j==move_from_y2+2)||(i==move_from_x2-1&&j==move_from_y2-2))
                     {
                           if(current_position2[i][j]!='k'&&current_position2[i][j]!='q'&&current_position2[i][j]!='b'&&current_position2[i][j]!='n'&&current_position2[i][j]!='r'&&current_position2[i][j]!='p')
                           movable_squares2[i][j]=1;
                     }
                 }
             }
             }
         }
         return movable_squares2;
    }
    public char[][] updatePosition2(char current_position2[][],int move_from_x2,int move_from_y2,int move_to_x2, int move_to_y2,int change_turn_to)
    {
        char piece=current_position2[move_from_x2][move_from_y2];
        if(piece!='s')
        {
            if(piece=='P'&&move_from_y2==1)
            {
                current_position2[move_from_x2][move_from_y2]='s';
                current_position2[move_to_x2][move_to_y2]='Q';
            }
            else if(piece=='p'&&move_from_y2==6)
            {
                current_position2[move_from_x2][move_from_y2]='s';
                current_position2[move_to_x2][move_to_y2]='q';
            }
            else
            {
            current_position2[move_from_x2][move_from_y2]='s';
            current_position2[move_to_x2][move_to_y2]=piece;
            }
        }
        if(change_turn_to=='w')
        turn='w';
        else if(change_turn_to=='b')
        turn='b';

        return current_position2;
    }
    public boolean isMovableSquare(int move_from_x2,int move_from_y2,int move_to_x2,int move_to_y2,int movable_squares2[][])
    {
        if(movable_squares2[move_to_x2][move_to_y2]==1)
        return true;
        else
        return false;

    }
     private class mouse_input_handler extends MouseAdapter
    {
        @Override
        public void mousePressed(MouseEvent mouse_event)
         {
             int i,j,pi,pj,im,jm,im2,jm2,im3,jm3,move_from_x2,move_from_y2,tempc=0;
             int botpcx[];
             int botpcy[];
             int botcount=0;
             int botcount2=0;
             int botran=0;
             botpcx=new int[64];
             botpcy=new int[64];
             timer.start();


             mouse_click_x=(int)(Math.ceil(mouse_event.getX()/80));
             mouse_click_y=(int)(Math.ceil(mouse_event.getY()/80));
            if(state==2)
            {
                if(turn=='w')
                {

             //none selected
            if(move_from_x==9)
            {
                move_from_x=mouse_click_x;
                move_from_y=mouse_click_y;
                for(i=0;i<8;i++)
                for(j=0;j<8;j++)
                {
                  if((i==mouse_click_x)&&(j==mouse_click_y))
                   movable_squares[i][j]=2;
                  else
                  setMovableSquares2(current_position,movable_squares,move_from_x,move_from_y,'w');
                }

            }
            //selected piece selected again
            else if((move_from_x==mouse_click_x)&&(move_from_y==mouse_click_y))
            {
                for(i=0;i<8;i++)
                for(j=0;j<8;j++)
                movable_squares[i][j]=0;
                move_from_x=9;
                move_from_y=9;
            }
            //moving selected piece
            else
            {
                if(isMovableSquare(move_from_x,move_from_y,mouse_click_x,mouse_click_y,movable_squares))
                {
                    for(i=0;i<8;i++)
                    for(j=0;j<8;j++)
                    movable_squares[i][j]=0;
                updatePosition2(current_position,move_from_x,move_from_y,mouse_click_x,mouse_click_y,'b');
                //checking for check
                turn='w';
                for(i=0;i<8;i++)
                for(j=0;j<8;j++)
                {
                     if(current_position[i][j]=='K'||current_position[i][j]=='Q'||current_position[i][j]=='R'||current_position[i][j]=='B'||current_position[i][j]=='N'||current_position[i][j]=='P')
                     {
                        move_from_x=i;
                        move_from_y=j;
                        setMovableSquares2(current_position,movable_squares,move_from_x,move_from_y,'w');
                     }
                }
                white_king_attacked=0;
                for(i=0;i<8;i++)
                for(j=0;j<8;j++)
                {
                    if(movable_squares[i][j]==1)
                    {
                        if(current_position[i][j]=='k')
                        white_king_attacked++;
                    }
                }
                if(white_king_attacked>0)
                black_in_check=1;
                else
                black_in_check=0;
                turn='b';
                if(black_in_check==1)
                System.out.println("black check");
                for(i=0;i<8;i++)
                for(j=0;j<8;j++)
                movable_squares[i][j]=0;
                move_from_x=9;
                move_from_y=9;
                }
                else
                {
                    move_from_x=mouse_click_x;
                    move_from_y=mouse_click_y;
                    for(i=0;i<8;i++)
                    for(j=0;j<8;j++)
                    movable_squares[i][j]=0;
                    for(i=0;i<8;i++)
                    for(j=0;j<8;j++)
                    {
                      if((i==mouse_click_x)&&(j==mouse_click_y))
                       movable_squares[i][j]=2;
                      else
                      setMovableSquares2(current_position,movable_squares,move_from_x,move_from_y,'w');
                    }
                }
            }
        }
        if(turn=='b')
        {
            end1=0;
            //bot plays
            for(i=0;i<8;i++)
            {
            for(j=0;j<8;j++)
            {
                if(current_position[i][j]=='p'||current_position[i][j]=='r'||current_position[i][j]=='b'||current_position[i][j]=='q'||current_position[i][j]=='n'||current_position[i][j]=='k')
                {
                      move_from_x=i;
                      move_from_y=j;
                      botcount=0;
                      setMovableSquares2(current_position,movable_squares,move_from_x,move_from_y,'b');
                      if(black_in_check==1)
                      {
                        for(im=0;im<8;im++)
                        for(jm=0;jm<8;jm++)
                        {
                            if(movable_squares[im][jm]==1)
                            {
                                checktemp=0;
                                for(im2=0;im2<8;im2++)
                            for(jm2=0;jm2<8;jm2++)
                            chkpos[im2][jm2]=current_position[im2][jm2];
                                chkpos=updatePosition2(chkpos, move_from_x, move_from_y, im, jm, 'w');
                                for(im2=0;im2<8;im2++)
                                for(jm2=0;jm2<8;jm2++)
                                   {
                                        if(chkpos[im2][jm2]=='K'||chkpos[im2][jm2]=='Q'||chkpos[im2][jm2]=='R'||chkpos[im2][jm2]=='B'||chkpos[im2][jm2]=='N'||chkpos[im2][jm2]=='P')
                                       {
                                          move_from_x2=im2;
                                            move_from_y2=jm2;
                                            movable_squares2=setMovableSquares2(chkpos, movable_squares2, move_from_x2, move_from_y2,'w');
                                       }
                                    }
                                    white_king_attacked=0;
                            for(im3=0;im3<8;im3++)
                            for(jm3=0;jm3<8;jm3++)
                             {
                             if(movable_squares2[im3][jm3]==1)
                                {
                                   if(chkpos[im3][jm3]=='k')
                                    white_king_attacked++;
                                }
                              }
                           if(white_king_attacked>0)
                           checktemp=1;
                            if(checktemp==1)
                            {
                            movable_squares[im][jm]=0;
                            }
                            for(im3=0;im3<8;im3++)
                            for(jm3=0;jm3<8;jm3++)
                            movable_squares2[im3][jm3]=0;
                        }
                        }
                      }
                      for(pi=0;pi<8;pi++)
                      {
                      for(pj=0;pj<8;pj++)
                      {
                          if(movable_squares[pi][pj]==1)
                          botcount++;
                      }
                    }
                      if(botcount==0)
                      {
                        for(pi=0;pi<8;pi++)
                        for(pj=0;pj<8;pj++)
                        movable_squares[pi][pj]=0;
                        move_from_x=9;
                        move_from_y=9;
                        continue;
                      }
                      else
                      {
                        for(pi=0;pi<8;pi++)
                        for(pj=0;pj<8;pj++)
                        {
                            if(movable_squares[pi][pj]==1)
                            {
                                botcount2++;
                                botpcx[botcount2]=pi;
                                botpcy[botcount2]=pj;
                            }
                        }
                        botran=(int)(Math.random() * ((botcount2 - 1) + 1)) + 1;
                        move_to_x=botpcx[botran];
                        move_to_y=botpcy[botran];
                        for(pi=0;pi<8;pi++)
                        for(pj=0;pj<8;pj++)
                        movable_squares[pi][pj]=0;

                    updatePosition2(current_position,move_from_x,move_from_y,move_to_x,move_to_y,'w');
                    end1++;
                    move_from_x=9;
                    move_from_y=9;
                    tempc=1;
                    break;
                }
            }
        }
        if(tempc==1)
        break;
    }
    if(end1==0)
    state=3;
    }
    }
    }}
}


//Jframe class
public class chessout  extends JFrame
{
    //Default constructor
    public chessout()
    {
        customising_jframe();
    }


    public void customising_jframe()
    {
        add(new chess()); //adding JPanel chess to JFrame chessout
        setResizable(false); //making the window non resizable
        pack();
        setLocationRelativeTo(null);  //centralising the position of the window
        setTitle("Chess Game");  //Giving title to the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //activating the close button of the window
    }
    public static void main(String[] args)
    {
        EventQueue.invokeLater(() ->
        {
            JFrame jframe = new chessout();  //Creating the jframe
            jframe.setVisible(true);  //Making the JFrame visible
        });
    }
}