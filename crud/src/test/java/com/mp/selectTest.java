
package com.mp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mp.dao.UserMapper;
import com.mp.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lining
 * @date 2020/3/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class selectTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void selectByld(){
        User user = userMapper.selectById(1094590409767661570L);
        System.out.println(user);
    }
    @Test
    public void selectlds(){//根据columnMap条件进行查询
        List<Long> idsList = Arrays.asList(1094592041087729666L,1094590409767661570L,1088248166370832385L);
        List<User> userList = userMapper.selectBatchIds(idsList);
        userList.forEach(System.out::println);
    }
    @Test
    public void selectByMap(){//根据entity条件查询
        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("name","王天风");
        columnMap.put("age",25);
        List<User> userList = userMapper.selectByMap(columnMap);
        userList.forEach(System.out::println);
    }

    /**
     * 名字中包含雨并且年龄小于40
     * name like '%雨%' and age < 40
     */
    @Test
    public void selectByWrapper01(){
        QueryWrapper<User>queryWrapper = new QueryWrapper<User>();
        queryWrapper.like("name","雨").lt("age",40);

        List<User>userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
    /**
     * 名字中包含雨并且年龄大于20且小于等于40并且email不为空
     * name like '%雨%' and age between 20 and 40 and email is not null
     */
    @Test
    public void selectByWrapper02(){
        QueryWrapper<User>queryWrapper = new QueryWrapper<User>();
        queryWrapper.like("name","雨").between("age",20,40).isNotNull("email");

        List<User>userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 名字为王姓或者年龄大于等于25，按照年龄降序排列，年龄相同的按照id升序排列
     * name like '王%' or age >= 40 order by age desc,id asc
     */
    @Test
    public void selectByWrapper03(){
        QueryWrapper<User>queryWrapper = new QueryWrapper<User>();
        queryWrapper.like("name","王").or().ge("age",25).orderByDesc("age").orderByAsc("id");

        List<User>userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 创建时间日期为2019.2.14并且直属上级为王姓
     * date_format(create_time,'%Y-%m-%d')and manager_id in (select id from user where name like '王%')
     */
    @Test
    public void selectByWrapper04(){
        QueryWrapper<User>queryWrapper = new QueryWrapper<User>();
        queryWrapper.apply("date_format(create_time,'%Y-%m-%d') ={0}","2019-02-14")
                .inSql("manager_id","select id from user where name like '王%'");

        List<User>userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
    /**
     * 名字王姓并且（年龄小于40或者邮箱不为空）
     * name like '王%' and (age<40 or email is not null)
     */
    @Test
    public void selectByWrapper05(){
        QueryWrapper<User>queryWrapper = new QueryWrapper<User>();
        queryWrapper.likeRight("name","王").and(wq->wq.lt("age",40)
                .or().isNotNull("email"));

        List<User>userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
    /**
     * 名字王姓或者（年龄小于40并且年龄大于20并且邮箱不为空）
     * name like '王%' or (age<40 and age > 20 and email is not null)
     */
    @Test
    public void selectByWrapper06(){
        QueryWrapper<User>queryWrapper = new QueryWrapper<User>();
        queryWrapper.likeRight("name","王").or(wq->wq.lt("age",40)
                .gt("age",20).isNotNull("email"));

        List<User>userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
    /**
     * (年龄小于40或者邮箱不为空)并且名字为王姓
     * （age<40 or email is not null ）and name like '王%'
     */
    @Test
    public void selectByWrapper07(){
        QueryWrapper<User>queryWrapper = new QueryWrapper<User>();
        queryWrapper.nested(wq->wq.lt("age",40)
                .or().isNotNull("email").likeRight("name","王"));

        List<User>userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
    /**
     * 年龄30、31、34、35
     * age in (30、31、34、35)
     */
    @Test
    public void selectByWrapper08(){
        QueryWrapper<User>queryWrapper = new QueryWrapper<User>();
        queryWrapper.in("age",Arrays.asList(30,31,34,35));

        List<User>userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
    /**
     * 只返回满足条件的其中一条语句即可
     * limit 1
     */
    @Test
    public void selectByWrapper09(){
        QueryWrapper<User>queryWrapper = new QueryWrapper<User>();
        queryWrapper.in("age",Arrays.asList(30,31,34,35)).last("limit 1");

        List<User>userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
    /**
     * 名字中包含雨并且年龄小于40
     * name like '%雨%' and age < 40
     */
    @Test
    public void selectByWrapperSupper01(){
        QueryWrapper<User>queryWrapper = new QueryWrapper<User>();
        queryWrapper.select("id","name").like("name","雨").lt("age",40);

        List<User>userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
    /**
     * 名字中包含雨并且年龄小于40
     * name like '%雨%' and age < 40
     */
    @Test
    public void selectByWrapperSupper02(){
        QueryWrapper<User>queryWrapper = new QueryWrapper<User>();
        queryWrapper.like("name","雨").lt("age",40).select(User.class,info->!info.getColumn()
                .equals("create_time")&&!info.getColumn().equals("manager_id"));

        List<User>userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

}
