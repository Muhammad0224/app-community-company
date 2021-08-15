package uz.pdp.appcommunicationcompany.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.appcommunicationcompany.domain.USSDCode;
import uz.pdp.appcommunicationcompany.model.dto.create.USSDCodeDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;
import uz.pdp.appcommunicationcompany.repository.USSDRepo;
import uz.pdp.appcommunicationcompany.service.USSDService;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class USSDServiceImpl implements USSDService {
    private final USSDRepo ussdRepo;

    @Override
    public ApiResponse getCodes() {
        return new ApiResponse("OK", true, ussdRepo.findAllByStatusTrue());
    }

    @Override
    public ApiResponse getCode(String code) {
        Optional<USSDCode> optionalUSSDCode = ussdRepo.findByCodeAndStatusTrue(code);
        return optionalUSSDCode.map(ussdCode -> new ApiResponse("OK", true, ussdCode)).orElseGet(() -> new ApiResponse("Code not found", false));
    }

    @Override
    public ApiResponse deActivate(String code) {
        Optional<USSDCode> optionalUSSDCode = ussdRepo.findByCodeAndStatusTrue(code);
        if (!optionalUSSDCode.isPresent())
            return new ApiResponse("Code not found", false);
        USSDCode ussdCode = optionalUSSDCode.get();
        ussdCode.setStatus(false);
        ussdRepo.save(ussdCode);
        return new ApiResponse("OK", true);
    }

    @Override
    public ApiResponse createCode(USSDCodeDto dto) {
        if (ussdRepo.existsByCode(dto.getCode()))
            return new ApiResponse("Code has already existed",false);
        USSDCode code = new USSDCode();
        code.setCode(dto.getCode());
        code.setDescription(dto.getDescription());
        ussdRepo.save(code);
        return new ApiResponse("Created", true);
    }

    @Override
    public ApiResponse editCode(USSDCodeDto dto, Long id) {
        if (ussdRepo.existsByCodeAndIdNot(dto.getCode(), id))
            return new ApiResponse("Code has already existed",false);
        Optional<USSDCode> optionalUSSDCode = ussdRepo.findByIdAndStatusTrue(id);
        if (!optionalUSSDCode.isPresent())
            return new ApiResponse("Code not found", false);
        USSDCode code = optionalUSSDCode.get();
        code.setCode(dto.getCode());
        code.setDescription(dto.getDescription());
        ussdRepo.save(code);
        return new ApiResponse("Updated", true);
    }
}
