package com.huanzong.property.database;

public class Visitor {
    //{"id":85382,"o_id":2581,"mobile":"15828297789","status":2,"ty":0,"create_time":1590897497,"cid":109,"name":"廖威","role":1,"foolor":"3-2","room":1105},
    private int id;
    private int o_id;
    private String mobile;
    private int status;
    private int ty;
    private long create_time;
    private int cid;
    private String name;
    private int role;
    private String foolor;
    private String room;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getO_id() {
        return o_id;
    }

    public void setO_id(int o_id) {
        this.o_id = o_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTy() {
        return ty;
    }

    public void setTy(int ty) {
        this.ty = ty;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
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
}
