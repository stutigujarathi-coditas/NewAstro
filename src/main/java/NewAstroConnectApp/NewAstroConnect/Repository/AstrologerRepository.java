package NewAstroConnectApp.NewAstroConnect.Repository;

import NewAstroConnectApp.NewAstroConnect.Entity.Astrologer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AstrologerRepository extends JpaRepository<Astrologer,Long> {
    Optional<Astrologer> findByEmail(String email);
}
