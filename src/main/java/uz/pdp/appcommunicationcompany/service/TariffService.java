package uz.pdp.appcommunicationcompany.service;

import uz.pdp.appcommunicationcompany.model.dto.create.TariffDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;

public interface TariffService {
    ApiResponse getTariffs();

    ApiResponse getTariff(Long id);

    ApiResponse createTariff(TariffDto dto);

    ApiResponse editTariff(Long id, TariffDto dto);

    ApiResponse deleteTariff(Long id);

    ApiResponse getReport();
}
