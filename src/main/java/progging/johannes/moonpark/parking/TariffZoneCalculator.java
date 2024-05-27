package progging.johannes.moonpark.parking;

import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

@Slf4j
public class TariffZoneCalculator {
    long price;


    long calculatePriceBasedOnTariffZone(LocalDateTime startedParking, LocalDateTime endedParking, TariffZone tariffZone) {
        switch (tariffZone) {
            case M1 -> price = calculatePriceM1(startedParking, endedParking);
            case M2 -> price = calculatePriceM2(startedParking, endedParking);
            case M3 -> price = calculatePriceM3(startedParking, endedParking);
            default -> throw new IllegalStateException("Unexpected TariffZone: " + tariffZone);
        }
        return price;
    }

    long calculatePriceM1(LocalDateTime startedParking, LocalDateTime endedParking) {
        long hoursToPayFor = getHoursToPayFor(startedParking, endedParking);
        price = hoursToPayFor * 60;

        return price;
    }

    long calculatePriceM2(LocalDateTime startedParking, LocalDateTime endedParking) {
        long hoursToPayFor = getHoursToPayFor(startedParking, endedParking);
        final long HOURLY_PRICE = 100;

        for(int i = 0; i < hoursToPayFor; i++) {
            DayOfWeek dayOfWeek = DayOfWeek.of(startedParking.plusHours(i).get(ChronoField.DAY_OF_WEEK));
            if(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                price += HOURLY_PRICE*2;
            } else {
                price += HOURLY_PRICE;
            }
        }
        return price;
    }

    long calculatePriceM3(LocalDateTime startedParking, LocalDateTime endedParking) {
        long minutesToPayFor = getMinutesToPayFor(startedParking, endedParking);

        if(isBetweenMondayAndSaturday(startedParking) && (startedParking.getHour() >= 8 && startedParking.getHour() <= 15)) {
                minutesToPayFor = Math.max(0, minutesToPayFor - 60);
        }

        while(minutesToPayFor != 0) {
            minutesToPayFor--;
            if(isBetweenMondayAndSaturday(startedParking)) {
                if(startedParking.getHour() >= 8 && startedParking.getHour() <= 15) {
                    price += 2;
                } else {
                    price += 3;
                }
            }
        }
        return price;
    }

    // ---------- HELPER METHODS ---------
    private static long getMinutesToPayFor(LocalDateTime startedParking, LocalDateTime endedParking) {
        long minuteDifference = ChronoUnit.MINUTES.between(startedParking, endedParking);
        log.info("Minute difference: {}", minuteDifference);

        return minuteDifference;
    }

    private static boolean isBetweenMondayAndSaturday(LocalDateTime date) {
        DayOfWeek dayOfWeek = DayOfWeek.of(date.get(ChronoField.DAY_OF_WEEK));
        return dayOfWeek != DayOfWeek.SUNDAY;
    }

    private static long getHoursToPayFor(LocalDateTime startedParking, LocalDateTime endedParking) {
        long minuteDifference = ChronoUnit.MINUTES.between(startedParking, endedParking);
        long hourDifference = ChronoUnit.HOURS.between(startedParking, endedParking);
        logging(minuteDifference, hourDifference);

        long hoursToPayFor = hourDifference;
        if(minuteDifference != 0 && minuteDifference != 60) {
            hoursToPayFor++;
        }
        return hoursToPayFor;
    }

    private static void logging(long minuteDifference, long hourDifference) {
        log.info("Minute difference: {}", minuteDifference);
        log.info("Hour difference: {}", hourDifference);

    }

    public static boolean isWeekend(final LocalDateTime date) {
        DayOfWeek dayOfWeek = DayOfWeek.of(date.get(ChronoField.DAY_OF_WEEK));
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
