package tacos.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.CreditCardNumber;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "taco_order")
@NoArgsConstructor
public class TacoOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date = new Date();

    @NotBlank(message = "Il nome del destinatario è obbligatorio")
    @Column(name = "deliveryName")
    private String deliveryName;

    @NotBlank(message = "La via è obbligatoria")
    @Column(name = "deliveryStreet")
    private String deliveryStreet;

    @NotBlank(message = "La città è obbligatoria")
    @Column(name = "deliveryCity")
    private String deliveryCity;

    @NotBlank(message = "Lo Stato è obbligatorio")
    @Column(name = "deliveryState")
    private String deliveryState;

    @NotBlank(message = "Il codice postale è obbligatorio")
    @Column(name = "deliveryZip")
    private String deliveryZip;

    @CreditCardNumber(message = "Numero di carta di credito non valido")
    @Column(name = "ccNumber")
    private String ccNumber;

    @Pattern(
        regexp = "^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$",
        message = "Deve essere nel formato MM/AA"
    )
    @Column(name = "ccExpiration")
    private String ccExpiration;

    @Digits(
        integer = 3,
        fraction = 0,
        message = "CVV non valido"
    )
    @Column(name = "ccVV")
    private String ccCVV;

    @ManyToMany
    @JoinTable(
        name = "taco_order_taco",
        joinColumns = @JoinColumn(name = "taco_order_id"),
        inverseJoinColumns = @JoinColumn(name = "taco_id")
    )
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }
}