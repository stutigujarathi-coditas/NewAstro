package NewAstroConnectApp.NewAstroConnect.Service;

import NewAstroConnectApp.NewAstroConnect.Dto.RefreshTokenRequestDto;
import NewAstroConnectApp.NewAstroConnect.Entity.Astrologer;
import NewAstroConnectApp.NewAstroConnect.Jwt.JwtService;
import NewAstroConnectApp.NewAstroConnect.Repository.AstrologerRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class RefreshTokenService {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AstrologerRepository astrologerRepository;

    public String generateNewToken(RefreshTokenRequestDto token) {
        Claims claims = jwtService.extractAllClaims(token.getRefreshToken());
        String email = claims.getSubject();
        Optional<Astrologer> astrologerRepoByEmailId = astrologerRepository.findByEmail(email);
        if (astrologerRepoByEmailId.isPresent()) {

            String tokenName = claims.get("tokenName").toString();
            if (tokenName.equalsIgnoreCase("accessToken")) {
                return "Incorrect Token Passed";
            }

            Date date = claims.getExpiration();
            if (date.before(new Date())) {
                return "Session Expired..Please ReLogin";
            }
            return jwtService.generateAccessToken((String) claims.get("username"));
        }
        return "Session Expired..Please ReLogin";
    }
}
