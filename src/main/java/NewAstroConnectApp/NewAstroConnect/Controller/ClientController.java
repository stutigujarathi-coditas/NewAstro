package NewAstroConnectApp.NewAstroConnect.Controller;

import NewAstroConnectApp.NewAstroConnect.Dto.ClientDashboardDto;
import NewAstroConnectApp.NewAstroConnect.Dto.ClientDto;
import NewAstroConnectApp.NewAstroConnect.Dto.ResponseApoDto;
import NewAstroConnectApp.NewAstroConnect.Entity.Client;
import NewAstroConnectApp.NewAstroConnect.Service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * This is the ClientController class, responsible for handling all client-related HTTP requests.
 * It includes functionality for creating, retrieving, listing, and deleting clients.
 */

@RestController
@CrossOrigin
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ClientController(ClientService clientService, ObjectMapper objectMapper) {
        this.clientService = clientService;
        this.objectMapper = objectMapper;
    }

    // Create a new client
    @PostMapping("/create")
    public ResponseEntity<ResponseApoDto<ClientDto>> newClient(@RequestParam("data") String clientData,@RequestParam("image") MultipartFile file) throws IOException {
        ClientDto clientDto = objectMapper.readValue(clientData, ClientDto.class);
//        byte[] bytes = file.getBytes();
//        clientDto.setChart(bytes);
        ClientDto newClient = clientService.createNewClient(clientDto,file);
//        ClientDto createdClient = objectMapper.convertValue(newClient, ClientDto.class);
        ResponseApoDto<ClientDto> response = new ResponseApoDto<>(newClient, HttpStatus.CREATED.value(), "Client has been successfully registered");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    // Get a client by ID
    @GetMapping("/clientById/{id}")
    public ResponseEntity<ResponseApoDto<ClientDto>> clientById(@PathVariable Long id) {
        ClientDto theClient = objectMapper.convertValue(clientService.getClientById(id), ClientDto.class);
        ResponseApoDto<ClientDto> response = new ResponseApoDto<>(theClient, HttpStatus.OK.value(), "Client data retrieved successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get all clients (with pagination and optional filters)
    @GetMapping("/All")
    public ResponseEntity<ResponseApoDto<List<ClientDto>>> allClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String age) {

        Pageable pageable = PageRequest.of(page, size);
        List<ClientDto> clients = clientService.getAllClients(name, gender, age, pageable);

        ResponseApoDto<List<ClientDto>> response = new ResponseApoDto<>(clients, HttpStatus.OK.value(), "List of all registered clients");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Update (patch) a client by ID
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseApoDto<ClientDto>> patchClient(@PathVariable Long id, @RequestBody ClientDto clientDto) {
        ClientDto updatedClient = clientService.patchClient(id, clientDto);
        if (updatedClient != null) {
            ResponseApoDto<ClientDto> response = new ResponseApoDto<>(updatedClient, HttpStatus.OK.value(), "Client updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseApoDto<ClientDto> response = new ResponseApoDto<>(null, HttpStatus.NOT_FOUND.value(), "Client not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Delete a client by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseApoDto<String>> removeClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        ResponseApoDto<String> response = new ResponseApoDto<>("Client successfully removed", HttpStatus.NO_CONTENT.value(), "Client has been deleted");
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    // Get the client dashboard
    @GetMapping("consultation/{id}")
    public ResponseEntity<ClientDashboardDto> getClientDashboard(@PathVariable Long id) {
        ClientDashboardDto dashboard = clientService.getClientDashboard(id);
        return ResponseEntity.ok(dashboard);
    }
}
