package uz.pdp.appcommunicationcompany.model.resp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentRespDto {
    private double amount;
    private String subscriberNumber;
    private String createdAt;
    private String paymentType;
    private String payer;
}
