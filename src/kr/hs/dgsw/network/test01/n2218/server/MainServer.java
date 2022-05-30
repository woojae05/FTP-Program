package kr.hs.dgsw.network.test01.n2218.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainServer extends Thread{
    private static String id = "admin";
    private static String password = "1234";
    private OutputMessage om;
    private InputMessage im;

    private final Socket socket;

    public MainServer(Socket socket){
        this.socket = socket;

        om = new OutputMessage(socket);
        im = new InputMessage(socket);
    }


    public void checkInfoAndExcute(){
        FileCopyServer fs = new FileCopyServer(socket);
        String id = null;
        String password = null;

        while(true){

            id = im.getMessage();
            password = im.getMessage();

            if(MainServer.id.equals(id) && MainServer.password.equals(password)){
                om.sendMessage("true");
                System.out.println("FTP 서버 실행");
                fs.excute();
                break;
            }else{
                om.sendMessage("false");
            }
        }
    }

    @Override
    public void run() {
            checkInfoAndExcute();
    }

}
