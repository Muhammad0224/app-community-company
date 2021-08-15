package uz.pdp.appcommunicationcompany.model.dto.create;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class PaymentDto {
    @NotNull
    private double amount;

    @NotNull
    private String number;

    @NotNull
    @ApiModelProperty(example = "PAYNET")
    private String paymentType;

    @NotNull
    private String payer;
}
