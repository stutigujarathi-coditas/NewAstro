package NewAstroConnectApp.NewAstroConnect.Service;

import NewAstroConnectApp.NewAstroConnect.Dto.ConsultationDto;
import NewAstroConnectApp.NewAstroConnect.Entity.Consultation;
import NewAstroConnectApp.NewAstroConnect.Repository.ClientRepository;
import NewAstroConnectApp.NewAstroConnect.Repository.ConsultationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final ClientRepository clientRepository;
    private final ConsultationRepository consultationRepository;
    private final ObjectMapper objectMapper;

    public DashboardService(ClientRepository clientRepository, ConsultationRepository consultationRepository) {
        this.clientRepository = clientRepository;
        this.consultationRepository = consultationRepository;
        this.objectMapper = new ObjectMapper();
    }

    // Get today's total clients
    public long getTotalClientsToday() {
        LocalDate today = LocalDate.now();
        return clientRepository.countByCreatedDate(today);  // Assuming Client has a `createdDate` field
    }

    // Get upcoming consultations with pagination
    public List<ConsultationDto> getUpcomingConsultations(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);  // Page size of 5 consultations per page
        LocalDate today = LocalDate.now();
        Page<Consultation> consultationsPage = consultationRepository.findByConsultationDateAfter(today, pageable);
        objectMapper.findAndRegisterModules();
        // Convert consultations to DTO
        return consultationsPage.getContent().stream()
                .map(consultation -> {
                    ConsultationDto consultationDto = objectMapper.convertValue(consultation, ConsultationDto.class);
                    consultationDto.setClientId(consultation.getClient().getId());
                    consultationDto.setClientName(consultation.getClient().getName());
                    return consultationDto;
                })
                .collect(Collectors.toList());
    }
}
