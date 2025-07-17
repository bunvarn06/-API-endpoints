package co.istad.mobliebankingapi.init;

import co.istad.mobliebankingapi.domain.CustomerSegment;
import co.istad.mobliebankingapi.repository.CustomerSegmentRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final CustomerSegmentRepository customerSegmentRepository;

    @PostConstruct
    void initData() {
        initCustomerSegments();
    }

    void initCustomerSegments() {
        if (customerSegmentRepository.count() == 0) {
            CustomerSegment gold = new CustomerSegment();
            gold.setSegment("Gold");
            gold.setDeleted(false);
            gold.setDescription("Gold Segment Description");

            CustomerSegment sliver = new CustomerSegment();
            sliver.setSegment("Sliver");
            sliver.setDeleted(false);
            sliver.setDescription("Sliver Segment Description");

            CustomerSegment regular = new CustomerSegment();
            regular.setSegment("Regular");
            regular.setDeleted(false);
            regular.setDescription("Regular Segment Description");


            customerSegmentRepository.saveAll(List.of(gold, sliver, regular));
        }
    }

}
