package uz.pdp.appcommunicationcompany.service.impl;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.appcommunicationcompany.domain.Tariff;
import uz.pdp.appcommunicationcompany.domain.TariffOpportunity;
import uz.pdp.appcommunicationcompany.enums.ClientType;
import uz.pdp.appcommunicationcompany.enums.TariffType;
import uz.pdp.appcommunicationcompany.mapper.MapstructMapper;
import uz.pdp.appcommunicationcompany.model.dto.create.TariffDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;
import uz.pdp.appcommunicationcompany.repository.TariffRepo;
import uz.pdp.appcommunicationcompany.service.TariffService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TariffServiceImpl implements TariffService {
    private final TariffRepo tariffRepo;
    private final MapstructMapper mapper;

    @Override
    public ApiResponse getTariffs() {
        return new ApiResponse("OK", true, mapper.toTariffDto(tariffRepo.findAllByStatusTrue()));
    }

    @Override
    public ApiResponse getTariff(Long id) {
        Optional<Tariff> optionalTariff = tariffRepo.findByIdAndStatusTrue(id);
        return optionalTariff.map(tariff -> new ApiResponse("OK", true, mapper.toTariffDto(tariff))).orElseGet(() -> new ApiResponse("Tariff not found", false));
    }

    @Override
    public ApiResponse createTariff(TariffDto dto) {
        try {
            if (tariffRepo.existsByName(dto.getName()))
                return new ApiResponse("This tariff has already existed", false);
            Tariff tariff = new Tariff();
            List<TariffOpportunity> opportunities = new ArrayList<>();
            Map<String, Map<String, String>> map = new Gson().fromJson(dto.getOpportunities(), Map.class);
            map.forEach((s, longDoubleMap) -> {
                longDoubleMap.forEach((s1, s2) -> {
                    TariffOpportunity opportunity = new TariffOpportunity();
                    opportunity.setTariff(tariff);
                    opportunity.setKey(s);
                    opportunity.setAdditional(Boolean.parseBoolean(s1));
                    opportunity.setValue(s2);
                    opportunities.add(opportunity);
                });
            });

            tariff.setTariffType(TariffType.valueOf(dto.getTariffType()));
            tariff.setClientType(ClientType.valueOf(dto.getClientType()));
            tariff.setConnectPrice(dto.getConnectPrice());
            tariff.setName(dto.getName());
            tariff.setPrice(dto.getPrice());
            tariff.setDescription(dto.getDescription());
            tariff.setTariffOpportunities(opportunities);
            tariffRepo.save(tariff);
            return new ApiResponse("Created", true);
        } catch (Exception e) {
            return new ApiResponse("An error variant of the map was given, Please look to example in TariffDto.class", false);
        }
    }

    @Override
    public ApiResponse editTariff(Long id, TariffDto dto) {
        try {
            if (tariffRepo.existsByNameAndIdNot(dto.getName(), id))
                return new ApiResponse("This tariff has already existed", false);
            Optional<Tariff> optionalTariff = tariffRepo.findByIdAndStatusTrue(id);
            if (!optionalTariff.isPresent())
                return new ApiResponse("Tariff not found", false);
            Map<String, Map<String, String>> map = new Gson().fromJson(dto.getOpportunities(), Map.class);
            Tariff tariff = optionalTariff.get();
            List<TariffOpportunity> opportunities = tariff.getTariffOpportunities();

            map.forEach((s, stringStringMap) -> {
                stringStringMap.forEach((s1, s2) -> {
                    boolean exist = false;
                    for (TariffOpportunity opportunity : opportunities) {
                        if (opportunity.getKey().equals(s) && opportunity.isAdditional() == Boolean.parseBoolean(s1)) {
                            opportunity.setValue(s2);
                            exist = true;
                        }
                    }
                    if (!exist) {
                        TariffOpportunity opportunity = new TariffOpportunity();
                        opportunity.setTariff(tariff);
                        opportunity.setKey(s);
                        opportunity.setAdditional(Boolean.parseBoolean(s1));
                        opportunity.setValue(s2);
                        opportunities.add(opportunity);
                    }
                });
            });

            tariff.setTariffType(TariffType.valueOf(dto.getTariffType()));
            tariff.setClientType(ClientType.valueOf(dto.getClientType()));
            tariff.setConnectPrice(dto.getConnectPrice());
            tariff.setName(dto.getName());
            tariff.setPrice(dto.getPrice());
            tariff.setDescription(dto.getDescription());
            tariffRepo.save(tariff);
            return new ApiResponse("Edited", true);
        } catch (Exception e) {
            return new ApiResponse("An error variant of the map was given, Please look to example in TariffDto.class", false);
        }
    }

    @Override
    public ApiResponse deleteTariff(Long id) {
        Optional<Tariff> optionalTariff = tariffRepo.findByIdAndStatusTrue(id);
        if (!optionalTariff.isPresent())
            return new ApiResponse("Tariff not found", false);
        Tariff tariff = optionalTariff.get();
        tariff.setStatus(false);
        return new ApiResponse("Deleted", false);
    }

    @Override
    public ApiResponse getReport() {
        return new ApiResponse("OK", true, tariffRepo.getReport());
    }
}

