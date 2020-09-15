package com.mujeeb.mosquedashboard.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class NamazTime {

    @Id
    // @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;

    @Column
    private String time;

    @Column
    private Integer masjidId;

    public NamazTime() {
    }

    public NamazTime(String name, String time, Integer masjidId) {
        this.name = name;
        this.time = time;
        this.masjidId = masjidId;
    }

    public NamazTime(Integer id, String name, String time, Integer masjidId) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.masjidId = masjidId;
    }

    @Override
    public String toString() {
        return "NamazTime{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", masjidId=" + masjidId +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getMasjidId() {
        return masjidId;
    }

    public void setMasjidId(Integer masjidId) {
        this.masjidId = masjidId;
    }
}
