package NewAstroConnectApp.NewAstroConnect.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDashboardDto {
    private ClientDto client; // Client details
    private Double pendingAmount; // Pending amount
    private List<ConsultationDto> consultationHistory; // Consultation history
}
