package NewAstroConnectApp.NewAstroConnect.Controller;

import NewAstroConnectApp.NewAstroConnect.Dto.ConsultationDto;
import NewAstroConnectApp.NewAstroConnect.Dto.ResponseApoDto;
import NewAstroConnectApp.NewAstroConnect.Entity.Consultation;
import NewAstroConnectApp.NewAstroConnect.Service.ConsultationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

/**
 * This is the ConsultationController class, responsible for handling all consultation-related HTTP requests.
 * It includes functionality for creating, retrieving, deleting consultations, and fetching upcoming consultations.
 */
@RestController
@RequestMapping("api/consultation")
public class ConsultationController {

    //    private final ConsultationService consultationService;
//    private final ConsultationRepository consultationRepository;
//    private final ObjectMapper objectMapper;
//
//    @Autowired
//    public ConsultationController(ConsultationService consultationService, ConsultationRepository consultationRepository, ObjectMapper objectMapper) {
//        this.consultationService = consultationService;
//        this.consultationRepository = consultationRepository;
//        this.objectMapper = objectMapper;
//    }
    @Autowired
    private ConsultationService consultationService;

    @PostMapping("/create")
    public ResponseEntity<ResponseApoDto<Object>> createConsultation(@RequestBody ConsultationDto consultationDto) {
        try {
            // Save the consultation and map the result to ConsultationDto
            ConsultationDto savedConsultationDto = consultationService.saveConsultation(consultationDto);

            // Create the response with the consultation data
            ResponseApoDto<Object> response = new ResponseApoDto<>(
                    savedConsultationDto,
                    HttpStatus.CREATED.value(),
                    "Consultation Data Saved"
            );

            // Return the response with HTTP status 201 (Created)
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle any exceptions that occur during the process
            ResponseApoDto<Object> errorResponse = new ResponseApoDto<>(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error Saving Consultation"
            );

            // Return the error response with HTTP status 500 (Internal Server Error)
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseApoDto<List<ConsultationDto>>> getAllConsultationsByClientId(@PathVariable Long id) {
        // Fetch all consultations for the given client ID
        List<ConsultationDto> consultations = consultationService.getAllConsultationsByClientId(id);

        if (consultations.isEmpty()) {
            // No consultations found for this client
            ResponseApoDto<List<ConsultationDto>> response = new ResponseApoDto<>(null, HttpStatus.NOT_FOUND.value(), "No consultations found for this client.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            // Consultations found, return them
            ResponseApoDto<List<ConsultationDto>> response = new ResponseApoDto<>(consultations, HttpStatus.OK.value(), "Consultations found.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    // Delete a consultation by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApoDto<Void>> deleteConsultation(@PathVariable Long id) {
        boolean deleted = consultationService.deleteConsultation(id);
        if (deleted) {
            ResponseApoDto<Void> response = new ResponseApoDto<>(null, HttpStatus.NO_CONTENT.value(), "Consultation deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            ResponseApoDto<Void> response = new ResponseApoDto<>(null, HttpStatus.NOT_FOUND.value(), "Consultation not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Update (patch) a consultation
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseApoDto<ConsultationDto>> patchConsultation(@PathVariable Long id, @RequestBody ConsultationDto consultationDto) {
        ConsultationDto updatedConsultation = consultationService.patchConsultation(id, consultationDto); // Directly using the ConsultationDto return type
        if (updatedConsultation != null) {
            ResponseApoDto<ConsultationDto> response = new ResponseApoDto<>(updatedConsultation, HttpStatus.OK.value(), "Consultation updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseApoDto<ConsultationDto> response = new ResponseApoDto<>(null, HttpStatus.NOT_FOUND.value(), "Consultation not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Get all consultations with pagination
    @GetMapping("/All")
    public ResponseEntity<ResponseApoDto<List<ConsultationDto>>> getAllConsultations(
            @RequestParam(defaultValue = "1") int pageNo) {

        // Fetch paginated consultations
        List<ConsultationDto> consultations = consultationService.getAllConsultation(pageNo);

        // Prepare the response DTO
        ResponseApoDto<List<ConsultationDto>> response = new ResponseApoDto<>(
                consultations, HttpStatus.OK.value(), "Consultations fetched successfully."
        );

        // Return the response
        return new ResponseEntity<>(response, HttpStatus.OK);
    }







    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseApoDto<String>> handleException(Exception ex) {
        ResponseApoDto<String> response = new ResponseApoDto<>(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

//    @DeleteMapping("/deleteConsultation/{id}")
//    public ResponseEntity<ResponseApoDto<String>> deleteConsultation(@PathVariable  Long id) {
//        consultationService.deleteConsultation(id);
//        ResponseApoDto<String> response = new ResponseApoDto<>("User Not Found",HttpStatus.NOT_FOUND.value(),"Consultation Deleted Successfully");
//        return new ResponseEntity<>(response,HttpStatus.OK);
//    }
//
//        @DeleteMapping("/deleteConsultation/{id}")
//        public ResponseEntity<ResponseApoDto<String>> deleteConsultation (@PathVariable id){
//            try {
//                // Call the service to delete the consultation
//                consultationService.deleteConsultation(id);
//
//                // Return success response
//                ResponseApoDto<String> response = new ResponseApoDto<>(
//                        "Consultation Deleted Successfully",
//                        HttpStatus.OK.value(),
//                        "Consultation was deleted successfully."
//                );
//                return new ResponseEntity<>(response, HttpStatus.OK);
//            } catch (Exception e) {
//                // Handle error and return failure response
//                ResponseApoDto<String> response = new ResponseApoDto<>(
//                        "Error Deleting Consultation",
//                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                        "An error occurred while trying to delete the consultation."
//                );
//                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//
//        @GetMapping("/allConsultations/pageNo")
//        public ResponseEntity<ResponseApoDto<List<ConsultationDto>>> getAllConsults(@RequestParam("pageNo") int pageNo) {
//
//            List<ConsultationDto> allConsultation = consultationService.getAllConsultation(pageNo);
//            ResponseApoDto<List<ConsultationDto>> response = new ResponseApoDto<>(allConsultation, HttpStatus.OK.value(), "All Consultations");
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }
//
//        @GetMapping("/upcoming")
//        public ResponseEntity<List<Consultation>> getUpcomingConsultation(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
//            return new ResponseEntity<>(consultationService.getUpcomingConsultations(Date.valueOf(startDate).toLocalDate(), Date.valueOf(endDate).toLocalDate()), HttpStatus.OK);
//
//        }


    /*@PutMapping("/updateConsultation/{id}")
    public ResponseEntity<ApiResponseDto<ConsultationDto>> updateConsultation(@RequestBody ConsultationDto consultationDto){
        Consultation newConsult = consultationService.updateConsultations(consultationDto);
        ApiResponseDto<ConsultationDto> response = new ApiResponseDto<>((objectMapper.convertValue(newConsult,ConsultationDto.class)),HttpStatus.CREATED.value(),"Consultation Data Saved");
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }*/
    }
}