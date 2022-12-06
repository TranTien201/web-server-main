package org.example.Model;

public class Directory {
    private int idDirectory;
    private String directory;

    public Directory() {

    }

    public Directory(int idDirectory, String directory) {
        this.idDirectory = idDirectory;
        this.directory = directory;
    }

    public int getIdDirectory() {
        return idDirectory;
    }

    public void setIdDirectory(int idDirectory) {
        this.idDirectory = idDirectory;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }
}
