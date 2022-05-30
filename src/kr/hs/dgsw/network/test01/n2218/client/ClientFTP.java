package kr.hs.dgsw.network.test01.n2218.client;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientFTP {
    private static String downloadDir = "/Users/DGSW/Downloads/FTP/";
    private Socket socket;
    private Scanner scanner;
    private OutputMessage om;
    private InputMessage im;

    public ClientFTP(Socket socket){
        this.socket = socket;
        scanner = new Scanner(System.in);
        im = new InputMessage(socket);
        om = new OutputMessage(socket);
    }

    public void excute(){
        while(true){
        checkAndExcute(scanner.nextLine());
        }
    }


    public void checkAndExcute(String command){
        String[] splited = command.split(" ");
        if(command.startsWith("/파일목록")){
            System.out.println("**[File List]**");
            om.sendMessage("0");
            im.printFileList();
        }else if(command.startsWith("/업로드") && splited.length == 2){
            om.sendMessage("1");
            upload1(splited[1]);
        }else if(command.startsWith("/업로드") && splited.length == 3){
            om.sendMessage("2");
            upload2(splited[1],splited[2]);
        }else if(command.startsWith("/다운로드")){
            om.sendMessage("3");
            download(splited[1]);
            System.out.println("다운로드 됨");
        }else if(command.startsWith("/접속종료")){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else System.out.println("알 수 없는 명령어 입니다.");
    }

    public void upload1(String filePath){
        File file = new File(filePath);
        om.sendMessage(file.getName());
        Boolean isExists = im.getSeverState();

        if(isExists){
            System.out.print("파일이 이미 있습니다. 덮어쓰기 하실건가요??(Yes: 덮어쓰기 / No: 업로드 취소): ");
            String isUpload = scanner.nextLine();
            if(isUpload.equals("Yes")){
                om.sendMessage("Yes");
                om.sendFile(file);
                System.out.printf("** %s 파일을 업로드 하였습니다.**\n",file.getName());
            }
            else if(isUpload.equals("No")) {
                om.sendMessage("No");
                System.out.println("** 파일 업로드를 취소합니다. **");
            }

        }else{
            om.sendFile(file);
        }
    }

    public void upload2(String filePath,String name){
        File file = new File(filePath);
        om.sendMessage(name);
        om.sendFile(file);
        System.out.printf("** %s 파일을 업로드하였습니다 **\n",name);
    }

    public void download(String filename){
        om.sendMessage(filename);
        im.getFile(downloadDir,filename);
    }

}
