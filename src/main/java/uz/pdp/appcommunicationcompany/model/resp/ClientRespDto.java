package uz.pdp.appcommunicationcompany.model.resp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRespDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String login;
    private String passport;
    private String clientType;
}
