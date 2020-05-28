package com.huanzong.property.activity.login;

import java.util.List;

public class LoginUserData {
    private int uid;
    private int pid;
    private List<XiaoQuData> xiaoqu;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public List<XiaoQuData> getXiaoqu() {
        return xiaoqu;
    }

    public void setXiaoqu(List<XiaoQuData> xiaoqu) {
        this.xiaoqu = xiaoqu;
    }
}
