package kr.hs.dgsw.network.test01.n2218.client;

public class App {
    public static void main(String[] args) {
        String ip = "localhost";
        int port = 1004;
        ClientMain main =  new ClientMain(ip, port);
        main.excute();
    }
}
