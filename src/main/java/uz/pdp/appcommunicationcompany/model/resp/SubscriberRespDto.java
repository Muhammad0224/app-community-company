package uz.pdp.appcommunicationcompany.model.resp;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

@Setter
@Getter
public class SubscriberRespDto {
    private String number;
    private String tariffName;
    private LocalDate activationDate;
    private LocalDate expirationDate;
    private LocalDate creationDate;
    private boolean serviceDebt;
    private boolean status = true;
}
