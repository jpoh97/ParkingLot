package co.com.ceiba.parkinglotbackend.core.entities;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Optional;

@Data
@RequiredArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @NonNull private Vehicle vehicle;
    @NonNull private LocalDate entryDate;
    private Optional<LocalDate> departureDate;
    private Optional<Long> price;
}
