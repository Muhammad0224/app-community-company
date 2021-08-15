package uz.pdp.appcommunicationcompany.model.resp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BranchRespDto {
    private Long id;
    private String name;
    private String manager;
}
