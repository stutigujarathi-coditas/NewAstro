package NewAstroConnectApp.NewAstroConnect.Authentication;

import NewAstroConnectApp.NewAstroConnect.Entity.Astrologer;
import NewAstroConnectApp.NewAstroConnect.Exception.AstrologerNotFoundException;
import NewAstroConnectApp.NewAstroConnect.Repository.AstrologerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AstrologerRepository astrologerRepository;

    @Autowired
    public CustomUserDetailsService(AstrologerRepository astrologerRepository) {
        this.astrologerRepository = astrologerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws AstrologerNotFoundException {
        Astrologer astrologer = astrologerRepository.findByEmail(email)
                .orElseThrow(() -> new AstrologerNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                astrologer.getEmail(),
                astrologer.getPassword(),
                Collections.emptyList()
        );
    }
}

