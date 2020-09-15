package com.mujeeb.mosquedashboard.util;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NoticeMapper implements RowMapper<String> {

	@Override
	public String mapRow(ResultSet result, int index) throws SQLException {
		return result.getString("NOTICE");
	}

}
