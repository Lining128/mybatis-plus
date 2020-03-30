package com.mp.entity;



import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author Lining
 * @date 2020/3/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends Model<User> {

    private static final long seriaVersionUID = 1L;


    private Long id;
    private String name;
    private Integer age;
    private String email;
    private Long managerld;
    private LocalDateTime createTime;
}
