package com.ling.framework.service;

import com.ling.framework.model.User;

/**
 * new class.
 *
 * @author 钟舒艺
 * @since 2022-10-27 14:43
 **/
public interface UserService {

    /**
     * 根据用户名获取用户.
     *
     * @param username 用户名
     * @return 用户信息
     */
    User getUserByUsername(String username);


    /**
     * 登录之后需要进行的操作.
     *
     * @param user 用户信息.
     */
    void afterLogin(User user);

}
