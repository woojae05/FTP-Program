package kr.hs.dgsw.network.test01.n2218.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
    private Socket socket;
    private BufferedReader br;
    private PrintWriter pw;
    private Scanner scanner;

    private final String ip;
    private final int port;

    public ClientMain(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void excute(){
        try {
            socket = new Socket(ip, port);
            System.out.println("** 서버에 접속하였습니다. **");
            ClientFTP cf = new ClientFTP(socket);

            InputMessage im = new InputMessage(socket);
            OutputMessage om = new OutputMessage(socket);

            om.sendInfo();
            while(true){
                if(im.checkInfo()){
                    cf.excute();
                    break;
                }else{
                    om.sendInfo();
                }
            }

        } catch (IOException e) {
            System.out.println("서버 연결에 실패 하였습니다");
        }
    }

}
