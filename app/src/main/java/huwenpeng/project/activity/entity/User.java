package huwenpeng.project.activity.entity;

/**
 * Created by Administrator on 2017/1/8 0008.
 */
public class User {

    private String Head;
    private String name;
    private String singature;

    @Override
    public String toString() {
        return "User{" +
                "Head='" + Head + '\'' +
                ", name='" + name + '\'' +
                ", singature='" + singature + '\'' +
                '}';
    }

    public String getHead() {
        return Head;
    }

    public void setHead(String head) {
        Head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSingature() {
        return singature;
    }

    public void setSingature(String singature) {
        this.singature = singature;
    }
}
