package NewAstroConnectApp.NewAstroConnect.Controller;

import NewAstroConnectApp.NewAstroConnect.Dto.AuthRequestDto;
import NewAstroConnectApp.NewAstroConnect.Dto.JwtResponseDto;
import NewAstroConnectApp.NewAstroConnect.Dto.ResponseApoDto;
import NewAstroConnectApp.NewAstroConnect.Jwt.JwtService;
import NewAstroConnectApp.NewAstroConnect.Service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;


/**
 * This is the AuthController class, responsible for handling authentication-related requests.
 * It exposes a login endpoint to authenticate users and retrieve JWT tokens.
 */
@RestController
@CrossOrigin // Allows cross-origin requests from any origin
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    // ObjectMapper is used to convert objects to JSON and vice versa
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthService authService;


    /**
     * This POST endpoint handles user login.
     * It receives the login request, authenticates the user, and returns the JWT token along with home screen data.
     *
     * @param authRequestDto Contains the email and password for login
     * @return A ResponseEntity containing the home screen data and JWT tokens upon successful login
     */
//    @PostMapping("/login")
//    public ResponseEntity<ResponseApoDto<HomeScreenDto>> AuthenticateAndGetToken(@RequestBody AuthRequestDto authRequestDto) {
//        return authService.loginAndHomePage(authRequestDto);
//    }

    @PostMapping("/login")
    public ResponseEntity<ResponseApoDto<JwtResponseDto>> AuthenticateAndGetToken(@RequestBody AuthRequestDto authRequestDto) {
        return authService.loginAndReturnJwt(authRequestDto);
    }
//
//    @PostMapping("/refreshToken")
//    public NewAccessTokenResponseDto refreshToken(@RequestBody RefreshTokenRequestDto refreshToken){
//        return objectMapper.convertValue(refreshTokenService.generateNewToken(refreshToken), NewAccessTokenResponseDto.class);
//    }
}

