package com.mujeeb.mosquedashboard.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Masjid {

    @Id
    // @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column
    private String description;

    @Column
    private String password;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    private Masjid() {}

    public Masjid(String description, String password, Double latitude, Double longitude, Date createdAt, Date updatedAt) {
        this.description = description;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Masjid(Integer id, String description, String password, Double latitude, Double longitude, Date createdAt, Date updatedAt) {
        this.id = id;
        this.description = description;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Masjid{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", password='" + password + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
