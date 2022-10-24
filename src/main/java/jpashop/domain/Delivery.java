package jpashop.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import static javax.persistence.FetchType.LAZY;

//@Entity
public class Delivery extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private String city;
    private String street;
    private String zipcode;
    private DeliveryStatus deliveryStatus;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

}
