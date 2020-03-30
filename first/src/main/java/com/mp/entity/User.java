package com.mp.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Lining
 * @date 2020/3/30
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private Long managerld;
    private LocalDateTime createTime;
}
