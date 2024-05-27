package progging.johannes.moonpark.parking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TariffZoneCalculatorTest {

    TariffZoneCalculator tariffZoneCalculator;

    @BeforeEach
    public void init() {
        tariffZoneCalculator = new TariffZoneCalculator();
    }

    @Nested
    class M1Tests {
        @Test
        void calculatePriceM1_givenExactlyOneHourParking_shouldReturnPrice60() {
            LocalDateTime startedParking = LocalDateTime.of(2024, 5, 26, 2, 3, 4);
            LocalDateTime endedParking = LocalDateTime.of(2024, 5, 26, 3, 3, 4);

            long result = tariffZoneCalculator.calculatePriceM1(startedParking, endedParking);
            assertEquals(60, result);
        }

        @Test
        void calculatePriceM1_givenOneMinuteParking_shouldReturnPrice60() {
            LocalDateTime startedParking = LocalDateTime.of(2024, 5, 26, 2, 3, 4);
            LocalDateTime endedParking = LocalDateTime.of(2024, 5, 26, 2, 4, 4);

            long result = tariffZoneCalculator.calculatePriceM1(startedParking, endedParking);
            assertEquals(60, result);
        }

        @Test
        void calculatePriceM1_givenOneHourAndOneMinuteParking_shouldReturnPrice120() {
            LocalDateTime startedParking = LocalDateTime.of(2024, 5, 26, 2, 3, 4);
            LocalDateTime endedParking = LocalDateTime.of(2024, 5, 26, 3, 4, 4);

            long result = tariffZoneCalculator.calculatePriceM1(startedParking, endedParking);
            assertEquals(120, result);
        }

        @Test
        void calculatePriceM1_givenOneHourAndFiftyNineMinutesParking_shouldReturnPrice120() {
            LocalDateTime startedParking = LocalDateTime.of(2024, 5, 26, 2, 0, 4);
            LocalDateTime endedParking = LocalDateTime.of(2024, 5, 26, 3, 59, 4);

            long result = tariffZoneCalculator.calculatePriceM1(startedParking, endedParking);
            assertEquals(120, result);
        }

        @Test
        void calculatePriceM1_givenOneDayOneHourAndFiftyNineMinutesParking_shouldReturnPrice1560() {
            LocalDateTime startedParking = LocalDateTime.of(2024, 5, 26, 2, 0, 4);
            LocalDateTime endedParking = LocalDateTime.of(2024, 5, 27, 3, 59, 4);

            long result = tariffZoneCalculator.calculatePriceM1(startedParking, endedParking);
            assertEquals(1560, result);
        }
    }

    @Nested
    class M2Tests {
        @Test
        void calculatePrice_given1HrParkingOnWeekdays_shouldReturnPrice100() {
            LocalDateTime startedParking = LocalDateTime.of(2024, 5, 27, 2, 0, 4);
            LocalDateTime endedParking = LocalDateTime.of(2024, 5, 27, 3, 0, 4);

            long result = tariffZoneCalculator.calculatePriceM2(startedParking, endedParking);
            assertEquals(100, result);
        }

        @Test
        void calculatePrice_given1Hr59MinParkingOnWeekdays_shouldReturnPrice200() {
            LocalDateTime startedParking = LocalDateTime.of(2024, 5, 27, 2, 0, 4);
            LocalDateTime endedParking = LocalDateTime.of(2024, 5, 27, 3, 59, 4);

            long result = tariffZoneCalculator.calculatePriceM2(startedParking, endedParking);
            assertEquals(200, result);
        }


        @Test
        void calculatePrice_given1Hrs59MinMinutesParkingDuringWeekend_shouldReturnPrice400() {
            LocalDateTime startedParking = LocalDateTime.of(2024, 5, 26, 2, 0, 4);
            LocalDateTime endedParking = LocalDateTime.of(2024, 5, 26, 3, 59, 4);

            long result = tariffZoneCalculator.calculatePriceM2(startedParking, endedParking);
            assertEquals(400, result);
        }

        @Test
        void calculatePrice_given2HrsParkingStartingOnAWeekDayButEndingDuringTheWeekend_shouldReturnPrice2500() {
            LocalDateTime startedParkingOnSaturday = LocalDateTime.of(2024, 5, 24, 23, 0, 4);
            LocalDateTime endedParkingOnSunday = LocalDateTime.of(2024, 5, 25, 11, 0, 4);

            long result = tariffZoneCalculator.calculatePriceM2(startedParkingOnSaturday, endedParkingOnSunday);
            assertEquals(2500, result);
        }

        @Test
        void calculatePrice_given2HrsParkingStartingDuringTheWeekendAndEndingDuringAWeekDay_shouldReturnPrice1400() {
            LocalDateTime startedParkingOnSaturday = LocalDateTime.of(2024, 5, 26, 23, 0, 4);
            LocalDateTime endedParkingOnSunday = LocalDateTime.of(2024, 5, 27, 11, 0, 4);

            long result = tariffZoneCalculator.calculatePriceM2(startedParkingOnSaturday, endedParkingOnSunday);
            assertEquals(1400, result);
        }
    }

    @Nested
    class M3Tests {
        @Test
        void calculatePrice_given1HrParkingOnMonday10To11_shouldReturnPrice0() {
            LocalDateTime startedParking = LocalDateTime.of(2024, 5, 27, 10, 0, 4);
            LocalDateTime endedParking = LocalDateTime.of(2024, 5, 27, 11, 0, 4);

            long result = tariffZoneCalculator.calculatePriceM3(startedParking, endedParking);
            assertEquals(0, result);
        }

        @Test
        void calculatePrice_given1Hr59MinParkingSaturday1700To1859_shouldReturnPrice357() {
            LocalDateTime startedParking = LocalDateTime.of(2024, 5, 27, 17, 0, 0);
            LocalDateTime endedParking = LocalDateTime.of(2024, 5, 27, 18, 59, 0);

            long result = tariffZoneCalculator.calculatePriceM3(startedParking, endedParking);
            assertEquals(357, result);
        }


        @Test
        void calculatePrice_given1Hrs59MinMinutesParkingDuringSunday_shouldReturnPrice0() {
            LocalDateTime startedParking = LocalDateTime.of(2024, 5, 26, 2, 0, 4);
            LocalDateTime endedParking = LocalDateTime.of(2024, 5, 26, 3, 59, 4);

            long result = tariffZoneCalculator.calculatePriceM3(startedParking, endedParking);
            assertEquals(0, result);
        }
    }
}