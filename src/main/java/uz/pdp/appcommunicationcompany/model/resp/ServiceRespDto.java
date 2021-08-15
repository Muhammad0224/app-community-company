package uz.pdp.appcommunicationcompany.model.resp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServiceRespDto {
    private String name;

    private String serviceCategory;

    private String type;

    private String price;

    private boolean status;
}
