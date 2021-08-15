package uz.pdp.appcommunicationcompany.model.resp;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PackageRespDto {
    private String name;
    private String key;
    private Long value;
    private double price;
    private Integer validityDay;
    private boolean addResidue = false;
    private boolean status;
}
