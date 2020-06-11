package com.huanzong.property.database;

public class JuheBean {
    private String data;
    private int h_id;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getH_id() {
        return h_id;
    }

    public void setH_id(int h_id) {
        this.h_id = h_id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JuheBean{" +
                "data='" + data + '\'' +
                ", h_id=" + h_id +
                '}';
    }
}
