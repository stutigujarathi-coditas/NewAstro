package NewAstroConnectApp.NewAstroConnect.Service;

import NewAstroConnectApp.NewAstroConnect.Dto.ProfileDto;
import NewAstroConnectApp.NewAstroConnect.Entity.Astrologer;
import NewAstroConnectApp.NewAstroConnect.Repository.AstrologerRepository;
import NewAstroConnectApp.NewAstroConnect.Repository.ClientRepository;
import NewAstroConnectApp.NewAstroConnect.Repository.ConsultationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;

//@Service
//public class ProfileService {
//
//    private final AstrologerRepository astrologerRepository;
//    private final ConsultationRepository consultationRepository;
//    private final ClientRepository clientRepository;
//
//    public ProfileService(AstrologerRepository astrologerRepository, ConsultationRepository consultationRepository, ClientRepository clientRepository) {
//        this.astrologerRepository = astrologerRepository;
//        this.consultationRepository = consultationRepository;
//        this.clientRepository = clientRepository;
//    }
//
//    public ProfileDto getAstrologerProfile(Long astrologerId) {
//        // Fetch astrologer details
//        Astrologer astrologer = astrologerRepository.findById(astrologerId)
//                .orElseThrow(() -> new RuntimeException("Astrologer not found"));
//
//        // Get the current month and year
//        int month = LocalDate.now().getMonthValue();
//        int year = LocalDate.now().getYear();
//
//        // Fetch total earnings and total clients for the current month
//        Double totalEarnings = consultationRepository.findTotalEarningsByMonth(month, year);
//        Long totalClients = clientRepository.findTotalClientsByMonth(Month.of(month), year);
//
//        // Build and return the profile DTO
//        return new ProfileDto(
//                astrologer.getId(),
//                astrologer.getUsername(),
//                astrologer.getEmail(),
//                totalEarnings != null ? totalEarnings : 0.0,
//                totalClients != null ? totalClients : 0L
//        );
//    }
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final AstrologerRepository astrologerRepository;
    private final ConsultationRepository consultationRepository;
    private final ClientRepository clientRepository;

    public ProfileDto getAstrologerProfile(Long astrologerId) {
        Astrologer astrologer = astrologerRepository.findById(astrologerId)
                .orElseThrow(() -> new RuntimeException("Astrologer Not Found"));

        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();

        Double totalEarnings = consultationRepository.findTotalEarningsByMonth(month, year);
        Long totalClients = clientRepository.findTotalClientsByMonth(month, year);

        totalEarnings = (totalEarnings != null) ? totalEarnings : 0.0;
        totalClients = (totalClients != null) ? totalClients : 0L;

        ProfileDto newProfile = new ProfileDto(
                astrologer.getId(),
                astrologer.getUsername(),
                astrologer.getEmail(),
                totalEarnings,
                totalClients
        );
        return newProfile;
    }
}

