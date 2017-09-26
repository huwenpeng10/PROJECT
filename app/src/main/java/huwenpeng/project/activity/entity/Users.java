package huwenpeng.project.activity.entity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/8 0008.
 */
public class Users {
    private ArrayList<User> userinfo;

    @Override
    public String toString() {
        return "Users{" +
                "userinfo=" + userinfo +
                '}';
    }

    public ArrayList<User> getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(ArrayList<User> userinfo) {
        this.userinfo = userinfo;
    }
}
