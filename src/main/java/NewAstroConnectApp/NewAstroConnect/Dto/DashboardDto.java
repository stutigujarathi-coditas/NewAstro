package NewAstroConnectApp.NewAstroConnect.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDto {

    private long totalClientsToday;
    private List<ConsultationDto> upcomingConsultations;
}
