package pl.sda.arpjavapl5.scooterrent.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User extends BaseEntity{

    @Column(unique = true)
    private String email;

    private String password;

    private Timestamp registerOn;

    private boolean active;
}
