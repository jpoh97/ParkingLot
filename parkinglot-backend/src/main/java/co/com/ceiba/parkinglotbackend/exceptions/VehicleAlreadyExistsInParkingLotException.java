package co.com.ceiba.parkinglotbackend.exceptions;

public class VehicleAlreadyExistsInParkingLotException extends BaseException {

    public VehicleAlreadyExistsInParkingLotException() {
        super("Vehicle already exists in parking lot. Check license plate");
    }
}
