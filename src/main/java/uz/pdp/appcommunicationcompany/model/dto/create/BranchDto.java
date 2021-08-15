package uz.pdp.appcommunicationcompany.model.dto.create;

import io.swagger.annotations.ApiModelProperty;
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
public class BranchDto {
    @NotNull
    private String name;

    @ApiModelProperty(example = "FILIAL or empty value")
    private BranchType branchType;

    @NotNull
    @ApiModelProperty(example = "akrom")
    private String managerLogin;

    public BranchDto(String name) {
        this.name = name;
    }
}
