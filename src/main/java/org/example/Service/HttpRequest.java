package org.example.Service;

import org.example.DAO.DBConnect;
import org.example.Model.Path;

import javax.swing.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.SQLException;
import java.util.*;

public class HttpRequest implements HttpRequestService {
    public static final String DIR = "src/system/";
    InputStream inputStream ;
    BufferedOutputStream dataOut;
    PrintWriter out;
    String fileRequest;
    Socket socket;
    String request;
    DBImpl db;
    public HttpRequest(Socket socket, InputStream inputStream, BufferedOutputStream dataOut, PrintWriter out, byte[] buffer) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.dataOut = dataOut;
        this.out = out;
        this.request = new String(buffer);
        db = new DBImpl();
    }

    @Override
    public void informationRequest(JTextArea serverLogText) {

        InetSocketAddress ClientIp = (InetSocketAddress) socket.getRemoteSocketAddress();
        String clientIpAddress = ClientIp.getAddress().getHostAddress();
        String text = serverLogText.getText() + "\n" + "Ip: " + clientIpAddress + "\n" +"Port: "+ ClientIp.getPort() + "\n" + request;
        serverLogText.setText(text);
    }

    @Override
    public List<String> getMethodAndFileRequest() {
        StringTokenizer parse = new StringTokenizer(request); // get the http method of client
        String method = parse.nextToken().toUpperCase(); // GET
        String fileRequest = parse.nextToken().toLowerCase();
        System.out.println(fileRequest);
        List<String> list = new ArrayList<>();
        list.add(method);
        list.add(fileRequest);
        return list;
    }

    @Override
    public void getMethod(String fileRequest) throws SQLException, IOException {
        boolean check = true;
        String[] direction = fileRequest.split("/");

        int length = direction.length;
        if(length < 2) {
            dirOrFileNotFound("src/system/404.html");
//            System.out.println("Sai");
        } else {
            if(db.checkDirectoryExist(direction[1]) && !direction[1].equals("src")) {

                if(length == 2) {
                    fileRequest = "src/system/" + direction[1] + "/view/index.html";
//                    System.out.println(fileRequest);
                }
                else {
                    String path = fileRequest.substring(fileRequest.indexOf("/", 1));
//                    System.out.println("Đường dẫn: " + path);
                    Path p = db.getPathByUrl(path);
                    if(p.getPath() != null) {
//                        System.out.println("Co su ton tai");
                        fileRequest = "src/system/" + direction[1] + "/view/" + p.getHtmlFile();

                    } else {
                        check = false;
                        fileRequest = "src/system" + direction[1] + "/view/404.html";
                        dirOrFileNotFound(fileRequest);
//                        System.out.println("Khong co su ton tai");
                    }
                }
            } else if(!db.checkDirectoryExist(direction[1]) && !direction[1].equals("src")) {
                check = false;
                dirOrFileNotFound("src/system/404.html");
//                System.out.println("Sai 1");
            } else {
                System.out.println(fileRequest);
                String fileRequest1 = buildFileRequestByFileExtension(direction[1], fileRequest);
                System.out.println(fileRequest1);
            }
            if(check) {
                File file = new File(".", fileRequest);
                int fileLength = (int) file.length();
                byte[] fileData = readFileData(file, fileLength);
                out.println("HTTP/1.1 200 OK");
                out.println("Content-length: " + fileLength);
                out.println();
                out.flush();
                dataOut.write(fileData,0,fileLength);
                dataOut.flush();
            }
        }
    }

    @Override
    public void handelRequest() throws SQLException, IOException {
        List<String> stringList = getMethodAndFileRequest();
        if(stringList.get(0).equals("GET")) {
            getMethod(stringList.get(1));
        }
    }



    @Override
    public void dirOrFileNotFound(String path) throws IOException {
        File file = new File(".", path);
        int fileLength = (int) file.length();
        String content = "text/html";
        byte[] fileData = readFileData(file,fileLength);
        out.println("HTTP/1.1 404 File Not Found");
        out.println("Content-length: " + fileLength);
        out.println();
        out.flush();
        dataOut.write(fileData, 0, fileLength);
        dataOut.flush();

    }

    @Override
    public byte[] readFileData(File file, int fileLength) throws IOException {
        FileInputStream fileIn = null;
        byte[] fileData = new byte[fileLength];

        try{
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        }finally {
            if(fileIn != null)
                fileIn.close();
        }
        return fileData;
    }

    @Override
    public String buildFileRequestByFileExtension(String dir, String fileRequest) {
        String fileExtension = fileRequest.split("\\.")[1];
        String buildFile = "";
        if(fileExtension.equals("css")) {
            buildFile = DIR + dir + "/css/" + fileRequest;
        }
        if(fileExtension.equals("js")) {
            buildFile = DIR + dir + "/js/" + fileRequest;
        }
        if(fileExtension.equals("jpg") || fileExtension.equals("jpeg")
                || fileExtension.equals("gif") || fileExtension.equals("png")) {
            buildFile = DIR + dir + "/image/" + fileRequest;
        }
        System.out.println(fileExtension);
        return buildFile;
    }


}
