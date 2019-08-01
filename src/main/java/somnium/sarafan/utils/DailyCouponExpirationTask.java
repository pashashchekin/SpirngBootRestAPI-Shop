package somnium.sarafan.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import somnium.sarafan.domain.Coupon;
import somnium.sarafan.repository.CouponRepository;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Component
public class DailyCouponExpirationTask {

    @Autowired
    CouponRepository couponsRepository;

    private static final Logger log = LoggerFactory.getLogger(DailyCouponExpirationTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "0 0-15 14 * * *")
    public void reportCurrentTime() {
        Collection<Coupon> coupons = couponsRepository.findAll();
        Date startDate = new Date();
        for (Coupon coupon : coupons) {
            if (coupon.getEndDate().before(startDate)) {
                log.info("Coupon is deleted, ID: {}", coupon.getId());
                couponsRepository.delete(coupon);
            }
        }
    }
}