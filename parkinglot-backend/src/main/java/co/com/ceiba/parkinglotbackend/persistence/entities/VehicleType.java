package co.com.ceiba.parkinglotbackend.persistence.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class VehicleType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    @NotNull
    private String name;
    @NotNull
    private Integer totalPlaces;
    @NotNull
    private LocalDateTime creationDate;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @NotNull
    private List<ParkingRates> parkingRates;

    public VehicleType() {
    }

    /**
     * Constructor for unit test
     */
    public VehicleType(@NotNull String name, @NotNull Integer totalPlaces) {
        this.name = name;
        this.totalPlaces = totalPlaces;
        this.creationDate = LocalDateTime.now();
        this.parkingRates = new ArrayList<>();
    }

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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public List<ParkingRates> getParkingRates() {
        return parkingRates;
    }

    public void setParkingRates(List<ParkingRates> parkingRates) {
        this.parkingRates = parkingRates;
    }
}
