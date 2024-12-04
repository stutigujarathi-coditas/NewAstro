package NewAstroConnectApp.NewAstroConnect.Controller;

import NewAstroConnectApp.NewAstroConnect.Dto.ProfileDto;
import NewAstroConnectApp.NewAstroConnect.Dto.ResponseApoDto;
import NewAstroConnectApp.NewAstroConnect.Service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * GET /api/profile/{astrologerId} - Fetch the astrologer's profile.
     *
     * @param astrologerId The ID of the astrologer.
     * @return The astrologer's profile information, including total earnings and total clients.
     */
    @GetMapping("/{astrologerId}")
    public ResponseEntity<ResponseApoDto<ProfileDto>> getProfile(@PathVariable Long astrologerId) {
        ProfileDto profileDto = profileService.getAstrologerProfile(astrologerId);
        ResponseApoDto<ProfileDto> response = new ResponseApoDto<>(profileDto, HttpStatus.OK.value(), "Profile fetched successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}