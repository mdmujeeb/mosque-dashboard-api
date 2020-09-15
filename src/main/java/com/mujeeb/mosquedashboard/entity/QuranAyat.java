package com.mujeeb.mosquedashboard.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class QuranAyat {

    @Id
    // @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column
    private String ayat;

    public QuranAyat() {
    }

    public QuranAyat(String ayat) {
        this.ayat = ayat;
    }

    public QuranAyat(Integer id, String ayat) {
        this.id = id;
        this.ayat = ayat;
    }

    @Override
    public String toString() {
        return "QuranAyat{" +
                "id=" + id +
                ", ayat='" + ayat + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAyat() {
        return ayat;
    }

    public void setAyat(String ayat) {
        this.ayat = ayat;
    }
}
