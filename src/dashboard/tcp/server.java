package dashboard.tcp;



import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class server extends Thread{
    static ServerSocket ss;
    static Socket s;
    static ObjectInputStream dis;
    static DataOutputStream dos;
    public server(int port){
        try {
            ss = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        try {
            s = ss.accept();
            dis = new ObjectInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            while (true) {
                MessageProcessing.processMessage((get()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void send(String msg){
        try {
            if (s.isConnected()) {
                new Frame();
                dos.writeUTF(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get() throws Exception {

        return (String)dis.readObject();
    }

    public static void close(){
        try {
            ss.close();
            s.close();
            dis.close();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

