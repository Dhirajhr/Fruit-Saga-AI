
package minimax4;
import java.io.*;
import java.util.*;
public class OutputReadWrite {
     String thisLine = "";
     String algo=null;
     int yo=0;
     private int board=0;
     private int fruit=0;
     private double time=0;
     private int[][] config=null;
     public void read(){
        try{
        FileReader fr=new FileReader("C:/Users/hp/Desktop/input.txt");
        BufferedReader br = new BufferedReader(fr);
        int lineNum=0;
        int row=0;
         while ((thisLine = br.readLine()) != null) {
            
            if(lineNum==0){
                board=Integer.parseInt(thisLine);
                 config=new int[board][board];
            }
            else if(lineNum==1)
            {
             
                fruit=Integer.parseInt(thisLine);
               
            }
            else if(lineNum==2)
                time=Double.parseDouble(thisLine);
            else{
               String[] temp=thisLine.split("");
               for(int i=0;i<board;i++)
               {
                   if(Character.isDigit(temp[i].charAt(0))){
                   config[row][i]=Integer.parseInt(temp[i]);
                   yo++;
                   }
                   else
                        config[row][i]=-1;
               }
               row++;
            }
            lineNum++;
         }
         fr.close();
         br.close();
        }catch(Exception e){
       // System.out.println(e);
        }
    }
     public void write(String ans){
         String fill=""; 
         BufferedWriter bw=null;
         try{
             //System.out.println("hi");
             FileWriter fw=new FileWriter("C:/Users/hp/Desktop/output.txt");
             bw=new BufferedWriter(fw);
             fill=ans+System.lineSeparator();
             for(int i=0;i<board;i++)
             {
                 for(int j=0;j<board;j++)
                 {
                     if(config[i][j]==-1)
                          fill+="*";
                     else
                       fill+=String.valueOf(config[i][j]);  
                   
                 }
                 fill+=System.lineSeparator();
             }

             bw.write(fill);
           //  System.out.println("hi");
             
         }catch(Exception e){}//System.out.println(e);}
         finally{
             try{
             bw.close();
             }catch(Exception e){}
         }
                
     }
     public int[][] getConfig(){
         
         return config;
     } 
        public int setF(){
         
         return yo;
     }
      public void setConfig(int[][] config){
         
         this.config=config;
     } 
     public int getFruit()
     {
         return fruit;
     }
     public int getBoard()
     {
         return board;
     }
     public double getTime()
     {
         
         return time*1000;
     }
     
         public double[] readCalibration(){
              double[] l=new double[52];
        try{
        FileReader fr=new FileReader("C:/Users/hp/Desktop/calibrate.txt");
        BufferedReader br = new BufferedReader(fr);
        String[] arr=new String[26];
       
        int i=0;
         while ((thisLine = br.readLine()) != null) {
            String[] temp=thisLine.split(" ");
            l[i]=Double.parseDouble(temp[0]);
            l[i+1]=Double.parseDouble(temp[1]);
            i+=2;
         }
         fr.close();
         br.close();
        }catch(Exception e){
       System.out.println(e);
     
        }
      return l;
    } 
     
     
    
}
