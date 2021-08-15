package uz.pdp.appcommunicationcompany.model.resp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeRespDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String login;
    private String name;
}
