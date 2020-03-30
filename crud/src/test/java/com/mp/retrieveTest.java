package com.mp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mp.dao.UserMapper;
import com.mp.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Lining
 * @date 2020/3/30 18:54
 */
public class retrieveTest {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void selectMy(){
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.<User>lambdaQuery();
        lambdaQuery.likeRight(User::getName,"王")
                .and(lqw->lqw.lt(User::getAge,40).or().isNotNull(User::getEmail));

        List<User> userList = userMapper.selectList(lambdaQuery);
        userList.forEach(System.out::println);
    }
    @Test
    public void selectPage(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.ge("age",26);

        Page<User> page = new Page<User>(1,2);

        IPage<User> iPage = userMapper.selectPage(page,queryWrapper);
        System.out.println("总页数"+iPage.getPages());
        System.out.println("总记录数"+iPage.getTotal());
        List<User> userList = iPage.getRecords();
        userList.forEach(System.out::println);
    }
}
