package com.example.pan.entity;

/**
 * <p>
 *
 * </p>
 *
 * @author daShen
 * @since 2022-04-04
 */
public class FilePath {

    private String username;
    private String name;
    private String path;
    private String type;
    private String time;
    private String details;

    public FilePath() {
    }

    public FilePath(String username, String name, String path, String type, String time, String details) {
        this.username = username;
        this.name = name;
        this.path = path;
        this.type = type;
        this.time = time;
        this.details=details;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
