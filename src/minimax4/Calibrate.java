
package minimax4;

import java.util.PriorityQueue;
import java.util.Random;
import static minimax4.Minimax4.sol;

public class Calibrate {
    int size=0;
    int[][] boardGenerator(int size)
    {
       Random rand=new Random();
       this.size=size;
        int[][] board=new int[size][size];
        int max=9;
        int min=0;
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {                   
                    int randomNum = rand.nextInt((max - min) + 1) + min;
             /*   if((i+j)%2==0)
                    board[i][j]=1;
                else
                    board[i][j]=0;*/
                board[i][j]=randomNum;
            }
        }
        
        return board;
    }
    
         
    
    }

