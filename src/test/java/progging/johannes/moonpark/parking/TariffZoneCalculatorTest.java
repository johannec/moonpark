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
    class calculatePriceM1 {
        @Test
        void givenExactlyOneHourParking_shouldReturnPrice60() {
            LocalDateTime startedParking = LocalDateTime.of(2024, 5, 26, 2, 3, 4);
            LocalDateTime endedParking = LocalDateTime.of(2024, 5, 26, 3, 3, 4);

            long result = tariffZoneCalculator.calculatePriceM1(startedParking, endedParking);
            assertEquals(60, result);
        }

        @Test
        void givenOneMinuteParking_shouldReturnPrice60() {
            LocalDateTime startedParking = LocalDateTime.of(2024, 5, 26, 2, 3, 4);
            LocalDateTime endedParking = LocalDateTime.of(2024, 5, 26, 2, 4, 4);

            long result = tariffZoneCalculator.calculatePriceM1(startedParking, endedParking);
            assertEquals(60, result);
        }

        @Test
        void givenOneHourAndOneMinuteParking_shouldReturnPrice120() {
            LocalDateTime startedParking = LocalDateTime.of(2024, 5, 26, 2, 3, 4);
            LocalDateTime endedParking = LocalDateTime.of(2024, 5, 26, 3, 4, 4);

            long result = tariffZoneCalculator.calculatePriceM1(startedParking, endedParking);
            assertEquals(120, result);
        }

        @Test
        void givenOneHourAndFiftyNineMinutesParking_shouldReturnPrice120() {
            LocalDateTime startedParking = LocalDateTime.of(2024, 5, 26, 2, 0, 4);
            LocalDateTime endedParking = LocalDateTime.of(2024, 5, 26, 3, 59, 4);

            long result = tariffZoneCalculator.calculatePriceM1(startedParking, endedParking);
            assertEquals(120, result);
        }

        @Test
        void givenOneDayOneHourAndFiftyNineMinutesParking_shouldReturnPrice1560() {
            LocalDateTime startedParking = LocalDateTime.of(2024, 5, 26, 2, 0, 4);
            LocalDateTime endedParking = LocalDateTime.of(2024, 5, 27, 3, 59, 4);

            long result = tariffZoneCalculator.calculatePriceM1(startedParking, endedParking);
            assertEquals(1560, result);
        }
    }

    @Nested
    class calculatePriceM2 {
        @Test
        void given1HrParkingOnWeekdays_shouldReturnPrice100() {
            LocalDateTime startedParking = LocalDateTime.of(2024, 5, 27, 2, 0, 4);
            LocalDateTime endedParking = LocalDateTime.of(2024, 5, 27, 3, 0, 4);

            long result = tariffZoneCalculator.calculatePriceM2(startedParking, endedParking);
            assertEquals(100, result);
        }

        @Test
        void given1Hr59MinParkingOnWeekdays_shouldReturnPrice200() {
            LocalDateTime startedParking = LocalDateTime.of(2024, 5, 27, 2, 0, 4);
            LocalDateTime endedParking = LocalDateTime.of(2024, 5, 27, 3, 59, 4);

            long result = tariffZoneCalculator.calculatePriceM2(startedParking, endedParking);
            assertEquals(200, result);
        }


        @Test
        void given1Hrs59MinMinutesParkingDuringWeekend_shouldReturnPrice400() {
            LocalDateTime startedParking = LocalDateTime.of(2024, 5, 26, 2, 0, 4);
            LocalDateTime endedParking = LocalDateTime.of(2024, 5, 26, 3, 59, 4);

            long result = tariffZoneCalculator.calculatePriceM2(startedParking, endedParking);
            assertEquals(400, result);
        }

        @Test
        void given2HrsParkingStartingOnAWeekDayButEndingDuringTheWeekend_shouldReturnPrice2500() {
            LocalDateTime startedParkingOnSaturday = LocalDateTime.of(2024, 5, 24, 23, 0, 4);
            LocalDateTime endedParkingOnSunday = LocalDateTime.of(2024, 5, 25, 11, 0, 4);

            long result = tariffZoneCalculator.calculatePriceM2(startedParkingOnSaturday, endedParkingOnSunday);
            assertEquals(2500, result);
        }

        @Test
        void given2HrsParkingStartingDuringTheWeekendAndEndingDuringAWeekDay_shouldReturnPrice1400() {
            LocalDateTime startedParkingOnSaturday = LocalDateTime.of(2024, 5, 26, 23, 0, 4);
            LocalDateTime endedParkingOnSunday = LocalDateTime.of(2024, 5, 27, 11, 0, 4);

            long result = tariffZoneCalculator.calculatePriceM2(startedParkingOnSaturday, endedParkingOnSunday);
            assertEquals(1400, result);
        }
    }

    @Nested
    class calculatePriceM3 {
        @Test
        void given1HrParkingOnMonday10To11_shouldReturnPrice0() {
            LocalDateTime startedParking = LocalDateTime.of(2024, 5, 27, 10, 0, 4);
            LocalDateTime endedParking = LocalDateTime.of(2024, 5, 27, 11, 0, 4);

            long result = tariffZoneCalculator.calculatePriceM3(startedParking, endedParking);
            assertEquals(0, result);
        }

        @Test
        void given2HrParkingOnMonday10To12_shouldReturnPrice120() {
            LocalDateTime startedParking = LocalDateTime.of(2024, 5, 27, 10, 0, 0);
            LocalDateTime endedParking = LocalDateTime.of(2024, 5, 27, 12, 0, 0);

            long result = tariffZoneCalculator.calculatePriceM3(startedParking, endedParking);
            assertEquals(120, result);
        }

        @Test
        void given2HrParkingOnMonday16To17_shouldReturnPrice180() {
            LocalDateTime startedParking = LocalDateTime.of(2024, 5, 27, 16, 0, 0);
            LocalDateTime endedParking = LocalDateTime.of(2024, 5, 27, 17, 0, 0);

            long result = tariffZoneCalculator.calculatePriceM3(startedParking, endedParking);
            assertEquals(180, result);
        }

        @Test
        void given1Hr59MinParkingSaturday1700To1859_shouldReturnPrice357() {
            LocalDateTime startedParking = LocalDateTime.of(2024, 5, 25, 17, 0, 0);
            LocalDateTime endedParking = LocalDateTime.of(2024, 5, 25, 18, 59, 0);

            long result = tariffZoneCalculator.calculatePriceM3(startedParking, endedParking);
            assertEquals(357, result);
        }


        @Test
        void given1Hrs59MinMinutesParkingDuringSunday_shouldReturnPrice0() {
            LocalDateTime startedParking = LocalDateTime.of(2024, 5, 26, 2, 0, 4);
            LocalDateTime endedParking = LocalDateTime.of(2024, 5, 26, 3, 59, 4);

            long result = tariffZoneCalculator.calculatePriceM3(startedParking, endedParking);
            assertEquals(0, result);
        }

        @Test
        void given10HrsParkingStartingOnASaturdayButEndingOnSunday_shouldReturnPrice180() {
            LocalDateTime startedParkingOnSaturday = LocalDateTime.of(2024, 5, 25, 23, 0, 0);
            LocalDateTime endedParkingOnSunday = LocalDateTime.of(2024, 5, 26, 11, 0, 0);

            long result = tariffZoneCalculator.calculatePriceM3(startedParkingOnSaturday, endedParkingOnSunday);
            assertEquals(180, result);
        }

        @Test
        void given10HrsParkingStartingOnSundayAndEndingOnMonday_shouldReturnPrice() {
            LocalDateTime startedParkingOnSaturday = LocalDateTime.of(2024, 5, 26, 23, 0, 0);
            LocalDateTime endedParkingOnSunday = LocalDateTime.of(2024, 5, 27, 11, 0, 0);

            long result = tariffZoneCalculator.calculatePriceM3(startedParkingOnSaturday, endedParkingOnSunday);
            assertEquals(1800, result);
        }
    }
}