package kr.hs.dgsw.network.test01.n2218.server;

import java.io.*;
import java.net.Socket;

public class InputMessage extends Thread{
    private BufferedInputStream bir;
    private DataInputStream dis;
    private InputStream is;

    public InputMessage(Socket socket){
        try {
            is = socket.getInputStream();
            bir = new BufferedInputStream(is);
            dis = new DataInputStream(bir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMessage() {
        try {
            return dis.readUTF();
        } catch (IOException e) {
            System.out.println("유저 1명이 떠남");
        }
        return null;
    }

    public void getFile(String dirPath,String fileName){
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(dirPath+fileName);

            byte[] bytes = new byte[1024];
            int readbit = 0;
            while ((readbit = dis.read(bytes)) != -1){
                fos.write(bytes, 0, readbit);
                fos.flush();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}