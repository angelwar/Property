package com.huanzong.property.database;

import java.io.Serializable;
import java.util.List;

public class ZushouBean1 implements Serializable {
    private int uid;
    private int id;
    private String addtime;
    private String xqmc;
    private String jzmj;
    private String fwlx;
    private int cx;
    private String cxStr;
    private String lc;
    private int cw;
    private String cwStr;
    private int dt;
    private String fy;
    private String fkfs;
    private String zjbhfy;
    private String lxrxm;
    private String lxrsf;
    private String sjhm;
    private String tg;
    private String jtsd;
    private int status;
    private int zs;

    private String fwpz;
    private String xxjs;
    private String czyq;
    private String hx;
    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFwld() {
        return fwld;
    }

    public void setFwld(String fwld) {
        this.fwld = fwld;
    }

    private String fwld;


    public String getCwStr() {
        return cwStr;
    }

    public void setCwStr(String cwStr) {
        this.cwStr = cwStr;
    }

    public String getFwpz() {
        return fwpz;
    }

    public void setFwpz(String fwpz) {
        this.fwpz = fwpz;
    }

    public String getXxjs() {
        return xxjs;
    }

    public void setXxjs(String xxjs) {
        this.xxjs = xxjs;
    }

    public String getCzyq() {
        return czyq;
    }

    public void setCzyq(String czyq) {
        this.czyq = czyq;
    }

    public String getHx() {
        return hx;
    }

    public void setHx(String hx) {
        this.hx = hx;
    }

    private List<ImgBean> thumb;

    public String getCxStr() {
        switch (cx) {
            case 0:
                cxStr = "东";
                break;
            case 1:
                cxStr = "南";
                break;
            case 2:
                cxStr = "西";
                break;
            case 3:
                cxStr = "北";
                break;
            case 4:
                cxStr = "东南";
                break;
            case 5:
                cxStr = "东北";
                break;
            case 6:
                cxStr = "西南";
                break;
            case 7:
                cxStr = "西北";
                break;
            case 8:
                cxStr = "南北";
                break;
            case 9:
                cxStr = "东西";
                break;
            default:cxStr = "东";
                break;
        }
        return cxStr;
    }

    public void setCxStr(String cxStr) {
        this.cxStr = cxStr;
    }


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getXqmc() {
        return xqmc;
    }

    public void setXqmc(String xqmc) {
        this.xqmc = xqmc;
    }

    public String getJzmj() {
        return jzmj;
    }

    public void setJzmj(String jzmj) {
        this.jzmj = jzmj;
    }

    public String getFwlx() {
        return fwlx;
    }

    public void setFwlx(String fwlx) {
        this.fwlx = fwlx;
    }

    public int getCx() {
        return cx;
    }

    public void setCx(int cx) {
        this.cx = cx;
    }

    public String getLc() {
        return lc;
    }

    public void setLc(String lc) {
        this.lc = lc;
    }

    public int getCw() {
        return cw;
    }

    public void setCw(int cw) {
        this.cw = cw;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public String getFy() {
        return fy;
    }

    public void setFy(String fy) {
        this.fy = fy;
    }

    public String getFkfs() {
        return fkfs;
    }

    public void setFkfs(String fkfs) {
        this.fkfs = fkfs;
    }

    public String getZjbhfy() {
        return zjbhfy;
    }

    public void setZjbhfy(String zjbhfy) {
        this.zjbhfy = zjbhfy;
    }

    public String getLxrxm() {
        return lxrxm;
    }

    public void setLxrxm(String lxrxm) {
        this.lxrxm = lxrxm;
    }

    public String getLxrsf() {
        return lxrsf;
    }

    public void setLxrsf(String lxrsf) {
        this.lxrsf = lxrsf;
    }

    public String getSjhm() {
        return sjhm;
    }

    public void setSjhm(String sjhm) {
        this.sjhm = sjhm;
    }

    public String getTg() {
        return tg;
    }

    public void setTg(String tg) {
        this.tg = tg;
    }

    public String getJtsd() {
        return jtsd;
    }

    public void setJtsd(String jtsd) {
        this.jtsd = jtsd;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getZs() {
        return zs;
    }

    public void setZs(int zs) {
        this.zs = zs;
    }

    public List<ImgBean> getThumb() {
        return thumb;
    }

    public void setThumb(List<ImgBean> thumb) {
        this.thumb = thumb;
    }
}
