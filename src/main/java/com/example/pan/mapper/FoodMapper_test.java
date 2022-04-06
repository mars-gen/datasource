package com.example.pan.mapper;

import com.example.pan.entity.FilePath;
import com.example.pan.entity.User;
import com.example.pan.entity.admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author daShen
 * @since 2022-04-04
 */
@Component
@Repository
public interface FoodMapper_test {
    boolean addfile(FilePath filePath);

    boolean detelefile(@Param("username") String username, @Param("filename") String filename);

    boolean insertuser(User user);

    boolean deleteuser(String username);

    boolean updateuser(User user);

    boolean changpassword(@Param("username") String username, @Param("password") String password);

    boolean changpasswordadmin(@Param("username") String username, @Param("password") String password);

    List<User> searchuser(@Param("mohu") String mohu, @Param("details") String details);

    List<User> getuserbyname(String username);//测试成功

    List<User> getalluser();

    List<admin> getadmin(String username);

    List<FilePath> getallpath();//测试成功

    List<FilePath> getpathadmin(@Param("type") String type, @Param("username") String username, @Param("filename") String filename, @Param("details") String details);

    List<FilePath> getpathbyusernametype(@Param("username") String username, @Param("type") String type);//测试成功

    List<FilePath>getpathbyusername(@Param("username") String username);//测试成功

}
