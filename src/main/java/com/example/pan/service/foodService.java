package com.example.pan.service;

import com.example.pan.entity.FilePath;
import com.example.pan.mapper.FoodMapper_test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author daShen
 * @since 2022-04-04
 */
@Service
public class foodService {
    @Autowired
    private FoodMapper_test foodMapper;

    public List<FilePath> getallpathdivide(String username, String type) {
        return foodMapper.getpathbyusernametype(username, type);
    }

    public List getallpath(String username) {
        return  foodMapper.getpathbyusername(username);
    }

    public boolean deletefile(String username,String filename) {
        return foodMapper.detelefile(username,filename);
    }


}
