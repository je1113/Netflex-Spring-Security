package fast.campus.netflix.repository.audit;

import fast.campus.netflix.entity.user.history.UserHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHistoryJpaRepository extends JpaRepository<UserHistoryEntity, String> {

}