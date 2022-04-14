package uz.pdp.appwarehouse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Output {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Timestamp date;

    @ManyToOne
    private Warehouse warehouse;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Currency currency;

    private String factureNumber;

    @Column(nullable = false,unique = true)
    private String code;
}
