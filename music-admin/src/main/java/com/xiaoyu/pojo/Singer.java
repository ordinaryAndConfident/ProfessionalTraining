package com.xiaoyu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Singer {

    private Integer id;
    private String name;
    private Integer sex;
    private String avatar;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String location;
    private String introduction;
}
