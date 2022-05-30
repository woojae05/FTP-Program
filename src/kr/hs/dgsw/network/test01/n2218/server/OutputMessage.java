package kr.hs.dgsw.network.test01.n2218.server;

import java.io.*;
import java.net.Socket;

public class OutputMessage {

    private DataOutputStream dos;

    public OutputMessage(Socket socket){
        try {

            BufferedOutputStream bor = new BufferedOutputStream(socket.getOutputStream());
            dos = new DataOutputStream(bor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg){
        try {
            dos.writeUTF(msg);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendFile(File file){
        try {
            System.out.println(file.length());
            dos.writeLong(file.length());
            dos.flush();
            FileInputStream fis = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];

            dos.write(bytes,0,(int) file.length());
            dos.flush();

            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
