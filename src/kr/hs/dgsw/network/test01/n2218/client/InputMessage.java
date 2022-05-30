package kr.hs.dgsw.network.test01.n2218.client;

import java.io.*;
import java.net.Socket;

public class InputMessage{
    private BufferedInputStream bir;
    private DataInputStream dis;
    private String msg;

    public InputMessage(Socket socket){
        try {
            InputStream is = socket.getInputStream();
            bir = new BufferedInputStream(is);
            dis = new DataInputStream(bir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkInfo(){
        try {
            this.msg = dis.readUTF();
            if(this.msg.equals("true")){
                System.out.println("** FTP 서버에 접속하였습니다. **");
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("** ID 또는 PASS가 틀렸습니다.! **");
        return false;
    }

    public void printFileList(){
        try {
            String line=null;
            while((line=dis.readUTF())!=null){
                if (line.equals("end"))
                    break;
                    System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("서버오류로 읽어오지 못합니다");
        }
    }

    public boolean getSeverState() {
        try {
            return Boolean.parseBoolean(dis.readUTF());
        } catch (IOException e) {
            System.out.println("서버오류로 읽어오지 못합니다");
        }
        return false;
    }

    public void getFile(String dirPath,String fileName){
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(dirPath+fileName);
            long size = dis.readLong();

            byte[] bytes = new byte[(int) size];

            fos.write(bytes, 0,(int)size);
            fos.flush();

            fos.close();
            System.out.println("저장 완료");
        } catch (IOException e) {
            System.out.println("서버오류로 읽어오지 못합니다");
        }
    }

}
