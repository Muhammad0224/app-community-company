package uz.pdp.appcommunicationcompany.model.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    @NotNull
    private String firstname;

    private String lastname;

    @NotNull
    private String login;

    @NotNull
    private String password;

    @NotNull
    private Long branchId;
}
