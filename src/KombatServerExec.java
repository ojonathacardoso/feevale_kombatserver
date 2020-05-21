
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class KombatServerExec implements Runnable{    

    public static void main(String[] args) {
        System.out.println("Iniciando servidor...");
        KombatServerExec kse = new KombatServerExec();
        kse.waitForPlayer();        
    }
    
    @Override
    public void run() {
        try {
            while(true){
                Thread.sleep(30);
                if(btR){
                    if((player.x + SPEED) > maxWidth)
                        player.x = maxWidth;
                    else
                        player.x += SPEED;
                    
                    player.x += SPEED;
                    player.lado = "R";
                    out.println(player.x + "_" + player.y + "_" 
                        + player.w + "_" + player.h + "_" + player.lado);
                }
                if(btL){
                    if(player.x - SPEED < 0)
                        player.x = 0;
                    else
                        player.x -= SPEED;

                    player.lado = "L";
                    out.println(player.x + "_" + player.y + "_" 
                        + player.w + "_" + player.h + "_" + player.lado);
                }
                if(btU){
                    if(player.y - SPEED < 0)
                        player.y = 0;
                    else
                        player.y -= SPEED;
                    
                    out.println(player.x + "_" + player.y + "_" 
                        + player.w + "_" + player.h + "_" + player.lado);
                }
                if(btD){
                    if((player.y + SPEED) > maxHeight)
                        player.y = maxHeight;
                    else
                        player.y += SPEED;
                    
                    player.y += SPEED;
                    out.println(player.x + "_" + player.y + "_" 
                        + player.w + "_" + player.h + "_" + player.lado);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void waitForPlayer(){
        try {
            player = new Player();
            ServerSocket ss = new ServerSocket(8880);
            Socket s = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);
            
            Thread th = new Thread(this);
            th.start();
            
            String command2[] = in.readLine().split(",");
            maxWidth = Integer.parseInt(command2[0]);
            maxHeight = Integer.parseInt(command2[1]);
            
            String command = "";  
            while(!(command = in.readLine()).equals("EXIT")){
                if(command.equals("PR_R")){
                    btR = true;
                } else if (command.equals("RE_R")){
                    btR = false;
                }
                
                if(command.equals("PR_L")){
                    btL = true;
                } else if (command.equals("RE_L")){
                    btL = false;
                }
                
                if(command.equals("PR_U")){
                    btU = true;
                } else if (command.equals("RE_U")){
                    btU = false;
                }
                
                if(command.equals("PR_D")){
                    btD = true;
                } else if (command.equals("RE_D")){
                    btD = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static final int SPEED = 8;
    public Player player;
    
    public int maxWidth = 0;
    public int maxHeight = 0;
    
    boolean btR = false;
    boolean btL = false;
    boolean btU = false;
    boolean btD = false;
    boolean btC = false;
    
    PrintWriter out;
}
