package uz.pdp.appcommunicationcompany.service;

import uz.pdp.appcommunicationcompany.model.dto.create.AttachPackageDto;
import uz.pdp.appcommunicationcompany.model.dto.create.AttachServiceDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;

public interface SubscriberService {
    ApiResponse getSubscribers();

    ApiResponse getSubscriber(Long id);

    ApiResponse getSubscriber(String number);

    ApiResponse changeTariff(String number, Long tariffId);

    ApiResponse attachToService(AttachServiceDto dto);

    ApiResponse attachToPackage(AttachPackageDto dto);
}
