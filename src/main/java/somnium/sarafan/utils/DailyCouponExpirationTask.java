package somnium.sarafan.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import somnium.sarafan.domain.Coupon;
import somnium.sarafan.repository.CouponsRepository;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class DailyCouponExpirationTask extends Thread {

    @Autowired
    CouponsRepository couponsRepository;

    private Date startDate;
    private boolean running;

    public DailyCouponExpirationTask() {
        this.running = true;
    }

    @Override
    public void run() {
        if (running) {
            System.out.println("Daily expired coupon remover initiated");
            ExpirationTask();
        }
    }

    private void ExpirationTask() {
        startDate = new Date();
        Collection<Coupon> allCoupons = couponsRepository.findAll();
        int i = 0;
        for (Coupon coupon : allCoupons) {
            if (coupon.getEndDate().before(startDate)) {
                couponsRepository.delete(coupon);
                i++;
            }
        }
        System.out.println(i + " Coupons removed");
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void stopTask() {
        running = false;
    }

}
