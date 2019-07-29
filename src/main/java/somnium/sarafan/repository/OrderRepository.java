package somnium.sarafan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import somnium.sarafan.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
