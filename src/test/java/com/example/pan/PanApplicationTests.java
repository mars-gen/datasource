package com.example.pan;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.pan.entity.admin;
import com.example.pan.mapper.FoodMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class PanApplicationTests {

    @Autowired
    private DataSource dataSource;
    @Resource
    private FoodMapper foodMapper;
    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);

        DruidDataSource druidDataSource = (DruidDataSource) dataSource;
        System.out.println(druidDataSource.getMaxActive());
        System.out.println(druidDataSource.getInitialSize());
        connection.close();

        List<admin> list = foodMapper.selectList(null);
        list.forEach(System.out::println);
    }

}
