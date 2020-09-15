package com.mujeeb.mosquedashboard.repository;

import com.mujeeb.mosquedashboard.entity.Masjid;
import com.mujeeb.mosquedashboard.entity.Occasion;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface OccasionRepository extends CrudRepository<Occasion, Long> {

    List<Occasion> findByMasjidId(Integer masjidId);

    Occasion findById(int id);
}
