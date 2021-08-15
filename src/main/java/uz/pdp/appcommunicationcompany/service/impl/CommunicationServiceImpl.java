package uz.pdp.appcommunicationcompany.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.appcommunicationcompany.domain.ServiceCategory;
import uz.pdp.appcommunicationcompany.domain.ServiceEntity;
import uz.pdp.appcommunicationcompany.enums.ServiceCategoryEnum;
import uz.pdp.appcommunicationcompany.enums.TariffType;
import uz.pdp.appcommunicationcompany.mapper.MapstructMapper;
import uz.pdp.appcommunicationcompany.model.dto.create.ServiceDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;
import uz.pdp.appcommunicationcompany.repository.ServiceCategoryRepo;
import uz.pdp.appcommunicationcompany.repository.ServiceRepo;
import uz.pdp.appcommunicationcompany.service.CommunicationService;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunicationServiceImpl implements CommunicationService {
    private final ServiceRepo serviceRepo;

    private final MapstructMapper mapper;

    private final ServiceCategoryRepo serviceCategoryRepo;

    @Override
    public ApiResponse getServices() {
        return new ApiResponse("OK", true, mapper.toServiceDto(serviceRepo.findAll()));
    }

    @Override
    public ApiResponse getService(Long id) {
        Optional<ServiceEntity> optionalService = serviceRepo.findByIdAndStatusTrue(id);
        return optionalService.map(service -> new ApiResponse("OK", true, mapper.toServiceDto(service))).orElseGet(() -> new ApiResponse("Service not found", false));
    }

    @Override
    public ApiResponse createService(ServiceDto dto) {
        if (serviceRepo.existsByName(dto.getName()))
            return new ApiResponse("Service has already existed", false);
        ServiceEntity service = new ServiceEntity();
        if (!saveService(dto, service))
            return new ApiResponse("Wrong value of service category enum. (Look enums)", false);
        return new ApiResponse("Created", true);
    }

    @Override
    public ApiResponse editService(ServiceDto dto, Long id) {
        if (serviceRepo.existsByNameAndIdNot(dto.getName(), id))
            return new ApiResponse("Service has already existed", false);

        Optional<ServiceEntity> optionalService = serviceRepo.findByIdAndStatusTrue(id);
        if (!optionalService.isPresent())
            return new ApiResponse("Service not found", false);
        ServiceEntity service = optionalService.get();
        if (!saveService(dto, service))
            return new ApiResponse("Wrong value of service category enum. (Look enums)", false);
        return new ApiResponse("Updated", true);
    }

    private boolean saveService(ServiceDto dto, ServiceEntity service) {
        try {
            ServiceCategory serviceCategory;
            Optional<ServiceCategory> optionalServiceCategory = serviceCategoryRepo.findByName(ServiceCategoryEnum.valueOf(dto.getServiceCategory()));
            if (optionalServiceCategory.isPresent()) {
                serviceCategory = optionalServiceCategory.get();
            } else {
                serviceCategory = new ServiceCategory();
                serviceCategory.setName(ServiceCategoryEnum.valueOf(dto.getServiceCategory()));
            }

            service.setServiceCategory(serviceCategoryRepo.save(serviceCategory));
            service.setName(dto.getName());
            service.setType(TariffType.valueOf(dto.getType()));
            service.setPrice(dto.getPrice());
            serviceRepo.save(service);
            return true;
        } catch (Exception e) {
            return false;
        }


    }

    @Override
    public ApiResponse deleteService(Long id) {
        Optional<ServiceEntity> optionalServiceEntity = serviceRepo.findByIdAndStatusTrue(id);
        if (!optionalServiceEntity.isPresent())
            return new ApiResponse("Service not found", false);
        ServiceEntity service = optionalServiceEntity.get();
        service.setStatus(false);
        serviceRepo.save(service);
        return new ApiResponse("Deleted", true);
    }

    @Override
    public ApiResponse getTopServices() {
        return new ApiResponse("OK", true, serviceRepo.getTopServices());
    }

    @Override
    public ApiResponse getPoorServices() {
        return new ApiResponse("OK", true, serviceRepo.getPoorServices());
    }
}
