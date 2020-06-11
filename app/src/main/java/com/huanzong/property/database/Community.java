package com.huanzong.property.database;

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