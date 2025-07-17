package co.istad.mobliebankingapi;

import co.istad.mobliebankingapi.repository.CustomerSegmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MoblieBankingApiApplicationTests {


    @Autowired
    private CustomerSegmentRepository customerSegmentRepository;

    @Test
    void testFetchType(){
        customerSegmentRepository.findAll().forEach(customerSegment -> {
            System.out.println(customerSegment.getCustomers());
        });
    }

    @Test
    void contextLoads() {
    }

}
