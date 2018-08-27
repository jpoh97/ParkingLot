package co.com.ceiba.parkinglotbackend.core.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
public class VehicleType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private Integer totalPlaces;
    @NotNull
    private LocalDate creationDate;
    @OneToMany(fetch = FetchType.EAGER, cascade =  CascadeType.ALL)
    @NotNull
    private List<ParkingRates> parkingRates;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalPlaces() {
        return totalPlaces;
    }

    public void setTotalPlaces(Integer totalPlaces) {
        this.totalPlaces = totalPlaces;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public List<ParkingRates> getParkingRates() {
        return parkingRates;
    }

    public void setParkingRates(List<ParkingRates> parkingRates) {
        this.parkingRates = parkingRates;
    }
}
