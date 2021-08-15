package uz.pdp.appcommunicationcompany.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.appcommunicationcompany.domain.Package;
import uz.pdp.appcommunicationcompany.domain.Tariff;
import uz.pdp.appcommunicationcompany.mapper.MapstructMapper;
import uz.pdp.appcommunicationcompany.model.dto.create.PackageDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;
import uz.pdp.appcommunicationcompany.repository.PackageRepo;
import uz.pdp.appcommunicationcompany.repository.TariffRepo;
import uz.pdp.appcommunicationcompany.service.PackageService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PackageServiceImpl implements PackageService {
    private final PackageRepo packageRepo;

    private final MapstructMapper mapper;

    private final TariffRepo tariffRepo;

    @Override
    public ApiResponse getPackages() {
        return new ApiResponse("OK", true, mapper.toPackageDto(packageRepo.findAll()));
    }

    @Override
    public ApiResponse getPackage(Long id) {
        Optional<Package> optionalPackage = packageRepo.findById(id);
        return optionalPackage.map(aPackage -> new ApiResponse("OK", true, mapper.toPackageDto(aPackage))).orElseGet(() -> new ApiResponse("Package not found", false));
    }

    @Override
    public ApiResponse createPackage(PackageDto dto) {
        if (packageRepo.existsByName(dto.getName()))
            return new ApiResponse("Package has already existed", false);
        List<Tariff> tariffs = new ArrayList<>();
        for (Long aLong : dto.getTariffsId()) {
            Optional<Tariff> optionalTariff = tariffRepo.findByIdAndStatusTrue(aLong);
            if (!optionalTariff.isPresent())
                return new ApiResponse("Tariff not found", false);
            tariffs.add(optionalTariff.get());
        }
        Package aPackage = new Package();
        aPackage.setAddResidue(dto.isAddResidue());
        aPackage.setKey(dto.getKey());
        aPackage.setName(dto.getName());
        aPackage.setPrice(dto.getPrice());
        aPackage.setTariffs(tariffs);
        aPackage.setValidityDay(dto.getValidityDay());
        aPackage.setValue(dto.getValue());
        packageRepo.save(aPackage);

        return new ApiResponse("Created", true);
    }

    @Override
    public ApiResponse editPackage(Long id, PackageDto dto) {
        if (packageRepo.existsByNameAndIdNot(dto.getName(),id))
            return new ApiResponse("Package has already existed", false);
        Optional<Package> optionalPackage = packageRepo.findByIdAndStatusTrue(id);
        if (!optionalPackage.isPresent())
            return new ApiResponse("Package not found", false);
        List<Tariff> tariffs = new ArrayList<>();
        for (Long aLong : dto.getTariffsId()) {
            Optional<Tariff> optionalTariff = tariffRepo.findByIdAndStatusTrue(aLong);
            if (!optionalTariff.isPresent())
                return new ApiResponse("Tariff not found", false);
            tariffs.add(optionalTariff.get());
        }

        Package aPackage = optionalPackage.get();
        aPackage.setValue(dto.getValue());
        aPackage.setTariffs(tariffs);
        aPackage.setName(dto.getName());
        aPackage.setKey(dto.getKey());
        aPackage.setPrice(dto.getPrice());
        aPackage.setValidityDay(dto.getValidityDay());
        aPackage.setAddResidue(dto.isAddResidue());
        packageRepo.save(aPackage);
        return new ApiResponse("Updated", true);
    }

    @Override
    public ApiResponse deletePackage(Long id) {
        Optional<Package> optionalPackage = packageRepo.findByIdAndStatusTrue(id);
        if (!optionalPackage.isPresent())
            return new ApiResponse("Package not found", false);
        Package aPackage = optionalPackage.get();
        aPackage.setStatus(false);
        packageRepo.save(aPackage);
        return new ApiResponse("Deleted", true);
    }

    @Override
    public ApiResponse getReport() {
        return new ApiResponse("OK", true, packageRepo.getReport());
    }
}
