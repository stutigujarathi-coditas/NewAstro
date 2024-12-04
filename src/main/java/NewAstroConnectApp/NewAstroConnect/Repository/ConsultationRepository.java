package NewAstroConnectApp.NewAstroConnect.Repository;


import NewAstroConnectApp.NewAstroConnect.Entity.Consultation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    @Query(value = "SELECT * FROM Consultations  WHERE next_consultation_date BETWEEN ?1 AND ?2",nativeQuery = true)
    List<Consultation> findConsultationsWithinDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(c.amountPaid) FROM Consultation c WHERE MONTH(c.consultationDate) = :month AND YEAR(c.consultationDate) = :year")
    Double findTotalEarningsByMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT c FROM Consultation c JOIN FETCH c.client WHERE c.id = :id")
    Optional<Consultation> findByIdWithClient(@Param("id") Long id);


//    Page<Consultation> findByConsultationDateAfter(LocalDate consultationDate, Pageable pageable);

    @Query("SELECT c FROM Consultation c WHERE c.consultationDate > :today")
    Page<Consultation> findByConsultationDateAfter(@Param("today") LocalDate today, Pageable pageable);

    List<Consultation> findByClientId(Long clientId);


}
