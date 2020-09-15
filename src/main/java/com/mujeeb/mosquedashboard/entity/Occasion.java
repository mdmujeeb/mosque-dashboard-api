package com.mujeeb.mosquedashboard.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Occasion {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column
    private String description;

    @Column
    private Date date;

    @Column
    private Integer masjidId;

    public Occasion() {
    }

    public Occasion(String description, Date date, Integer masjidId) {
        this.description = description;
        this.date = date;
        this.masjidId = masjidId;
    }

    public Occasion(Long id, String description, Date date, Integer masjidId) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.masjidId = masjidId;
    }

    @Override
    public String toString() {
        return "Occasion{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", masjidId=" + masjidId +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getMasjidId() {
        return masjidId;
    }

    public void setMasjidId(Integer masjidId) {
        this.masjidId = masjidId;
    }
}
