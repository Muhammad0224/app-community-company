package uz.pdp.appcommunicationcompany.model.dto.create;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ServiceDto {
    @NotNull
    private String name;

    @NotNull
    @ApiModelProperty(example = "MB")
    private String serviceCategory;

    @NotNull
    @ApiModelProperty(example = "MONTHLY")
    private String type;

    private double price;
}
