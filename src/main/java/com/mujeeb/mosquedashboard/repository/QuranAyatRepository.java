package com.mujeeb.mosquedashboard.repository;

import com.mujeeb.mosquedashboard.entity.Masjid;
import com.mujeeb.mosquedashboard.entity.QuranAyat;
import org.springframework.data.repository.CrudRepository;

public interface QuranAyatRepository extends CrudRepository<QuranAyat, Long> {

    QuranAyat findById(int id);
}
