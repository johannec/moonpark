package progging.johannes.moonpark.parking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("api/takst")
public class TariffController {

    private final TariffService tariffService;

    @Autowired
    public TariffController(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    @GetMapping()
    public long getTariffPricing(@RequestParam LocalDateTime startedParking,
                                 @RequestParam LocalDateTime endedParking,
                                 @RequestParam TariffZone tariffZone) {
        log.info("Received params: {}, {}, {}", startedParking, endedParking, tariffZone);
        return tariffService.getTariffPricing(startedParking, endedParking, tariffZone);
    }


}
