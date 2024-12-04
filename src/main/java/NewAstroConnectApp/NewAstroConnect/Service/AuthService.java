package NewAstroConnectApp.NewAstroConnect.Service;


import NewAstroConnectApp.NewAstroConnect.Dto.*;
import NewAstroConnectApp.NewAstroConnect.Exception.NotAllowedException;
import NewAstroConnectApp.NewAstroConnect.Jwt.JwtService;
import NewAstroConnectApp.NewAstroConnect.Repository.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;


//    public ResponseEntity<ResponseApoDto<HomeScreenDto>> loginAndHomePage(AuthRequestDto authRequestDTO){
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword()));
//        if (authentication.isAuthenticated()) {
//            HomeScreenDto homeScreenDto = new HomeScreenDto();
//
//            User astrologer = (User) authentication.getPrincipal();
//            String accessToken = jwtService.generateAccessToken(astrologer.getUsername());
//            String refreshToken = jwtService.generateRefreshToken(astrologer.getUsername());
//            JwtResponseDto jwtResponseDto = JwtResponseDto.builder()
//                    .accessToken(accessToken)
//                    .refreshToken(refreshToken)
//                    .build();
//            homeScreenDto.setJwtResponseDto(jwtResponseDto);
//            homeScreenDto.setClientDtoList(clientService.allClients());
//
//            Double monthlyEarning = clientRepository.findTotalEarningsByMonth(LocalDate.now().getMonth().getValue(),LocalDate.now().getYear());
//            homeScreenDto.setMonthlyEarning(monthlyEarning);
////
////            Long totalClients = clientRepository.findTotalClientsByMonth(LocalDate.now().getMonth(), LocalDate.now().getYear());
////            homeScreenDto.setMonthlyClients(totalClients);
//
//            ResponseApoDto<HomeScreenDto> response = new ResponseApoDto<>(homeScreenDto, HttpStatus.CREATED.value(), "Logged In Successfully");
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } else {
//            throw new NotAllowedException("Invalid Login Details!");
//        }
//    }
//
//    public NewAccessTokenResponseDto refreshToken(RefreshTokenRequestDto refreshToken){
//        return objectMapper.convertValue(refreshTokenService.generateNewToken(refreshToken), NewAccessTokenResponseDto.class);
//    }
//}


    public ResponseEntity<ResponseApoDto<JwtResponseDto>> loginAndReturnJwt(AuthRequestDto authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword())
        );

        if (authentication.isAuthenticated()) {
            // Generate JWT tokens
            User astrologer = (User) authentication.getPrincipal();
            String accessToken = jwtService.generateAccessToken(astrologer.getUsername());
            String refreshToken = jwtService.generateRefreshToken(astrologer.getUsername());

            // Create JwtResponseDto
            JwtResponseDto jwtResponseDto = JwtResponseDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

            // Wrap in ResponseApoDto
            ResponseApoDto<JwtResponseDto> response = new ResponseApoDto<>(
                    jwtResponseDto, HttpStatus.OK.value(), "Logged In Successfully"
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new NotAllowedException("Invalid Login Details!");
        }
    }
}