package somnium.sarafan.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import somnium.sarafan.domain.Coupon;
import somnium.sarafan.domain.User;
import somnium.sarafan.repository.CouponRepository;
import somnium.sarafan.repository.UserRepository;

import javax.jws.soap.SOAPBinding;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Component
public class DailyExpirationTask {

    @Autowired
    CouponRepository couponsRepository;

    @Autowired
    UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(DailyExpirationTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "0 0-15 14 * * *")
    public void deleteExpirationCoupons() {
        Collection<Coupon> coupons = couponsRepository.findAll();
        Date startDate = new Date();
        for (Coupon coupon : coupons) {
            if (coupon.getEndDate().before(startDate)) {
                log.info("Coupon is deleted, ID: {}", coupon.getId());
                couponsRepository.delete(coupon);
            }
        }
    }

    @Scheduled(cron = "0 0-10 15 * * *")
    public void activateResetPassword(){
        Collection<User> users = userRepository.findAll();
        Date startDate = new Date();
        for (User user : users){
            if (user.getPasswordResetDate() == null){
                user.setPasswordResetDate(startDate);
                userRepository.save(user);
            }
            if (user.getPasswordResetDate().before(startDate)){
                log.info("User {} can change password", user.getUsername());
                user.setPasswordResetDate(startDate);
                user.setResetPassword(true);
                userRepository.save(user);
            }
        }
    }
}