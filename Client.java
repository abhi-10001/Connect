
import java.io.*;
import java.net.*;

class Client
{
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    public Client()
    {
        try{
            System.out.println("Sending request to server");
            socket = new Socket("127.0.0.1",5050);// put the IPaddress of the server you want to connect
            // InetAddress address, int port, InetAddress localAddr, int localPort)

            System.out.println("Connection done!");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        }catch(Exception e){

        }
    }
    public static void main(String [] args)
    {
        System.out.println("This is Client...");
        new Client();
    }
    public void startReading()
    {
        //thread take data from the user and 
        Runnable r1 = ()->{
            System.out.println("reader started...");

            try{
                while(true){
                    String msg = br.readLine();
                    if(msg.equals("exit")){
                        System.out.println("Server terminated the chat");
                        socket.close();
                        break;
                    }
                    System.out.println("Server: "+ msg);
                } 
            }catch(Exception e){
                // e.printStackTrace();
                System.out.println("Connection is closed.");
            }
        };
        new Thread(r1).start();
    }
    public void startWriting()
    {
        //thread-read 
        Runnable r2 = ()->{
            System.out.println("writer started..");
            try{
                while(!socket.isClosed()){
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }
                }
            }catch(Exception e){
                // e.printStackTrace();
                System.out.println("Connection is closed.");
            }
        };
        new Thread(r2).start();
    }
}
