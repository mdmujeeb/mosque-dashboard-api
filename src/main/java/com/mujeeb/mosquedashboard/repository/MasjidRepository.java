package com.mujeeb.mosquedashboard.repository;

import com.mujeeb.mosquedashboard.entity.Masjid;
import org.springframework.data.repository.CrudRepository;

public interface MasjidRepository extends CrudRepository<Masjid, Long> {

    Masjid findById(int id);
}
