package com.mujeeb.mosquedashboard.repository;

import com.mujeeb.mosquedashboard.entity.Masjid;
import com.mujeeb.mosquedashboard.entity.Notice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NoticeRepository extends CrudRepository<Notice, Long> {

    List<Notice> findByMasjidId(Integer masjidId);

    Notice findById(int id);
}
