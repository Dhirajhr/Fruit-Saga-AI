
package minimax4;
import java.util.*;
class Yo implements Comparator<ScoreChild>{
            
    public int compare(ScoreChild a,ScoreChild b){
                int x=a.score;
                int y=b.score;
                if(x<y)
                    return 1;
                else if(x>y)
                    return -1;
                else
                    return 0;
            }
            
        }
class ScoreChild{
    int[][] child;
    int score;
    int newScore;
    int total=0;
    int cx;
    int cy;
    ScoreChild(){}
    ScoreChild(ScoreChild ss)
    {
        child=new int[ss.child.length][ss.child.length];
        for(int i=0;i<ss.child.length;i++)
        {
            child[i]=Arrays.copyOf(ss.child[i], ss.child.length);
        }
    }  
}
public class Minimax4 {
    private int f=0;//fruits
    private int size=10;
    private int height=3;
    private double timeLeft=0;
    private int alpha1=Integer.MIN_VALUE;
    private int beta1=Integer.MAX_VALUE;
    private double timePerMove=timeLeft;
    private static double[] calib=new double[52];
    static  String sol="";
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
                 //   int randomNum = rand.nextInt((max - min) + 1) + min;
                if((i+j)%2==0)
                    board[i][j]=1;
                else
                    board[i][j]=0;
                //board[i][j]=randomNum;
            }
        }
        
        return board;
    }
          //calibration
    public ScoreChild moveGenerator(int[][] x)
    {    
        ScoreChild bestChild=new ScoreChild();
        PriorityQueue<ScoreChild> tm=new PriorityQueue<ScoreChild>(new Yo());    
    int bestVal=Integer.MIN_VALUE;
    int alpha2=Integer.MIN_VALUE;
    int beta2=Integer.MAX_VALUE;
    ScoreChild root=new ScoreChild();  
    root.score=0;
    root.child=x;
     ScoreChild temp=new ScoreChild(root);
     
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {                
               if(temp.child[i][j]!=-1)
               {   ScoreChild sc=new ScoreChild(root);
                 
                   generate(i,j,sc,root.child[i][j],temp);
                   
                   sc.newScore=sc.score;
                   sc.total=sc.score;                  
                    sc.cx=i;
                    sc.cy=j;
                    
                   tm.add(sc);
                   
               }
            }
        }
        //Calibration
        double numberOfNodes;
        this.timePerMove=(((this.timeLeft)*2)*(tm.peek().score))/f;
        if(tm.size()>0){
        double lvl1=calib[2*(size-1)]/(tm.size());
        double lvl2=calib[2*(size-1)+1]/(tm.size()+(tm.size()*tm.size()));
        double lvlAvg=((lvl1+lvl2)/2);
        numberOfNodes=this.timePerMove/lvlAvg;
          System.out.println("Avg"+lvlAvg);
        }
        else
        {
        double lvl1=calib[2*(size-1)];
        double lvl2=calib[2*(size-1)+1];
        double lvlAvg=((lvl1+lvl2)/2);
       
        numberOfNodes=this.timePerMove/lvlAvg;
        }
        if(this.size>=20){
        if(tm.size()==1 || tm.size()==0)
        this.height=(int)Math.ceil(((int)Math.log(numberOfNodes)));
        else{
            this.height=(int)Math.ceil(((int)Math.log(numberOfNodes))/((int)(Math.log(tm.size()))));
        }
        }
        else{
                 if(tm.size()==1 || tm.size()==0)
        this.height=(int)Math.ceil(((int)Math.log(numberOfNodes)))+1;
        else{
            this.height=(int)Math.ceil(((int)Math.log(numberOfNodes))/((int)(Math.log(tm.size()))));
            this.height+=1;
        }   
            
            
        }
      
        System.out.println("Score"+tm.peek().score);
        System.out.println("Per Move"+this.timePerMove);
        System.out.println("height"+this.height);
       
       
        
        
       
        //Calibration
       // System.out.println(this.height);
        while(!tm.isEmpty()){
          //  System.out.println("Socre"+tm.peek().score);
            ScoreChild ch=(ScoreChild)tm.remove();
            ch.child=gravity(ch.child);
            if(ch.newScore>=f/2)
            {
                    bestChild=new ScoreChild();
                    bestChild.child=ch.child;
                    bestChild.score=ch.newScore*ch.newScore;
                    bestChild.cx=ch.cx;
                    bestChild.cy=ch.cy;
                    break;
            }
                   int moveVal=minimax(ch,1,false,alpha2,beta2);
                   alpha2=Math.max(alpha2,beta1);                   
                   if (moveVal > bestVal)
                   {
                    bestChild=new ScoreChild();
                    bestChild.child=ch.child;
                    bestChild.score=moveVal;
                    bestVal = moveVal;
                     bestChild.cx=ch.cx;
                    bestChild.cy=ch.cy;
                   }
          }


        sol=(char)(bestChild.cy+65)+""+(bestChild.cx+1);
        return bestChild;
    }
    public int minimax(ScoreChild sc1, int depth, boolean isMax,int alpha,int beta)
    {
//for(int i=0;i<size;i++)
//{
//    for(int j=0;j<size;j++)
//    {
//        System.out.print(sc1.child[i][j]+"  ");
//    }
//    System.out.println();
//}
//System.out.println();
//        boolean checko=false;     
       if(depth==height)
            return (sc1.score*sc1.score);
//        for(int i=0;i<size;i++)
//        {
//            for(int j=0;j<size;j++)
//            {
//                if(sc1.child[i][j]!=-1)
//                {
//                    checko=true;
//                    break;
//                }
//            }
//            if(checko==true)
//                break;
//        }
//        if(checko==true)
//            checko=false;
//        else
//            return (sc1.score*sc1.score);
        if(f==sc1.total)
            return (sc1.score*sc1.score);
        if(isMax)
        {
              ScoreChild temp=new ScoreChild(sc1);
          PriorityQueue<ScoreChild> tm=new PriorityQueue<ScoreChild>(new Yo());
        int best=Integer.MIN_VALUE;  
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
               if(temp.child[i][j]!=-1)
               {    
                   ScoreChild sc=new ScoreChild(sc1);
          
                   generate(i,j,sc,sc1.child[i][j],temp);
                  
                  
                   sc.total=sc.score+sc.total;
                    sc.newScore=sc.score+sc1.newScore;
                    if(sc.newScore>=f/2)
                        return sc.score*sc.score;
                   tm.add(sc);
               }
            }
        }
        while(!tm.isEmpty()){
            
                ScoreChild yy=(ScoreChild)tm.remove();
                 yy.child=gravity(yy.child);
                   int yo1=minimax(yy,depth+1,!isMax,alpha,beta);
                   int newVal=yo1-(sc1.score*sc1.score);//yo1-(sc1.score*sc1.score);
                   best=Math.max(newVal,best);                    
                   alpha=Math.max(best,alpha);
                   if(alpha>=beta){

                       break;
                   }
        }

        int yo=Math.max(alpha, alpha1);
        alpha1=yo;
        return best;
        }
        else
        {
          PriorityQueue<ScoreChild> tm=new PriorityQueue<ScoreChild>(new Yo());
            ScoreChild temp=new ScoreChild(sc1);
        int best=1000;  
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
               if(temp.child[i][j]!=-1)
               {    
                   ScoreChild sc=new ScoreChild(sc1);
      
                   generate(i,j,sc,sc1.child[i][j],temp);
                     sc.total=sc.score+sc.total;
                 
                   sc.newScore=sc1.newScore;
                   tm.add(sc);
               }
            }
        }
        while(!tm.isEmpty()){
            
                ScoreChild yy=(ScoreChild)tm.remove();
                  yy.child=gravity(yy.child);
                   int yo1=minimax(yy,depth+1,!isMax,alpha,beta);
                   int newVal=yo1+(sc1.score*sc1.score);//yo1-(sc1.score*sc1.score);
                   best=Math.min(newVal,best);                    
                   beta=Math.min(best,beta);
                   if(alpha>=beta){
                       
                       

                    //
                     //  counter++;
                       break;
                   }
        }

        int yo=Math.min(beta, beta1);
        beta1=yo;
        return best;
            
        }
    }
    public void generate(int row,int col, ScoreChild sc, int number, ScoreChild temp)
    {
        
       if(sc.child[row][col]==number)
       {
     
        sc.child[row][col]=-1;
        temp.child[row][col]=-1;
        sc.score++;
        if(col-1>=0)
        {
             if(sc.child[row][col-1]!=-1)
             {
                 generate(row,col-1,sc,number,temp);//left
             }
        }
            
        if(col+1<size)
        {
            if(sc.child[row][col+1]!=-1)
            {
                generate(row,col+1,sc,number,temp);//right
            }
        }
        if(row-1>=0)
        {
        if(sc.child[row-1][col]!=-1)
        {
            generate(row-1,col,sc,number,temp);//up
        }
        }
        if(row+1<size)
        {
        if(sc.child[row+1][col]!=-1)
        {
            generate(row+1,col,sc,number,temp);//down
        }
        } 
       }
    }
    public int[][] gravity(int[][] st)
    {
        int[][] x=st;
        for(int c=0;c<size;c++){
        for(int i=0;i<size-1;i++)
        {
            for(int j=0;j<size-i-1;j++)
            {
                int temp=100;
            
                if(x[j+1][c]==-1 && x[j][c]!=-1)
                {
                    temp=x[j+1][c];
                    
                    x[j+1][c]=x[j][c];
                  
                    x[j][c]=temp;
                  
                }
            }
        }
        }        
        return x;
    }
    public static void main(String[] args) {
        
    OutputReadWrite orw=new OutputReadWrite();
    orw.read();
    int[][] arr=orw.getConfig();
    calib=orw.readCalibration();
    Minimax4 ob=new Minimax4();
    ob.size=arr.length;
    ob.f=orw.setF();
    ob.timeLeft=orw.getTime();
    long start=System.currentTimeMillis();
    ScoreChild yoyo=ob.moveGenerator(arr);    
   long end=System.currentTimeMillis();
   System.out.println(end-start);
   orw.setConfig(yoyo.child);
   orw.write(sol);

    }
    
}
