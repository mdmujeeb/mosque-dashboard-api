package com.mujeeb.mosquedashboard.entity;

import javax.persistence.*;

@Entity
public class RamzanTime {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column
    private Integer date;

    @Column
    private String seheri;

    @Column
    private String iftar;

    @Column
    private Integer masjidId;

    public RamzanTime() {
    }

    public RamzanTime(Integer date, String seheri, String iftar, Integer masjidId) {
        this.date = date;
        this.seheri = seheri;
        this.iftar = iftar;
        this.masjidId = masjidId;
    }

    @Override
    public String toString() {
        return "RamzanTime{" +
                "id=" + id +
                ", date=" + date +
                ", seheri='" + seheri + '\'' +
                ", iftar='" + iftar + '\'' +
                ", masjidId=" + masjidId +
                '}';
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getSeheri() {
        return seheri;
    }

    public void setSeheri(String seheri) {
        this.seheri = seheri;
    }

    public String getIftar() {
        return iftar;
    }

    public void setIftar(String iftar) {
        this.iftar = iftar;
    }

    public Integer getMasjidId() {
        return masjidId;
    }

    public void setMasjidId(Integer masjidId) {
        this.masjidId = masjidId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
