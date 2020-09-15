package com.mujeeb.mosquedashboard.repository;

import com.mujeeb.mosquedashboard.entity.Masjid;
import com.mujeeb.mosquedashboard.entity.NamazTime;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NamazTimeRepository extends CrudRepository<NamazTime, Long> {

    NamazTime findByNameAndMasjidId(String name, Integer masjidId);

    List<NamazTime> findByMasjidId(Integer masjidId);

    NamazTime findById(int id);
}
