package com.ysq.mybatisplus.service.impl;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2022/4/13 10:24
 * @Version 1.0
 */

public class Student {
    private int id;
    private String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
