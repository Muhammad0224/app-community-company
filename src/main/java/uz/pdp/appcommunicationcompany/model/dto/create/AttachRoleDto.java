package uz.pdp.appcommunicationcompany.model.dto.create;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class AttachRoleDto {
    @NotNull
    @ApiModelProperty(example = "bahrom")
    private String login;

    @NotNull
    @ApiModelProperty(example = "ROLE_HR_MANAGER")
    private String roleName;
}
