package com.xian.model.activity.dtos;

import lombok.Data;

/**
 * @Author: jjxian
 */
@Data
public class ActivitySignDTO {
    private Integer id;
    private Integer activityId;
    private Integer userId;
    private String time;
    private String activityName;
    private String userName;
}
