package NewAstroConnectApp.NewAstroConnect.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    private Long id;           // Astrologer ID
    private String username;   // Astrologer Username
    private String email;      // Astrologer Email
    private Double totalEarnings;  // Total Earnings
    private Long totalClients;     // Total Clients
}

