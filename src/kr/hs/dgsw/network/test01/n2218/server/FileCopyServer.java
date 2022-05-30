package kr.hs.dgsw.network.test01.n2218.server;

import java.io.*;
import java.net.Socket;

public class FileCopyServer {

    private static String fileFolderPath = "/Users/DGSW/2학년/네트워크프로그래밍/files/";
    ;
    private OutputMessage om;
    private InputMessage im;
    private File folder;
    private Socket socket;


    public FileCopyServer(Socket socket) {
        this.socket = socket;
        om = new OutputMessage(socket);
        im = new InputMessage(socket);


        folder = new File(fileFolderPath);
    }

    public void checkCommand() throws NullPointerException {
        String message = null;

        message = im.getMessage();
        System.out.println(message);
        switch (message) {
            case "0":
                System.out.println("파일목록");
                sendFleList();
                break;
            case "1":
                System.out.println("업로드");
                saveFile();
                System.out.println("파일 저장");
                break;
            case "2":
                System.out.println("업로드2");
                saveFile2();
                System.out.println("파일 저장");
                break;
            case "3":
                System.out.println("다운로드");
                sendFile();
                System.out.println("파일 전송");
                break;
            default:
                System.out.println("알수 없는 명령입니다");
        }
    }

    public void sendFleList() {
        File[] files = folder.listFiles();

        if (files.length > 0) {
            for (File file : files) {
                om.sendMessage("** " + file.getName()+ "    "+file.length()+" **");
            }
        } else {
            System.out.println("보내기");
            om.sendMessage("** 0개 파일 **");
        }
        om.sendMessage("end");
        System.out.println("파일목록 전송 완료");
    }

    public void saveFile() {
        String filename = im.getMessage();
        File file = new File(fileFolderPath + filename);
        if(file.exists()){
            om.sendMessage("true");
            if(im.getMessage().equals("Yes"))
                im.getFile(fileFolderPath,filename);
            else System.out.println("파일을 가져오지 않습니다");
        }else {
            om.sendMessage("false");
            im.getFile(fileFolderPath,filename);
        }
    }

    public void saveFile2() {
        String filename = im.getMessage();
        im.getFile(fileFolderPath,filename);
    }

    public void sendFile() {
        String fileName = im.getMessage();
        File file = new File(fileFolderPath + fileName);
        om.sendFile(file);
    }


    public void excute() {
        while (true) {
            try {
                checkCommand();
            }catch (NullPointerException e){
                break;
            }
        }
    }
}
