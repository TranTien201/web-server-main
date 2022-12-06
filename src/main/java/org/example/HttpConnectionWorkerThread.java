package org.example;

import org.example.Service.HttpRequest;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.logging.Logger;

public class HttpConnectionWorkerThread extends Thread{

    static final File WEB_ROOT =new File(".");
    static final String DEFAULT_FILE ="src/system/index.html";
    static final String FILE_NOT_FOUND ="src/system/404.html";
    static final String METHOD_NOT_SUPPORTED = "src/system/not_supported.html";
    static final String CRLF = "\n\r";

    //    // verbose mode
    static final boolean verbose = true;

    private Socket socket;
    private JTextArea serverLogText;
    InputStream inputStream;
    BufferedOutputStream dataOut;
    PrintWriter out;
    String filerequest;
    byte[] buffer = new byte[10000];
    HttpConnectionWorkerThread(Socket socket, JTextArea serverLogText) throws IOException {
        this.socket = socket;
        this.serverLogText = serverLogText;
        inputStream = socket.getInputStream();
        dataOut = new BufferedOutputStream(socket.getOutputStream());
        out = new PrintWriter(socket.getOutputStream());
        inputStream.read(buffer);
    }
    @Override
    public void run() {
        try {
            HttpRequest httpRequest = new HttpRequest(socket, inputStream, dataOut, out, buffer);
            httpRequest.informationRequest(serverLogText);
            httpRequest.handelRequest();


            inputStream.close();
            out.close();
            dataOut.close();
            socket.close();
            sleep(3000);
            System.out.println("Connecting Processing Finished ...");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // doc file
//    private byte[] readfileData(File file, int filelength) throws IOException{
//        FileInputStream fileIn = null;
//        byte[] fileData = new byte[filelength];
//
//        try{
//            fileIn = new FileInputStream(file);
//            fileIn.read(fileData);
//        }finally {
//            if(fileIn != null)
//                fileIn.close();
//        }
//        return fileData;
//    }
//
//    // tra ve loai co ho tro
//    private String getContentType(String fileRequested){
//        if(fileRequested.endsWith(".htm") || fileRequested.endsWith(".html")){
//            return "text/html";
//        }else{
//            return "text/plain";
//        }
//    }
//
//    // file k tim thay || file khong ton tai
//    private void fileNotFound(PrintWriter out, OutputStream dataOut, String fileRequest) throws IOException{
//        File file = new File(WEB_ROOT, FILE_NOT_FOUND);
//        int fileLength = (int) file.length();
//        String content = "text/html";
//        byte[] fileData = readfileData(file,fileLength);
//
//        out.println("HTTP/1.1 404 File Not Found");
//        out.println("Content-length: " + fileLength);
//        out.println();
//        out.flush();
//
//        dataOut.write(fileData, 0, fileLength);
//        dataOut.flush();
//
//        if(verbose){
//            System.out.println("File " + fileRequest + " not found");
//        }
//    }

}
