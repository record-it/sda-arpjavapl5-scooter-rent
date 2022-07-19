package pl.sda.arpjavapl5.scooterrent.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "scooters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class Scooter extends BaseEntity {

    private String identifier;

    private String model;
}
