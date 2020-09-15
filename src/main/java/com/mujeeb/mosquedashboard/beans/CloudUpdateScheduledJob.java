package com.mujeeb.mosquedashboard.beans;

import com.mujeeb.mosquedashboard.util.SheetsQuickstart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;

public class CloudUpdateScheduledJob {

	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Scheduled(fixedDelay=60000)
	public void getDataFromCloud() {
		System.out.println("Getting data from Cloud...");
		try {
			new SheetsQuickstart().getSpreadSheetData(jdbcTemplate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
