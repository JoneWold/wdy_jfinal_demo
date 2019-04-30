package com.wdy.vo;

import com.wdy.generator.postgreSQL.model.User;
import com.wdy.generator.postgreSQL.model.UserRolePermission;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


/**
 * create_time:2019/3/12
 *
 * @author 刘浩然
 */
@Data
@ToString
@NoArgsConstructor
public class UserVo implements Serializable {

    private static final long serialVersionUID = 6583234580161864965L;
    /***
     * 用户对象
     * */
    private User user;
    /***
     * 用于权限对象
     * */
    private UserRolePermission userRolePermission;
    /***
     * 用户登录token
     * */
    private String token;
}
