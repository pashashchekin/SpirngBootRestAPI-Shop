package somnium.sarafan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import somnium.sarafan.domain.Coupon;

public interface CouponsRepository extends JpaRepository<Coupon, Long> {
    Coupon findById(long id);
}
