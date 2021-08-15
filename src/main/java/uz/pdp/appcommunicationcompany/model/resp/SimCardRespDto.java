package uz.pdp.appcommunicationcompany.model.resp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimCardRespDto {
    private Long id;
    private String number;
    private String client;
    private String branch;
    private Double balance;
    private Double price;
    private boolean status;
}
