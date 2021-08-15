package uz.pdp.appcommunicationcompany.model.req;

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
public class LoginDto {
    @NotNull
    @ApiModelProperty(example = "bahrom")
    private String login;

    @NotNull
    @ApiModelProperty(example = "12345")
    private String password;
}
