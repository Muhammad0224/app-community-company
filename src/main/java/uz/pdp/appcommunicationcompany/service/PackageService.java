package uz.pdp.appcommunicationcompany.service;

import uz.pdp.appcommunicationcompany.model.dto.create.PackageDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;

public interface PackageService {
    ApiResponse getPackages();

    ApiResponse getPackage(Long id);

    ApiResponse createPackage(PackageDto dto);

    ApiResponse editPackage(Long id, PackageDto dto);

    ApiResponse deletePackage(Long id);

    ApiResponse getReport();
}
