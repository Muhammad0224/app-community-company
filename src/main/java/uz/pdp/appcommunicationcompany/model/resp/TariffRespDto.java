package uz.pdp.appcommunicationcompany.model.resp;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.appcommunicationcompany.enums.ClientType;
import uz.pdp.appcommunicationcompany.enums.TariffType;

@Setter
@Getter
public class TariffRespDto {
    private String name;

    private TariffType tariffType;

    private Double price;

    private ClientType clientType;

    private Double connectPrice;

    private String description;

}
