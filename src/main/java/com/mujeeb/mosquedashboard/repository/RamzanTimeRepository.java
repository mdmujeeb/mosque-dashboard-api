package com.mujeeb.mosquedashboard.repository;

import com.mujeeb.mosquedashboard.entity.Masjid;
import com.mujeeb.mosquedashboard.entity.RamzanTime;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RamzanTimeRepository extends CrudRepository<RamzanTime, Long> {

    List<RamzanTime> findByMasjidId(int masjidId);

    RamzanTime findByDateAndMasjidId(Integer date, Integer masjidId);
}
