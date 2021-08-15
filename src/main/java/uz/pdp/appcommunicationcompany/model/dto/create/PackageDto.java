package uz.pdp.appcommunicationcompany.model.dto.create;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
public class PackageDto {
    @NotNull
    private String name;

    @NotNull
    private String key;

    @NotNull
    private Long value;

    @NotNull
    private double price;

    @NotNull
    private Integer validityDay;

    private boolean addResidue;

    private List<Long> tariffsId;
}
