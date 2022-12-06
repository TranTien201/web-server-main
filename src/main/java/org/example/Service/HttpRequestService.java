package org.example.Service;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

public interface HttpRequestService {
    void informationRequest(JTextArea serverLogText) throws SQLException;

    List<String> getMethodAndFileRequest();

    void getMethod(String fileRequest) throws SQLException, IOException;

    void handelRequest() throws SQLException, IOException;

    void dirOrFileNotFound(String path) throws IOException;

    byte[] readFileData(File file, int fileLength) throws IOException;

    String buildFileRequestByFileExtension(String dir, String fileRequest);
}
