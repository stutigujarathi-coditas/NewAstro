package NewAstroConnectApp.NewAstroConnect.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "astrologer")
public class Astrologer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Username is required and cannot be null")
    private String username;

    @NotNull(message = "Email is required and cannot be null")
    private String email;

    @NotNull(message = "Password is required and cannot be null")
    private String password;
}
