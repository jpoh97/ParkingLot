package co.com.ceiba.parkinglotbackend.services.implementations;

import co.com.ceiba.parkinglotbackend.services.interfaces.ParkingCalendarService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;

@Service
public class ParkingCalendarServiceImplementation implements ParkingCalendarService {

    public boolean isSundayOrMonday() {
        LocalDate localDate = LocalDate.now();
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();

        return dayOfWeek == DayOfWeek.SUNDAY || dayOfWeek == DayOfWeek.MONDAY;
    }
}
