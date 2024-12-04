package NewAstroConnectApp.NewAstroConnect.Controller;

import NewAstroConnectApp.NewAstroConnect.Dto.AstrologerDto;
import NewAstroConnectApp.NewAstroConnect.Dto.AstrologerResponseDto;
import NewAstroConnectApp.NewAstroConnect.Dto.ResponseApoDto;
import NewAstroConnectApp.NewAstroConnect.Service.AstrologerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This is the AstrologerController class, responsible for handling HTTP requests related to Astrologers.
 * It exposes a POST endpoint for registering a new Astrologer.
 */
@RestController
@RequestMapping("/api/astrologer")
public class AstrologerController {

    private final AstrologerService astrologerService;

    // Constructor to initialize the AstrologerService
    public AstrologerController(AstrologerService astrologerService) {
        this.astrologerService = astrologerService;
    }

    /**
     * This POST endpoint allows the registration of a new astrologer.
     * It accepts a request body containing astrologer data, processes it, and returns a response
     * containing the created astrologer and a success message.
     *
     * @param astrologerDto Contains the astrologer data to be registered
     * @return A ResponseEntity containing a ResponseApoDto with the created Astrologer data and a success message
     */
//    @PostMapping("/register")
//    public ResponseEntity<ResponseApoDto<AstrologerDto>> registerAstrologer(@RequestBody AstrologerDto astrologerDto) {
//
//        AstrologerDto createdAstrologer = astrologerService.createNewAstrologer(astrologerDto);
//        ResponseApoDto<AstrologerDto> response = new ResponseApoDto<>(createdAstrologer, HttpStatus.CREATED.value(), "Astrologer registered successfully");
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }

    @PostMapping("/register")
    public ResponseEntity<ResponseApoDto<AstrologerResponseDto>> registerAstrologer(@RequestBody AstrologerDto astrologerDto) {

        // Register the new astrologer and get the created AstrologerDto
        AstrologerDto createdAstrologer = astrologerService.createNewAstrologer(astrologerDto);

        // Map AstrologerDto to AstrologerResponseDto
        AstrologerResponseDto responseDto = new AstrologerResponseDto(
                createdAstrologer.getId(),
                createdAstrologer.getUsername(),
                createdAstrologer.getEmail()
        );

        // Return the response with AstrologerResponseDto
        ResponseApoDto<AstrologerResponseDto> response = new ResponseApoDto<>(
                responseDto,
                HttpStatus.CREATED.value(),
                "Astrologer registered successfully"
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
