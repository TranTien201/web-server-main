package org.example.Service;

import org.example.Model.Path;

import java.sql.SQLException;

public interface DBService {
    boolean checkDirectoryExist(String path) throws Exception;
    Path getPathByUrl(String path) throws Exception;
}
