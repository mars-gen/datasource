package com.example.pan.entity;

/**
 * <p>
 *
 * </p>
 *
 * @author daShen
 * @since 2022-04-04
 */
public class admin {
    private String username;
    private String password;
    private String perms;
    private String role;

    public admin(String username, String password,String perms,String role) {
        this.username = username;
        this.password = password;
        this.perms = perms;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
