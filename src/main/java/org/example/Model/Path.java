package org.example.Model;

public class Path {
    private int idPath;
    private String path;
    private String htmlFile;

    public Path() {

    }

    public Path(int idPath, String path, String htmlFile) {
        this.idPath = idPath;
        this.path = path;
        this.htmlFile = htmlFile;
    }

    public int getIdPath() {
        return idPath;
    }

    public void setIdPath(int idPath) {
        this.idPath = idPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHtmlFile() {
        return htmlFile;
    }

    public void setHtmlFile(String htmlFile) {
        this.htmlFile = htmlFile;
    }

    @Override
    public String toString() {
        return "Path {" +
                "idPath=" + idPath +
                ", path='" + path + '\'' +
                ", htmlFile='" + htmlFile + '\'' +
                '}';
    }
}
