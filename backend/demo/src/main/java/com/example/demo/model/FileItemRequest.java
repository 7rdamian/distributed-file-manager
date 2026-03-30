package com.example.demo.model;

public class FileItemRequest {
    private String name;
    private boolean isDirectory;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isDirectory() { return isDirectory; }
    public void setDirectory(boolean directory) { isDirectory = directory; }
}