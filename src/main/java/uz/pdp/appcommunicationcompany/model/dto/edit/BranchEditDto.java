package uz.pdp.appcommunicationcompany.model.dto.edit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.appcommunicationcompany.enums.BranchType;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BranchEditDto {
    @NotNull
    private String name;

    @NotNull
    private String managerLogin;

    public BranchEditDto(String name) {
        this.name = name;
    }
}
