package uz.pdp.appcommunicationcompany.model.dto.create;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    @NotNull
    @ApiModelProperty(example = "94")
    private Short code;

    @NotNull
    @ApiModelProperty(example = "9876543")
    private String number;

    @NotNull
    private String firstname;

    private String lastname;

    @NotNull
    private String login;

    @NotNull
    private String password;

    @NotNull
    @ApiModelProperty(example = "AA")
    private String passportSeries;

    @NotNull
    @ApiModelProperty(example = "4562178")
    private String passportNumber;

    @NotNull
    @ApiModelProperty(example = "PHYSICAL_PERSON")
    private String clientType;

    private Long branchId;

    private Double balance;

    private Double price;

    private Long tariffId;

    @ApiModelProperty(example = "true")
    private boolean serviceDebt;
}
