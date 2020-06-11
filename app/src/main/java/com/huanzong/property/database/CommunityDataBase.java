package com.huanzong.property.database;

import java.util.List;

public class CommunityDataBase {

    private int code;
    private String msg;
    private List<Community> info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Community> getInfo() {
        return info;
    }

    public void setInfo(List<Community> info) {
        this.info = info;
    }

    //"status":1,"msg":"\u5df2\u8ba4\u8bc1","role":1,"expire_time":0,"foolor":"2\u680b6\u5355\u5143","room":25,"c_id":17,"name":"\u6052\u5927\u7eff\u6d32","telephone":"02868740033"
    public class Community{

        private int c_id;
        private int status;
        private int role;
        private String msg;
        private String expire_time;
        private String foolor;
        private String room;
        private String name;
        private String telephone;

        public int getC_id() {
            return c_id;
        }

        public void setC_id(int c_id) {
            this.c_id = c_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getExpire_time() {
            return expire_time;
        }

        public void setExpire_time(String expire_time) {
            this.expire_time = expire_time;
        }

        public String getFoolor() {
            return foolor;
        }

        public void setFoolor(String foolor) {
            this.foolor = foolor;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }
    }
}
