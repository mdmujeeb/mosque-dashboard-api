package com.mujeeb.mosquedashboard.entity;

import javax.persistence.*;

@Entity
public class Notice {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column
    private String notice;

    @Column
    private Integer masjidId;

    public Notice() {
    }

    public Notice(String notice, Integer masjidId) {
        this.notice = notice;
        this.masjidId = masjidId;
    }

    public Notice(Integer id, String notice, Integer masjidId) {
        this.id = id;
        this.notice = notice;
        this.masjidId = masjidId;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", notice='" + notice + '\'' +
                ", masjidId=" + masjidId +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Integer getMasjidId() {
        return masjidId;
    }

    public void setMasjidId(Integer masjidId) {
        this.masjidId = masjidId;
    }
}
