package NewAstroConnectApp.NewAstroConnect.Service;

import NewAstroConnectApp.NewAstroConnect.Dto.AstrologerDto;
import NewAstroConnectApp.NewAstroConnect.Entity.Astrologer;
import NewAstroConnectApp.NewAstroConnect.Exception.NotAllowedException;
import NewAstroConnectApp.NewAstroConnect.Repository.AstrologerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AstrologerService {

    private final AstrologerRepository astrologerRepository;
    private final ObjectMapper objectMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AstrologerService(AstrologerRepository astrologerRepository, ObjectMapper objectMapper, PasswordEncoder passwordEncoder) {
        this.astrologerRepository = astrologerRepository;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public AstrologerDto createNewAstrologer(AstrologerDto astrologerDto) {
        if (astrologerRepository.findByEmail(astrologerDto.getEmail()).isPresent()) {
            throw new NotAllowedException("Already Exists ! Please Login");
        } else {
            Astrologer astrologer = new Astrologer();
            astrologer = objectMapper.convertValue(astrologerDto, Astrologer.class);
            astrologer.setPassword(passwordEncoder.encode(astrologer.getPassword()));
            return objectMapper.convertValue(astrologerRepository.save(astrologer), AstrologerDto.class);
        }
    }
}
