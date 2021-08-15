package uz.pdp.appcommunicationcompany.model.dto.create;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TariffDto {
    @NotNull
    private String name;

    @NotNull
    @ApiModelProperty(example = "MONTHLY")
    private String tariffType;

    @NotNull
    private Double price;

    @NotNull
    @ApiModelProperty(example = "PHYSICAL_PERSON")
    private String clientType;

    @NotNull
    private Double connectPrice;

    private String description;

    @NotNull
// Example   {'MB': {'true': '1000', 'false': '30' }, 'SMS': { 'true': '500', 'false': '20' }, 'CALL_NETWORK': { 'true': '700', 'false': '10' }, 'CALL_OUT_NETWORK': { 'true': '200', 'false': '30' } }
    private String opportunities;
}
