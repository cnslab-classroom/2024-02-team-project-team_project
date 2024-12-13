import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Dot{
   int x;
   int y;
   
   public Dot(int x,int y) {
      this.x = x;
      this.y = y;
   }
}

class Main {
   static BufferedReader br;
   static BufferedWriter bw;
   
   static boolean isPossible(int[][]a,int x,int y,int n) {
      for(int i=0;i<9;i++) {
         if(a[x][i] == n) {
            return false;
         }
         if(a[i][y] == n) {
            return false;
         }
      }
        
      int three_x = (x/3)*3;
      int three_y = (y/3)*3;
      
      for(int i = three_x;i<three_x+3;i++) {
         for(int j = three_y;j<three_y+3;j++) {
            if(a[i][j] == n)
               return false;
         }
      }
      
      return true;
   }
   
   static void dfs(int[][] a, ArrayList<Dot> arr,int idx) throws IOException {
      if(idx == arr.size()) {
         for(int i = 0;i<a.length;i++) {
            for(int j=0;j <a.length;j++) {
               bw.write(String.valueOf(a[i][j])+" ");
            }
            bw.newLine();
         }
         bw.flush();
         bw.close();
         br.close();
         System.exit(0);
      }
      
      Dot d = arr.get(idx);
      
      for(int i=1;i<=9;i++) {
         if(isPossible(a,d.x,d.y,i)) {
            a[d.x][d.y] = i;
            dfs(a,arr,idx+1);
            a[d.x][d.y] = 0;
         }
      }   
   }
   
   public static void main(String[] args) throws IOException  {
      br = new BufferedReader(new InputStreamReader(System.in));
      bw = new BufferedWriter(new OutputStreamWriter(System.out));
      StringTokenizer st;
      
      ArrayList<Dot> arr = new ArrayList<>();
      int a[][] = new int[9][9];
      
      
      for(int i=0;i<a.length;i++) {
         st = new StringTokenizer(br.readLine());
         for(int j=0;j<a[i].length;j++) {
            a[i][j] = Integer.parseInt(st.nextToken());
            if(a[i][j] == 0) {
               arr.add(new Dot(i,j));
            }
         } 
      }
      
      dfs(a,arr,0);
         
   }
}
