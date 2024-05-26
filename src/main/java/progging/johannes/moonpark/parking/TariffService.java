package progging.johannes.moonpark.parking;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TariffService {

    TariffZoneCalculator tariffZoneCalculator;

    public TariffService() {
        tariffZoneCalculator = new TariffZoneCalculator();
    }

    long getTariffPricing(LocalDateTime startedParking, LocalDateTime endedParking, TariffZone tariffZone) {
        return tariffZoneCalculator.calculatePriceBasedOnTariffZone(startedParking, endedParking, tariffZone);
    }
}
