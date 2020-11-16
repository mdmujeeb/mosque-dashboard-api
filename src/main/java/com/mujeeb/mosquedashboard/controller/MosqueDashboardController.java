package com.mujeeb.mosquedashboard.controller;

import java.util.*;
import com.mujeeb.mosquedashboard.beans.BaseException;
import com.mujeeb.mosquedashboard.beans.DateBean;
import com.mujeeb.mosquedashboard.beans.TempreatureBean;
import com.mujeeb.mosquedashboard.beans.request.BaseRequestBean;
import com.mujeeb.mosquedashboard.beans.request.NamazTimeUpdateRequestBean;
import com.mujeeb.mosquedashboard.beans.request.OccasionRequestBean;
import com.mujeeb.mosquedashboard.beans.request.UpdateRefreshRequiredBean;
import com.mujeeb.mosquedashboard.beans.request.EnquiryRequestBean;
import com.mujeeb.mosquedashboard.beans.request.OrderRequestBean;
import com.mujeeb.mosquedashboard.beans.request.QRCodeUpdateRequestBean;
import com.mujeeb.mosquedashboard.beans.response.AddOccasionResponseBean;
import com.mujeeb.mosquedashboard.beans.response.AllQRCodesResponseBean;
import com.mujeeb.mosquedashboard.beans.response.QRCodeResponseBean;
import com.mujeeb.mosquedashboard.beans.response.BaseResponseBean;
import com.mujeeb.mosquedashboard.entity.Masjid;
import com.mujeeb.mosquedashboard.entity.Occasion;
import com.mujeeb.mosquedashboard.repository.*;
import com.mujeeb.mosquedashboard.service.MasjidService;
import com.mujeeb.mosquedashboard.util.DateUtil;
import com.mujeeb.mosquedashboard.util.IslamicUtil;
import com.mujeeb.mosquedashboard.util.WeatherUtil;
import com.mujeeb.mosquedashboard.util.EmailUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        List<String> masjidList = getMasjidList();

        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("namazTimes", namazTimes);
        returnMap.put("temperature", temp);
        returnMap.put("hijriDate", hijriDate);
        returnMap.put("occasions", occasions);
        returnMap.put("masjidName", masjidName);
        returnMap.put("masjidList", masjidList);

        return returnMap;
    }

    @GetMapping(value = "/getMasjidList", produces = "application/json")
    public List<String> getMasjidList() {

        try {
            return masjidService.getMasjidList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<String>();
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
        boolean isPostMagrib = IslamicUtil.isPostMagrib();
        adjustment = isPostMagrib ? adjustment+1 : adjustment;
        DateBean date = IslamicUtil.getHijriDate(adjustment);
        String occasion = masjidService.getCurrentOccasion(masjidId, isPostMagrib);
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

    /***************************** Email Related Methods *****************************/

    @PostMapping(value = "/sendEnquiryEmail", consumes = "application/json", produces="application/json")
    public BaseResponseBean sendEnquiryEmail(@RequestBody EnquiryRequestBean bean) {

            StringBuilder builder = new StringBuilder("<html>\n");
            builder.append("Name: ").append(bean.getName()).append("<br>")
                .append("Phone: ").append(bean.getPhone()).append("<br>")
                .append("Email: ").append(bean.getEmail()).append("<br>")
                .append("Enquiry: ").append(bean.getEnquiry()).append("<br>")
                .append("</html>");
            if(EmailUtil.sendEMail("Enquiry about Mosque Dashboard", builder.toString())) {
                return new BaseResponseBean(0, "Enquiry was sent Successfully.");
            } else {
                return new BaseResponseBean(4);
            }
    }

    @PostMapping(value = "/sendOrderEmail", consumes = "application/json", produces="application/json")
    public BaseResponseBean sendOrderEmail(@RequestBody OrderRequestBean bean) {

        StringBuilder builder = new StringBuilder("<html>\n");
        builder.append("Name: ").append(bean.getName()).append("<br>")
                .append("Phone: ").append(bean.getPhone()).append("<br>")
                .append("Email: ").append(bean.getEmail()).append("<br>")
                .append("Address: ").append(bean.getAddress1()).append(", ").append(bean.getAddress2()).append(", ")
                        .append(bean.getCity()).append(" - ").append(bean.getPinCode()).append(", ").append(bean.getState()).append("<br>")
                .append("TV: ").append(bean.getTv()).append("<br>")
                .append("WiFi: ").append(bean.getWifi()).append("<br>")
                .append("Special Instructions: ").append(bean.getSpecialInstructions()).append("<br>")
                .append("</html>");
        if(EmailUtil.sendEMail("New Order - Mosque Dashboard", builder.toString())) {
            return new BaseResponseBean(0, "Order Email was sent Successfully.");
        } else {
            return new BaseResponseBean(4);
        }
    }

    /***************************** Sadaqa Related Methods *****************************/
    public static final String QR_CODE_PREFIX = "MOHAMMAD.MUJEEB@GMAIL.COM@";
    private static Map<String,String> qrCodes = new HashMap<String,String>();

    @PostMapping(value = "/enableQRCode", consumes = "application/json", produces="application/json")
    public BaseResponseBean enableQRCode(@RequestBody QRCodeUpdateRequestBean bean) {

        try {
            // Make sure ID contains a valid integer
            int masjidId = 1;
            try {
                masjidId = Integer.parseInt(bean.getUserId());
            }catch(Throwable ex) {
                throw new BaseException(1);
            }

            masjidService.authenticateUser(masjidId, bean.getPassword());

            boolean isEnable = "true".equalsIgnoreCase(bean.getEnable());

            if(isEnable) {
                qrCodes.put(bean.getQrCode(), "true");
            } else {
                qrCodes.put(bean.getQrCode(), "false");
            }

            return new BaseResponseBean(0, "QR Code was " + (isEnable ? "enabled" : "disabled") + " Successfully.");

        } catch(BaseException b) {

            return new BaseResponseBean(b.getReasonCode());
        } catch(Throwable ex) {

            ex.printStackTrace();
            return new BaseResponseBean(6);
        }
    }

    @PostMapping(value = "/disableALLQRCodes", consumes = "application/json", produces="application/json")
    public BaseResponseBean disableALLQRCodes(@RequestBody BaseRequestBean bean) {

        try {
            // Make sure ID contains a valid integer
            int masjidId = 1;
            try {
                masjidId = Integer.parseInt(bean.getUserId());
            }catch(Throwable ex) {
                throw new BaseException(1);
            }

            masjidService.authenticateUser(masjidId, bean.getPassword());

            qrCodes.forEach((key,value) -> {
                if("true".equals(value)) {
                    qrCodes.put(key, "false");
                }
            });

            return new BaseResponseBean(0, "All QR Codes were disabled Successfully.");

        } catch(BaseException b) {

            return new BaseResponseBean(b.getReasonCode());
        } catch(Throwable ex) {

            ex.printStackTrace();
            return new BaseResponseBean(6);
        }
    }

    @GetMapping(value = "/getQRCodeStatus", produces = "application/json")
    public BaseResponseBean getQRCodeStatus(@RequestParam String id) {

        if(id == null || id.isEmpty() || !id.startsWith(QR_CODE_PREFIX)) {
            return new BaseResponseBean(56);
        }

        String value = qrCodes.get(id);

        if(value == null || !value.equals("true")) {
            return new BaseResponseBean(57);
        }

        return new QRCodeResponseBean(id, "true");
    }

    @GetMapping(value = "/getAllActiveQRCodes", produces = "application/json")
    public BaseResponseBean getAllActiveQRCodes() {

        List<QRCodeResponseBean> returnList = new ArrayList<QRCodeResponseBean>();
        qrCodes.forEach((key,value) -> {
            if("true".equals(value)) {
                returnList.add(new QRCodeResponseBean(key, value));
            }
        });

        return new AllQRCodesResponseBean(returnList);
    }

    @GetMapping(value = "/getAndDisableQRCodeStatus", produces = "application/json")
    public BaseResponseBean getAndDisableQRCodeStatus(@RequestParam String id) {

        if(id == null || id.isEmpty() || !id.startsWith(QR_CODE_PREFIX)) {
            return new BaseResponseBean(56);
        }

        String value = qrCodes.get(id);

        if(value == null || !value.equals("true")) {
            return new BaseResponseBean(57);
        }

        new Thread(){
            public void run() {
                sendQRCodeRedeemedEmail(id);
            }
        }.start();
        qrCodes.put(id, "false");
        return new QRCodeResponseBean(id, "true");
    }

    private void sendQRCodeRedeemedEmail(String id) {
        StringBuilder builder = new StringBuilder("<html>\n");
        builder.append("QR Code: ").append(id).append("<br>")
                .append("Date & Time: ").append(DateUtil.formatTime(new Date())).append("<br>");
        EmailUtil.sendEMail("QR Code Redeemed", builder.toString());
    }
}
