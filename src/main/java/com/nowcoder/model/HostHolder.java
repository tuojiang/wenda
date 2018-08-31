package com.nowcoder.model;

import org.springframework.stereotype.Component;

/**
 * @Date: 18-8-31
 * @version： V1.0
 * @Author: Chandler
 * @Description: ${todo}
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<>();

    public User getUsers() {
        return users.get();
    }

    public void setUsers(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }
}
