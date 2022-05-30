package kr.hs.dgsw.network.test01.n2218.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class App {

    private static final int PORT_NUMBER = 1004;

    public static void main(String[] args) {
        System.out.println("서버 실행");
        try(ServerSocket server = new ServerSocket(PORT_NUMBER)){
            while(true){
                Socket connection = server.accept();
                Thread task = new MainServer(connection);
                task.start();
            }
        }catch(IOException e) {
        }
    }
}
