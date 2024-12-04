package NewAstroConnectApp.NewAstroConnect.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull(message = "Name is required and cannot be null")
    private String name;

    @NotNull(message = "Contact is required and cannot be null")
    private String contact;

    @NotNull(message = "Age is required")
    private String age;

    @NotNull(message = "Gender is required and cannot be null")
    private String gender;

    @NotNull(message = "Date of birth is required and cannot be null")
    private LocalDate dob;

    @NotNull(message = "Time of birth is required and cannot be null")
    private String timeOfBirth;

    @NotNull(message = "Place of birth is required and cannot be null")
    private String placeOfBirth;

    @Lob
    private byte[] chart;

    @Column(name = "created_date", updatable = false)
    @CreationTimestamp
    private LocalDate createdDate;

    @JsonManagedReference
    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Consultation> consultation;
}





















