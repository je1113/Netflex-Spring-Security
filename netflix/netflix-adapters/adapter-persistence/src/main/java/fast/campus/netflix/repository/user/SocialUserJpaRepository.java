package fast.campus.netflix.repository.user;

import fast.campus.netflix.entity.user.SocialUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialUserJpaRepository extends JpaRepository<SocialUserEntity, String>, SocialUserCustomRepository {
}
