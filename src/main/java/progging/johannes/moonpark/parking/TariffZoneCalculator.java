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
            if(isWeekend(dayOfWeek)) {
                price += HOURLY_PRICE*2;
            } else {
                price += HOURLY_PRICE;
            }
        }
        return price;
    }

    long calculatePriceM3(LocalDateTime startedParking, LocalDateTime endedParking) {
        long minutesToPayFor = getMinutesToPayFor(startedParking, endedParking);
        final long MINUTE_PRICE_FROM_08_TO_16 = 2;
        final long MINUTE_PRICE_FROM_16_TO_08 = 3;
        final long HOURLY_PRICE_FROM_08_TO_16 = MINUTE_PRICE_FROM_08_TO_16*60;
        final long HOURLY_PRICE_FROM_16_TO_08 = MINUTE_PRICE_FROM_16_TO_08*60;

        DayOfWeek dayOfWeekStartedParking = DayOfWeek.of(startedParking.get(ChronoField.DAY_OF_WEEK));
        if(isBetweenMondayAndSaturday(dayOfWeekStartedParking)) {
            if(startedParking.getHour() >= 8 && startedParking.getHour() < 16) {
                if (minutesToPayFor <= 60) {
                    price = 0;
                    return price;
                } else {
                    minutesToPayFor -= 60;
                }
            }
        }

        long hoursToPayFor = minutesToPayFor / 60;
        long remainingMinutes = minutesToPayFor % 60;

        if(hoursToPayFor != 0) {
            addHourPricing(startedParking, hoursToPayFor, HOURLY_PRICE_FROM_08_TO_16, HOURLY_PRICE_FROM_16_TO_08);
        }

        if(remainingMinutes != 0) {
            addMinutePricing(startedParking, endedParking, remainingMinutes, MINUTE_PRICE_FROM_08_TO_16, MINUTE_PRICE_FROM_16_TO_08);
        }
        return price;
    }

    private void addMinutePricing(LocalDateTime startedParking, LocalDateTime endedParking, long remainingMinutes, long MINUTE_PRICE_FROM_08_TO_16, long MINUTE_PRICE_FROM_16_TO_08) {
        DayOfWeek dayOfWeekEndedParking = DayOfWeek.of(endedParking.get(ChronoField.DAY_OF_WEEK));
        if(isBetweenMondayAndSaturday(dayOfWeekEndedParking)) {
            if(startedParking.getHour() >= 8 && startedParking.getHour() <= 15) {
                price += remainingMinutes * MINUTE_PRICE_FROM_08_TO_16;
            } else {
                price += remainingMinutes * MINUTE_PRICE_FROM_16_TO_08;
            }
        }
    }

    private void addHourPricing(LocalDateTime startedParking, long hoursToPayFor, long HOURLY_PRICE_FROM_08_TO_16, long HOURLY_PRICE_FROM_16_TO_08) {
        for(int i = 0; i < hoursToPayFor; i++) {
            DayOfWeek dayOfWeek = DayOfWeek.of(startedParking.plusHours(i).get(ChronoField.DAY_OF_WEEK));
            LocalDateTime startedParkingAddingHours = startedParking.plusHours(i);
            if(isBetweenMondayAndSaturday(dayOfWeek)) {
                if(startedParkingAddingHours.getHour() >= 8 && startedParkingAddingHours.getHour() <= 15) {
                    price += HOURLY_PRICE_FROM_08_TO_16;
                } else {
                    price += HOURLY_PRICE_FROM_16_TO_08;
                }
            }
        }
    }

    // ---------- HELPER METHODS ---------
    private static long getMinutesToPayFor(LocalDateTime startedParking, LocalDateTime endedParking) {
        return ChronoUnit.MINUTES.between(startedParking, endedParking);
    }

    private static boolean isBetweenMondayAndSaturday(DayOfWeek dayOfWeek) {
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

    public static boolean isWeekend(DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
