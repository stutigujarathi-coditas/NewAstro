package NewAstroConnectApp.NewAstroConnect.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")

    @JsonBackReference
    private Client client;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate consultationDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate nextConsultationDate;
    private String notes;
    private Double price;
    private Double amountPaid;
    private Double balanceAmount;
}
