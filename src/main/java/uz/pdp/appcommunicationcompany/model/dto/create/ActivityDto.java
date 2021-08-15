package uz.pdp.appcommunicationcompany.model.dto.create;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class ActivityDto {
    @NotNull
    @ApiModelProperty(example = "949299117")
    private String fromNumber;

    @ApiModelProperty(example = "939998877 or null")
    private String toNumber;

    @NotNull
    @ApiModelProperty(example = "CALL_NETWORK")
    private String serviceCategory;

    @NotNull
    private Long amount;
}
