package uz.pdp.appcommunicationcompany.model.dto.create;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class AttachServiceDto {
    @NotNull
    private Long subscriberId;

    @NotNull
    private Long serviceId;
}
