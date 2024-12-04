package NewAstroConnectApp.NewAstroConnect.Repository;

import NewAstroConnectApp.NewAstroConnect.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {
   Optional<Client> findByNameAndDobAndPlaceOfBirthAndTimeOfBirth(String name, LocalDate dob, String placeOfBirth, String timeOfBirth);

//    @Query("SELECT SUM(c.amountPaid) FROM Consultation c WHERE MONTH(c.consultationDate) = :month AND YEAR(c.consultationDate) = :year")
//    Double findTotalEarningsByMonth(@Param("month") long month, @Param("year") long year);

   @Query("SELECT COUNT(c) FROM Client c WHERE MONTH(c.createdDate) = :month AND YEAR(c.createdDate) = :year")
   Long findTotalClientsByMonth(@Param("month") int month, @Param("year") int year);

//    //main
//    @Query("SELECT COUNT(c) FROM Client c WHERE MONTH(c.createdDate) = :month AND YEAR(c.createdDate) = :year")
//    Long findTotalClientsByMonth(@Param("month") Month month, @Param("year") int year);

   //    Long countByCreatedDate(LocalDate createdDate);
   @Query("SELECT COUNT(c) FROM Client c WHERE c.createdDate = :today")
   Long countByCreatedDate(@Param("today") LocalDate today);

}
