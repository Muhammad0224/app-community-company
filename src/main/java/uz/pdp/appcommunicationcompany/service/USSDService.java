package uz.pdp.appcommunicationcompany.service;

import uz.pdp.appcommunicationcompany.model.dto.create.USSDCodeDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;

public interface USSDService {

    ApiResponse getCodes();

    ApiResponse getCode(String code);

    ApiResponse deActivate(String code);

    ApiResponse createCode(USSDCodeDto dto);

    ApiResponse editCode(USSDCodeDto dto, Long id);
}
