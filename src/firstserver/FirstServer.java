/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firstserver;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author David
 */
public class FirstServer {

    static String ip;
    static int port;
    ServerSocket serverSock;

    public void handleClient(Socket s) throws IOException {
        PrintWriter pw;
        Scanner scan;
        boolean stop = false;
        pw = new PrintWriter(s.getOutputStream(), true);
        scan = new Scanner(s.getInputStream());
        System.out.println("Waiting for data from client");
        pw.println("Hi client, start sending strings plox");
        while (!stop) {
            String line = scan.nextLine();
            if (line.equals("#stop#")) {
                stop = true;
            }
            lineHandler(line, pw);
        }
        System.out.println("STOPPED");
        s.close();
    }

    public void startServer() throws IOException {

        serverSock = new ServerSocket();
        serverSock.bind(new InetSocketAddress(ip, port));
        
        System.out.println("You have connected");

        while (true) {
            Socket socket = serverSock.accept();//Remember that .accept is a blocking call.
            handleClient(socket);
        }
    }
    
    public boolean lineHandler(String str, PrintWriter pw){
        boolean fitsPattern = true;
        int n = str.indexOf("#");
        if(n < 0) return false;
        String sub = str.substring(0, n);
        String res = str.substring(n+1);
        if(sub.equals("UPPER")){
            pw.println(res.toUpperCase());
        }else if(sub.equals("LOWER")){
            pw.println(res.toLowerCase());
        }else if(sub.equals("REVERSE")){
            pw.println(reverseString(res));
        }else if(sub.equals("TRANSLATE")){
            pw.println(translateString(res));
        }else{
            fitsPattern = false;
        }
        
        
        return fitsPattern;
    }

    public static void main(String[] args) throws IOException {
        port = 8180;
        ip = "localhost";
        new FirstServer().startServer();

    }

    private String reverseString(String str) {
        String res = "";
        
        for(int i = str.length()-1; i >= 0; --i){
            res += str.charAt(i);
        }
        
        return res;
    }

    private String translateString(String res) {
        if(res.equals("hund")){
            return "dog";
        }else if(res.equals("kat")){
            return "cat";
        }
        return res;
    }

}
