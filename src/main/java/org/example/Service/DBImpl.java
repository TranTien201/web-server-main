package org.example.Service;

import org.example.DAO.DBConnect;
import org.example.Model.Path;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBImpl implements DBService {
    private DBConnect connect;
    private Statement statement;
    private PreparedStatement preparedStatement;



    @Override
    public Path getPathByUrl(String path) throws SQLException {
        Path p = new Path();
        try {
            connect = new DBConnect();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String sql = "SELECT * FROM `path` WHERE path = '" + path + "'";
        preparedStatement = connect.getConnect().prepareStatement(sql);
        ResultSet result = preparedStatement.executeQuery(sql);
        if(result != null) {
            while (result.next()) {
                p.setIdPath(result.getInt("pathID"));
                p.setPath(result.getString("path"));
                p.setHtmlFile(result.getString("fileHTML"));
            }
        }
        return p;
    }
    @Override
    public boolean checkDirectoryExist(String directory) throws SQLException {
        try {
            connect = new DBConnect();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String sql = "SELECT * FROM `directory` WHERE directory = '" + directory + "'";
        preparedStatement = connect.getConnect().prepareStatement(sql);
        ResultSet result = preparedStatement.executeQuery(sql);
        boolean check = false;
        while (result.next()) {
            if(!result.getString("directoryID").equals("")) {
                check = true;
            }
        }
        return check;
    }
}
