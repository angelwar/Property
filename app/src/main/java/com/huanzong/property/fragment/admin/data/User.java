package com.huanzong.property.fragment.admin.data;

public class User {
    //{"id":3971,"c_id":40,"u_id":3561,"name":"谢佳","mobile":"15828210769","pnum":"","role":3,"foolor":"22栋3单元","room":15,"ident":1,"expire_time":1574426586,"status":0,"sums":0,"c_name":"龙湖家园  "
    private int id;
    private int c_id;
    private int u_id;
    private int ident;
    private int role;
    private String pnum;
    private int sums;
    private int status;
    private String name;
    private String c_name;
    private String foolor;//=1栋2单元;
    private String mobile;
    private String room;
    private long expire_time;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getPnum() {
        return pnum;
    }

    public void setPnum(String pnum) {
        this.pnum = pnum;
    }

    public int getSums() {
        return sums;
    }

    public void setSums(int sums) {
        this.sums = sums;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getFoolor() {
        return foolor;
    }

    public void setFoolor(String foolor) {
        this.foolor = foolor;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public long getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(long expire_time) {
        this.expire_time = expire_time;
    }
}
