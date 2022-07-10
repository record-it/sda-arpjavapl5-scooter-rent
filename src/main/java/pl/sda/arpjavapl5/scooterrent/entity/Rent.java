package pl.sda.arpjavapl5.scooterrent.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "rents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Rent extends BaseEntity {

    private LocalDateTime startOn;

    private LocalDateTime endOn;

    private BigDecimal pricePerMinute;

    private BigDecimal totalCost;


    @ManyToOne
    private User user;

    @ManyToOne
    private Scooter scooter;
}
