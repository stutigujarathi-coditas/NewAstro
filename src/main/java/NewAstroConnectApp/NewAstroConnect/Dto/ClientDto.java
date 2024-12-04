package NewAstroConnectApp.NewAstroConnect.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private Long id;
    private String name;
    private String contact;
    private String age;
    private String gender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    private String timeOfBirth;
    private String placeOfBirth;
    private byte[] chart;
}

