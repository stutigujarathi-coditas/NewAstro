package NewAstroConnectApp.NewAstroConnect.Service;

import NewAstroConnectApp.NewAstroConnect.Dto.ClientDashboardDto;
import NewAstroConnectApp.NewAstroConnect.Dto.ClientDto;
import NewAstroConnectApp.NewAstroConnect.Dto.ConsultationDto;
import NewAstroConnectApp.NewAstroConnect.Entity.Client;
import NewAstroConnectApp.NewAstroConnect.Exception.ClientNotFoundException;
import NewAstroConnectApp.NewAstroConnect.Exception.NotAllowedException;
import NewAstroConnectApp.NewAstroConnect.Repository.ClientRepository;
import NewAstroConnectApp.NewAstroConnect.Specification.ClientSpecification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ClientService(ClientRepository clientRepo, ObjectMapper objectMapper) {
        this.clientRepository = clientRepo;
        this.objectMapper = objectMapper;
    }

    // Create a new client
    public ClientDto createNewClient(ClientDto clientDto, MultipartFile file) throws IOException {
        if (clientRepository.findByNameAndDobAndPlaceOfBirthAndTimeOfBirth(clientDto.getName(), clientDto.getDob(), clientDto.getPlaceOfBirth(), clientDto.getTimeOfBirth()).isPresent()) {
            throw new NotAllowedException("Client Already Exists");
        }
        Client client = objectMapper.convertValue(clientDto, Client.class);
        client.setChart(file.getBytes());
        return objectMapper.convertValue(clientRepository.save(client),ClientDto.class);
    }

    // Get all clients with filters and pagination
    public List<ClientDto> getAllClients(String name, String gender, String age, Pageable pageable) {
        Specification<Client> spec = Specification.where(ClientSpecification.hasName(name))
                .and(ClientSpecification.hasGender(gender))
                .and(ClientSpecification.hasAge(age));

        Page<Client> clients = clientRepository.findAll(spec, pageable);

        return clients.stream()
                .map(client -> {
                    ClientDto clientDto = objectMapper.convertValue(client, ClientDto.class);
//                    clientDto.setChart(client.getChart());
                    return clientDto;
                })
                .collect(Collectors.toList());
    }

    // Get client by ID
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException("Client Doesn't Exist"));
    }

    // Delete client by ID
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    // Update client (patch)
    public ClientDto patchClient(Long id, ClientDto clientDto) {
        Client existingClient = clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException("Client doesn't exist"));
        if (clientDto.getName() != null) existingClient.setName(clientDto.getName());
        if (clientDto.getContact() != null) existingClient.setContact(clientDto.getContact());
        if (clientDto.getAge() != null) existingClient.setAge(clientDto.getAge());
        if (clientDto.getGender() != null) existingClient.setGender(clientDto.getGender());
        if (clientDto.getDob() != null) existingClient.setDob(clientDto.getDob());
        if (clientDto.getTimeOfBirth() != null) existingClient.setTimeOfBirth(clientDto.getTimeOfBirth());
        if (clientDto.getPlaceOfBirth() != null) existingClient.setPlaceOfBirth(clientDto.getPlaceOfBirth());
        clientRepository.save(existingClient);
        return objectMapper.convertValue(existingClient, ClientDto.class);
    }

    // Get client dashboard
    public ClientDashboardDto getClientDashboard(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));
        Double pendingAmount = client.getConsultation().stream()
                .mapToDouble(c -> c.getBalanceAmount())
                .sum();

        List<ConsultationDto> consultationHistory = client.getConsultation().stream()
                .map(c -> objectMapper.convertValue(c, ConsultationDto.class))
                .collect(Collectors.toList());

        ClientDto clientDto = objectMapper.convertValue(client, ClientDto.class);
        return new ClientDashboardDto(clientDto, pendingAmount, consultationHistory);
    }
}
