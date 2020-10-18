package com.mujeeb.mosquedashboard.controller;

import com.mujeeb.mosquedashboard.beans.BaseException;
import com.mujeeb.mosquedashboard.beans.DateBean;
import com.mujeeb.mosquedashboard.beans.TempreatureBean;
import com.mujeeb.mosquedashboard.beans.request.BaseRequestBean;
import com.mujeeb.mosquedashboard.beans.request.NamazTimeUpdateRequestBean;
import com.mujeeb.mosquedashboard.beans.request.OccasionRequestBean;
import com.mujeeb.mosquedashboard.beans.request.UpdateRefreshRequiredBean;
import com.mujeeb.mosquedashboard.beans.response.AddOccasionResponseBean;
import com.mujeeb.mosquedashboard.beans.response.BaseResponseBean;
import com.mujeeb.mosquedashboard.entity.Masjid;
import com.mujeeb.mosquedashboard.entity.Occasion;
import com.mujeeb.mosquedashboard.repository.*;
import com.mujeeb.mosquedashboard.service.MasjidService;
import com.mujeeb.mosquedashboard.util.DateUtil;
import com.mujeeb.mosquedashboard.util.IslamicUtil;
import com.mujeeb.mosquedashboard.util.WeatherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class MosqueDashboardController {

    @Autowired
    private MasjidService masjidService;

    @Autowired
    private NamazTimeRepository namazTimeRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private OccasionRepository occasionRepository;

    @Autowired
    private QuranAyatRepository quranAyatRepository;

    @Autowired
    private RamzanTimeRepository ramzanTimeRepository;

    @GetMapping(value = "/getDataForMobileApp", produces = "application/json")
    public Map<String,Object> getDataForMobileApp(@RequestParam String id) {

        int masjidId = 1;
        // Make sure ID contains a valid integer
        try {
            masjidId = Integer.parseInt(id);
        }catch(Exception ex) {
            ex.printStackTrace();
        }

        Map<String,String> namazTimes = getNamazTimes("" + masjidId);
        TempreatureBean temp = getCurrentTempreature("" + masjidId);
        DateBean hijriDate = getHijriDate("" + masjidId);
        List<Occasion> occasions = getOccasions("" + masjidId);
        String masjidName = getMasjidName("" + masjidId);

        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("namazTimes", namazTimes);
        returnMap.put("temperature", temp);
        returnMap.put("hijriDate", hijriDate);
        returnMap.put("occasions", occasions);
        returnMap.put("masjidName", masjidName);

        return returnMap;
    }

    @GetMapping(value = "/resetUpdateRefreshRequired", produces = "application/json")
    public BaseResponseBean resetUpdateRefreshRequired(@RequestParam String id) {

        int masjidId = 1;
        // Make sure ID contains a valid integer
        try {
            masjidId = Integer.parseInt(id);
        }catch(Exception ex) {
            ex.printStackTrace();
        }

        try {
            boolean result = masjidService.updateNamazTime(masjidId, "REFRESH_REQUIRED", "false");

            if (result) {

                return new BaseResponseBean(0, "Reset Successful.");
            } else {

                return new BaseResponseBean(2);
            }
        }catch(Throwable ex) {
            return new BaseResponseBean(2);
        }
    }

    @GetMapping(value = "/getMasjidName", produces = "application/json")
    public String getMasjidName(@RequestParam String id) {

        int masjidId = 1;
        // Make sure ID contains a valid integer
        try {
            masjidId = Integer.parseInt(id);
        }catch(Exception ex) {
            ex.printStackTrace();
        }

        return masjidService.getMasjidName(masjidId);
    }

    @GetMapping(value = "/getNamazTimes", produces = "application/json")
    public Map<String,String> getNamazTimes(@RequestParam String id) {

        int masjidId = 1;
        // Make sure ID contains a valid integer
        try {
            masjidId = Integer.parseInt(id);
        }catch(Exception ex) {
            ex.printStackTrace();
        }

        Map<String,String> namazTimes = masjidService.getNamazTimes(masjidId);

        Map<String,String> hijriNamazTimes = IslamicUtil.getPrayerTimes();
        namazTimes.put("SEHERI", hijriNamazTimes.get("Fajr"));
        namazTimes.put("ISHRAQ", hijriNamazTimes.get("Ishraq"));
        namazTimes.put("MAGRIB", hijriNamazTimes.get("Maghrib"));
        namazTimes.put("IFTAR", hijriNamazTimes.get("Iftar"));

//        RamzanTime ramzanTime = masjidService.getRamzanTime(masjidId);
//        namazTimes.put("SEHERI", ramzanTime.getSeheri());
//        namazTimes.put("IFTAR", ramzanTime.getIftar());

        return namazTimes;
    }

    @GetMapping(value = "/getOccasions", produces = "application/json")
    public List<Occasion> getOccasions(@RequestParam String id) {

        int masjidId = 1;
        // Make sure ID contains a valid integer
        try {
            masjidId = Integer.parseInt(id);
        }catch(Exception ex) {
            id = "0";
        }

        try {
            return masjidService.getOccasions(masjidId);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<Occasion>();
    }

    @GetMapping(value = "/getCurrentTempreature", produces = "application/json")
    public TempreatureBean getCurrentTempreature(@RequestParam String id) {

        int masjidId = 1;
        // Make sure ID contains a valid integer
        try {
            masjidId = Integer.parseInt(id);
        }catch(Exception ex) {
            ex.printStackTrace();
        }

        try {
            Masjid location = masjidService.findMasjidByMasjidId(masjidId);
            System.out.println(location);
            return WeatherUtil.getCurrentTempreature(location.getLatitude().toString(), location.getLongitude().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new TempreatureBean("25", "cyan");
        }
    }

    @GetMapping(value = "/getHijriDate", produces = "application/json")
    public DateBean getHijriDate(@RequestParam String id) {

        int masjidId = 1;
        // Make sure ID contains a valid integer
        try {
            masjidId = Integer.parseInt(id);
        }catch(Exception ex) {
            ex.printStackTrace();
        }

        int adjustment = masjidService.getHijriAdjustment(masjidId);
        DateBean date = IslamicUtil.getHijriDate(adjustment);
	   /*if(adjustment < 0) {
	   		date.setDate("" + (Integer.parseInt(date.getDate()) - adjustment));
   	   } else if(adjustment > 0) {
	   		date.setDate("" + (Integer.parseInt(date.getDate()) + adjustment));
   	   }*/
        String occasion = masjidService.getCurrentOccasion(masjidId);
        if(occasion != null) {

            date.setYear(occasion);
            date.setMonth("OCCASION");
        }

        return date;
    }

    @PostMapping(value = "/authenticate", consumes = "application/json", produces="application/json")
    public BaseResponseBean authenticate(@RequestBody BaseRequestBean bean) {
        try {
            // Make sure ID contains a valid integer
            int masjidId = 1;
            try {
                masjidId = Integer.parseInt(bean.getUserId());
            }catch(Throwable ex) {
                throw new BaseException(1);
            }

            masjidService.authenticateUser(masjidId, bean.getPassword());

            return new BaseResponseBean(0, "Authentication Successful.");

        } catch(BaseException b) {

            return new BaseResponseBean(b.getReasonCode());
        }
    }

    @PostMapping(value = "/updateNamazTime", consumes = "application/json", produces="application/json")
    public BaseResponseBean updateNamazTime(@RequestBody NamazTimeUpdateRequestBean bean) {
        try {
            // Make sure ID contains a valid integer
            int masjidId = 1;
            try {
                masjidId = Integer.parseInt(bean.getUserId());
            }catch(Throwable ex) {
                throw new BaseException(1);
            }

            masjidService.authenticateUser(masjidId, bean.getPassword());

            boolean result = masjidService.updateNamazTime(masjidId, bean.getName(), bean.getTime());

            if(result) {

                return new BaseResponseBean(0, "Namaz time updated successfully.");
            } else {

                return new BaseResponseBean(2);
            }

        } catch(BaseException b) {

            return new BaseResponseBean(b.getReasonCode());
        } catch(Throwable ex) {

            ex.printStackTrace();
            return new BaseResponseBean(-1);
        }
    }

    @PostMapping(value = "/updateRefreshRequired", consumes = "application/json", produces="application/json")
    public BaseResponseBean updateRefreshRequired(@RequestBody UpdateRefreshRequiredBean bean) {
        try {
            // Make sure ID contains a valid integer
            int masjidId = 1;
            try {
                masjidId = Integer.parseInt(bean.getUserId());
            }catch(Throwable ex) {
                throw new BaseException(1);
            }

//            masjidService.authenticateUser(masjidId, bean.getPassword());

            boolean result = masjidService.updateRefreshRequired(masjidId, Boolean.parseBoolean(bean.getRefreshRequired()));

            if(result) {

                return new BaseResponseBean(0, "Request for Refresh page submitted Successfully.");
            } else {

                return new BaseResponseBean(3);
            }

        } catch(BaseException b) {

            return new BaseResponseBean(b.getReasonCode());
        } catch(Throwable ex) {

            ex.printStackTrace();
            return new BaseResponseBean(-1);
        }
    }

    @PostMapping(value = "/addOccasion", consumes = "application/json", produces="application/json")
    public BaseResponseBean addOccasion(@RequestBody OccasionRequestBean bean) {
        try {
            // Make sure ID contains a valid integer
            int masjidId = 1;
            try {
                masjidId = Integer.parseInt(bean.getUserId());
            }catch(Throwable ex) {
                throw new BaseException(1);
            }

            masjidService.authenticateUser(masjidId, bean.getPassword());

            Date date = DateUtil.parseDate(bean.getDate());

            String id = masjidService.addOccasion(masjidId, date, bean.getDescription());

            if(id != null) {
                return new AddOccasionResponseBean(0, "Occasion was Added Successfully.", id);

            } else {
                return new BaseResponseBean(5);
            }

        } catch(BaseException b) {

            return new BaseResponseBean(b.getReasonCode());
        } catch(Throwable ex) {

            ex.printStackTrace();
            return new BaseResponseBean(6);
        }
    }

    @PostMapping(value = "/deleteOccasion", consumes = "application/json", produces="application/json")
    public BaseResponseBean deleteOccasion(@RequestBody OccasionRequestBean bean) {
        try {
            // Make sure ID contains a valid integer
            int masjidId = 1;
            try {
                masjidId = Integer.parseInt(bean.getUserId());
            }catch(Throwable ex) {
                throw new BaseException(1);
            }

            masjidService.authenticateUser(masjidId, bean.getPassword());

            boolean result = masjidService.deleteOccasion(bean.getId());

            if(result) {
                return new BaseResponseBean(0, "Occasion was Deleted Successfully.");

            } else {
                return new BaseResponseBean(4);
            }

        } catch(BaseException b) {

            return new BaseResponseBean(b.getReasonCode());
        } catch(Throwable ex) {

            ex.printStackTrace();
            return new BaseResponseBean(6);
        }
    }
}
