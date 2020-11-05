package com.mujeeb.mosquedashboard;

import com.mujeeb.mosquedashboard.entity.*;
import com.mujeeb.mosquedashboard.repository.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

@SpringBootApplication
public class MosqueDashboardApplication extends SpringBootServletInitializer {

	public static String CSV_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSSSSS";
	public static final SimpleDateFormat dateFormatter = new SimpleDateFormat(CSV_DATE_FORMAT);

	@Autowired
	private ResourceLoader resourceLoader;

	public static void main(String[] args) {
		SpringApplication.run(MosqueDashboardApplication.class, args);
	}

	@Bean
	public CommandLineRunner masjidsLoader(MasjidRepository repository) {

		return (args) -> {

			try {
				InputStreamReader input = new InputStreamReader(resourceLoader.getResource("classpath:masjid_master.csv").getInputStream());
				CSVParser csvParser = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(input);
				for (CSVRecord record : csvParser) {
					String id = record.get("ID");
					String description = record.get("DESCRIPTION");
					String masjidId = record.get("MASJID_ID");
					String password = record.get("PASSWORD");
					String latitude = record.get("GPS_LATITUDE");
					String longitude = record.get("GPS_LONGITUDE");
					String createdAt = record.get("DATE_CREATED");
					String updatedAt = record.get("DATE_UPDATED");

					repository.save(new Masjid(Integer.parseInt(masjidId)
												, description
												, password
												, Double.parseDouble(latitude)
												, Double.parseDouble(longitude)
												, dateFormatter.parse(createdAt)
					                            , dateFormatter.parse(updatedAt)));
				}
			} catch(Exception ex) {

				ex.printStackTrace();
			}
		};
	}

	@Bean
	public CommandLineRunner namazTimesLoader(NamazTimeRepository repository) {

		return (args) -> {

			try {
				InputStreamReader input = new InputStreamReader(resourceLoader.getResource("classpath:namaz_times.csv").getInputStream());
				CSVParser csvParser = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(input);
				for (CSVRecord record : csvParser) {
					String id = record.get("ID");
					String name = record.get("NAME");
					String time = record.get("TIME");
					String masjidId = record.get("MASJID_ID");

					repository.save(new NamazTime(Integer.parseInt(id)
							, name
							, time
							, Integer.parseInt(masjidId)));
				}
			} catch(Exception ex) {

				ex.printStackTrace();
			}
		};
	}

	@Bean
	public CommandLineRunner noticesLoader(NoticeRepository repository) {

		return (args) -> {

			try {
				InputStreamReader input = new InputStreamReader(resourceLoader.getResource("classpath:notices.csv").getInputStream());
				CSVParser csvParser = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(input);
				for (CSVRecord record : csvParser) {
					String id = record.get("ID");
					String name = record.get("NOTICE");
					String masjidId = record.get("MASJID_ID");

					repository.save(new Notice(Integer.parseInt(id)
							, name
							, Integer.parseInt(masjidId)));
				}
			} catch(Exception ex) {

				ex.printStackTrace();
			}
		};
	}

	@Bean
	public CommandLineRunner occasionsLoader(OccasionRepository repository) {

		return (args) -> {

			try {
				InputStreamReader input = new InputStreamReader(resourceLoader.getResource("classpath:occasions.csv").getInputStream());
				CSVParser csvParser = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(input);
				for (CSVRecord record : csvParser) {
					String id = record.get("ID");
					String description = record.get("DESCRIPTION");
					String date = record.get("OCCASION_DATE");
					String masjidId = record.get("MASJID_ID");

					repository.save(new Occasion(Long.parseLong(id)
							, description
							, dateFormatter.parse(date)
							, Integer.parseInt(masjidId)));
				}
			} catch(Exception ex) {

				ex.printStackTrace();
			}
		};
	}

	@Bean
	public CommandLineRunner QuranAyatLoader(QuranAyatRepository repository) {

		return (args) -> {

			try {
				InputStreamReader input = new InputStreamReader(resourceLoader.getResource("classpath:quran_ayats.csv").getInputStream());
				CSVParser csvParser = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(input);
				for (CSVRecord record : csvParser) {
					String id = record.get("ID");
					String ayat = record.get("AYAT");

					repository.save(new QuranAyat(Integer.parseInt(id)
							, ayat));
				}
			} catch(Exception ex) {

				ex.printStackTrace();
			}
		};
	}

	@Bean
	public CommandLineRunner ramzanTimeTableLoader(RamzanTimeRepository repository) {

		return (args) -> {

			try {
				InputStreamReader input = new InputStreamReader(resourceLoader.getResource("classpath:ramzan_timetable.csv").getInputStream());
				CSVParser csvParser = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(input);
				for (CSVRecord record : csvParser) {
					String date = record.get("RAMZAN_DATE");
					String seheri = record.get("SEHERI");
					String iftar = record.get("IFTAR");
					String masjidId = record.get("MASJID_ID");

					repository.save(new RamzanTime(Integer.parseInt(date)
							, seheri
							, iftar
							, Integer.parseInt(masjidId)));
				}
			} catch(Exception ex) {

				ex.printStackTrace();
			}
		};
	}
}
