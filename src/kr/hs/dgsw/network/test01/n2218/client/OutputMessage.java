package kr.hs.dgsw.network.test01.n2218.client;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class OutputMessage extends Thread{
    private final Socket socket;
    private Scanner scanner;
    private DataOutputStream dos;

    public OutputMessage(Socket socket){
        this.socket = socket;
        scanner = new Scanner(System.in);

        try {
            BufferedOutputStream bor = new BufferedOutputStream(socket.getOutputStream());
            dos = new DataOutputStream(bor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendInfo() throws IOException {
        System.out.print("ID: ");
        sendMessage(scanner.nextLine());
        System.out.print("Pass: ");
        sendMessage(scanner.nextLine());
    }

    public void sendMessage(String msg){
        try {
            dos.writeUTF(msg);
            dos.flush();
        } catch (IOException e) {
            System.out.println("전송 실패");
        }
    }

    public void sendFile(File file){
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];

            int readbit = 0;

            while((readbit = fis.read(bytes)) != -1) {
                dos.write(bytes, 0, readbit);
            }

            dos.flush();
            System.out.println("파일 전송 완료");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
