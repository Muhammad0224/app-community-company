package uz.pdp.appcommunicationcompany.service;

import uz.pdp.appcommunicationcompany.model.dto.create.ServiceDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;

public interface CommunicationService {
    ApiResponse getServices();

    ApiResponse getService(Long id);

    ApiResponse createService(ServiceDto dto);

    ApiResponse editService(ServiceDto dto, Long id);

    ApiResponse deleteService(Long id);

    ApiResponse getTopServices();

    ApiResponse getPoorServices();
}
