package NewAstroConnectApp.NewAstroConnect.Service;

import NewAstroConnectApp.NewAstroConnect.Dto.ConsultationDto;
import NewAstroConnectApp.NewAstroConnect.Entity.Client;
import NewAstroConnectApp.NewAstroConnect.Entity.Consultation;
import NewAstroConnectApp.NewAstroConnect.Exception.ClientNotFoundException;
import NewAstroConnectApp.NewAstroConnect.Repository.ClientRepository;
import NewAstroConnectApp.NewAstroConnect.Repository.ConsultationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultationService {

    private ConsultationRepository consultationRepository;
    private ClientRepository clientRepository;
    private ObjectMapper objectMapper;


    @Autowired
    public ConsultationService(ConsultationRepository consultationRepository, ClientRepository clientRepository, ObjectMapper objectMapper) {
        this.consultationRepository = consultationRepository;
        this.clientRepository = clientRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional // This ensures the client is fetched eagerly
    public List<ConsultationDto> getAllConsultation(int pageNo) {
        // Set the page size to 10 (you can adjust this as needed)
        PageRequest pageRequest = PageRequest.of(pageNo - 1, 5);
        Page<Consultation> consultationPage = consultationRepository.findAll(pageRequest);

        // Convert consultations to DTO
        return consultationPage.getContent().stream()
                .map(consultation -> {
                    ConsultationDto consultationDto = objectMapper.convertValue(consultation, ConsultationDto.class);
                    // Ensure the clientId is set correctly
                    consultationDto.setClientId(consultation.getClient() != null ? consultation.getClient().getId() : null);
                    return consultationDto;
                })
                .collect(Collectors.toList());
    }


//    public Consultation createConsultations(ConsultationDto consultationDto) {
//        Optional<Client> optionalClient = clientRepository.findById(consultationDto.getClientId());
//        Consultation consultations = new Consultation();
//        if (optionalClient.isPresent()) {
//            try {
//                Consultation newconsultation = new Consultation();
//                newconsultation.setConsultationDate(consultationDto.getConsultationDate());
//                newconsultation.setNextConsultationDate(consultationDto.getNextConsultationDate());
//                newconsultation.setNotes(consultationDto.getNotes());
//                newconsultation.setPrice(consultationDto.getPrice());
//                newconsultation.setAmountPaid(consultationDto.getAmountPaid());
//                newconsultation.setBalanceAmount(consultationDto.getBalanceAmount());
//                newconsultation.setClient(optionalClient.get());
//                if (optionalClient.get().getConsultation().isEmpty()) {
//                    optionalClient.get().setConsultation(new ArrayList<>());
//                    optionalClient.get().getConsultation().add(newconsultation);
//                } else {
//                    optionalClient.get().getConsultation().add(newconsultation);
//                }
//                clientRepository.save(optionalClient.get());
//                consultations = newconsultation;
//
//                //consultationRepo.save(newconsultation);
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        } else {
//            throw new ClientNotFoundException("Client Not Found");
//        }
//        return consultations;
//    }
//    @Transactional
//    public Consultation createConsultation(ConsultationDto consultationDto) {
//        // Fetch the Client from database using the provided clientId
//        Client client = clientRepository.findById(consultationDto.getClientId())
//                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + consultationDto.getClientId()));
//
//        // Map ConsultationDto to Consultation entity
//        Consultation consultation = new Consultation();
//        consultation.setClient(client);  // Set the client from the fetched Client entity
//        consultation.setConsultationDate(consultationDto.getConsultationDate());
//        consultation.setNextConsultationDate(consultationDto.getNextConsultationDate());
//        consultation.setNotes(consultationDto.getNotes());
//        consultation.setPrice(consultationDto.getPrice());
//        consultation.setAmountPaid(consultationDto.getAmountPaid());
//        consultation.setBalanceAmount(consultationDto.getBalanceAmount());
//
//        // Save the Consultation entity
//        return consultationRepository.save(consultation);
//    }

    public ConsultationDto saveConsultation(ConsultationDto consultationDto) {
        Client client = clientRepository.findById(consultationDto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client Not Found"));

        Consultation consultation = new Consultation();
        consultation.setClient(client);
        consultation.setConsultationDate(consultationDto.getConsultationDate());
        consultation.setNextConsultationDate(consultationDto.getNextConsultationDate());
        consultation.setNotes(consultationDto.getNotes());
        consultation.setPrice(consultationDto.getPrice());
        consultation.setAmountPaid(consultationDto.getAmountPaid());
        consultation.setBalanceAmount(consultationDto.getBalanceAmount());

        consultation = consultationRepository.save(consultation);

        return new ConsultationDto(
                consultation.getClient().getId(),
                consultation.getClient().getName(),
                consultation.getId(),
                consultation.getConsultationDate(),
                consultation.getNextConsultationDate(),
                consultation.getNotes(),
                consultation.getPrice(),
                consultation.getAmountPaid(),
                consultation.getBalanceAmount()
        );
    }





//    //    public void deleteConsultation(Long id) {
////        consultationRepository.deleteById(id);
////    }
//    public boolean deleteConsultation(Long id) {
//        Optional<Consultation> consultation = consultationRepository.findById(id);
//        if (consultation.isPresent()) {
//            consultationRepository.deleteById(id);
//            return true;
//        } else {
//            return false;
//        }
//    }

    // Get a consultation by its ID
    public List<ConsultationDto> getAllConsultationsByClientId(Long clientId) {
        // Fetch consultations for a specific client
        List<Consultation> consultations = consultationRepository.findByClientId(clientId);

        // Map to ConsultationDto and return the list
        return consultations.stream()
                .map(consultation -> objectMapper.convertValue(consultation, ConsultationDto.class))
                .collect(Collectors.toList());
    }

    // Delete a consultation by its ID
    public boolean deleteConsultation(Long id) {
        Optional<Consultation> consultation = consultationRepository.findById(id);
        if (consultation.isPresent()) {
            consultationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ConsultationDto patchConsultation(Long id, ConsultationDto consultationDto) {
        Optional<Consultation> existingConsultation = consultationRepository.findById(id);

        if (existingConsultation.isPresent()) {
            Consultation consultation = existingConsultation.get();

            // Apply the patch logic: update only the fields that are not null in the DTO
            if (consultationDto.getConsultationDate() != null) {
                consultation.setConsultationDate(consultationDto.getConsultationDate());
            }
            if (consultationDto.getNextConsultationDate() != null) {
                consultation.setNextConsultationDate(consultationDto.getNextConsultationDate());
            }
            if (consultationDto.getNotes() != null) {
                consultation.setNotes(consultationDto.getNotes());
            }
            if (consultationDto.getPrice() != null) {
                consultation.setPrice(consultationDto.getPrice());
            }
            if (consultationDto.getAmountPaid() != null) {
                consultation.setAmountPaid(consultationDto.getAmountPaid());
            }
            if (consultationDto.getBalanceAmount() != null) {
                consultation.setBalanceAmount(consultationDto.getBalanceAmount());
            }

            // Save the updated Consultation
            consultationRepository.save(consultation);

            // Convert the updated entity back to DTO and return
            return objectMapper.convertValue(consultation, ConsultationDto.class);
        }

        return null; // Consultation not found
    }



    public List<Consultation> getUpcomingConsultations(LocalDate startDate, LocalDate endDate) {
        System.out.println(startDate+ " "+endDate);
//        List<Consultation> upcomingConsultations =
        return consultationRepository.findConsultationsWithinDateRange(startDate, endDate);

    }


    /*public Consultation updateConsultations(ConsultationDto consultationDto) {

    }*/
}