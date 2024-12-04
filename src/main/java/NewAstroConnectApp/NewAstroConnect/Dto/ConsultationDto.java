package NewAstroConnectApp.NewAstroConnect.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationDto {

    private Long clientId;  // Represents the client ID

    private String clientName;

    private Long id;  // Represents the consultation ID

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate consultationDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate nextConsultationDate;
    private String notes;
    private Double price;
    private Double amountPaid;
    private Double balanceAmount;
}
