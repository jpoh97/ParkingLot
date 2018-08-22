package co.com.ceiba.parkinglotbackend.entities;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Optional;

@Data
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    private Vehicle vehicle;
    private LocalDate entryDate;
    private Optional<LocalDate> departureDate;
    private Optional<Long> price;
}
