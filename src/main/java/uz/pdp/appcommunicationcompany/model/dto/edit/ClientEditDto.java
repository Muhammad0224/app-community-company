package uz.pdp.appcommunicationcompany.model.dto.edit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientEditDto {
    @NotNull
    private String firstname;

    private String lastname;

    @NotNull
    private String login;

    @NotNull
    private String password;
}
