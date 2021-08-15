package uz.pdp.appcommunicationcompany.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.appcommunicationcompany.domain.*;
import uz.pdp.appcommunicationcompany.domain.Package;
import uz.pdp.appcommunicationcompany.enums.TariffType;
import uz.pdp.appcommunicationcompany.mapper.MapstructMapper;
import uz.pdp.appcommunicationcompany.model.dto.create.AttachPackageDto;
import uz.pdp.appcommunicationcompany.model.dto.create.AttachServiceDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;
import uz.pdp.appcommunicationcompany.repository.*;
import uz.pdp.appcommunicationcompany.service.SubscriberService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class SubscribeServiceImpl implements SubscriberService {
    private final SubscriberRepo subscriberRepo;

    private final SimCardServiceImpl simCardService;

    private final SimCardRepo simCardRepo;

    private final MapstructMapper mapper;

    private final TariffRepo tariffRepo;

    private final ResidueOpportunityRepo residueOpportunityRepo;

    private final ServiceRepo serviceRepo;

    private final PackageRepo packageRepo;

    private final ActivityAndActivityType activityAndActivityType;

    public void createSubscriber(SimCard simCard, Tariff tariff, boolean isDebt) {
        LocalDate now = LocalDate.now();

        Subscriber subscriber = new Subscriber();
        double rate = 1;
        if (tariff.getTariffType() == TariffType.MONTHLY) {
            subscriber.setExpirationDate(LocalDate.of(now.getYear(), now.getMonth().plus(1), 1));
            rate = ((double) Period.between(now, subscriber.getExpirationDate()).getDays()) / now.getMonth().length(now.isLeapYear());
        } else {
            subscriber.setExpirationDate(now.plusDays(1));
        }

        List<ResidueOpportunity> opportunities = new ArrayList<>();
        for (TariffOpportunity tariffOpportunity : tariff.getTariffOpportunities()) {
            if (tariffOpportunity.isAdditional()) {
                ResidueOpportunity opportunity = new ResidueOpportunity();
                opportunity.setTariff(true);
                opportunity.setKey(tariffOpportunity.getKey());
                opportunity.setValue((long) (Long.parseLong(tariffOpportunity.getValue()) * rate));
                opportunity.setSubscriber(subscriber);
                opportunities.add(opportunity);
            }
        }

        subscriber.setServiceDebt(isDebt);
        subscriber.setActivationDate(now);

        subscriber.setSimCard(simCard);
        subscriber.setTariff(tariff);
        subscriber.setOpportunities(opportunities);
        subscriberRepo.save(subscriber);
        simCardService.decreaseBalance(simCardRepo.findByNumber(simCard.getNumber()).getId(), tariff.getPrice() * rate, "Tariff service");
    }

    @Override
    public ApiResponse getSubscribers() {
        return new ApiResponse("OK", true, mapper.toSubsDto(subscriberRepo.findAll()));
    }

    @Override
    public ApiResponse getSubscriber(Long id) {
        Optional<Subscriber> optionalSubscriber = subscriberRepo.findById(id);
        return optionalSubscriber.map(subscriber -> new ApiResponse("OK", true, mapper.toSubsDto(subscriber))).orElseGet(() -> new ApiResponse("Subscriber not found", false));
    }

    @Override
    public ApiResponse getSubscriber(String number) {
        Optional<Subscriber> optionalSubscriber = subscriberRepo.findBySimCard_Number_CodeAndSimCard_Number_Number(Short.parseShort(number.substring(0, 2)), number.substring(2));
        return optionalSubscriber.map(subscriber -> new ApiResponse("OK", true, mapper.toSubsDto(subscriber))).orElseGet(() -> new ApiResponse("Subscriber not found", false));
    }



    @Override
    public ApiResponse changeTariff(String number, Long tariffId) {
        Optional<Tariff> optionalTariff = tariffRepo.findByIdAndStatusTrue(tariffId);
        if (!optionalTariff.isPresent())
            return new ApiResponse("Tariff not found", false);
        ApiResponse apiResponse = simCardService.getSubscriberFromNumber(number);
        if (!apiResponse.isStatus())
            return new ApiResponse("Subscriber not found", false);
        Tariff tariff = optionalTariff.get();
        Subscriber subscriber = (Subscriber) apiResponse.getObject();
        if (!tariff.getClientType().equals(subscriber.getSimCard().getClient().getClientType()))
            return new ApiResponse("This tariff does not apply to you", false);

        if (subscriber.getSimCard().getBalance() < tariff.getConnectPrice())
            return new ApiResponse("You do not have enough money for the tariff", false);

        LocalDate now = LocalDate.now();
        double rate = 1;
        if (tariff.getTariffType() == TariffType.MONTHLY) {
            subscriber.setExpirationDate(LocalDate.of(now.getYear(), now.getMonth().plus(1), 1));
            rate = ((double) Period.between(now, subscriber.getExpirationDate()).getDays()) / now.getMonth().length(now.isLeapYear());
        } else {
            subscriber.setExpirationDate(now.plusDays(1));
        }

        List<ResidueOpportunity> opportunities = subscriber.getOpportunities();
        opportunities.forEach(residueOpportunity -> {
            if (residueOpportunity.isTariff())
                residueOpportunityRepo.delete(residueOpportunity);
        });
        opportunities.clear();
        for (TariffOpportunity tariffOpportunity : tariff.getTariffOpportunities()) {
            if (tariffOpportunity.isAdditional()) {
                ResidueOpportunity opportunity = new ResidueOpportunity();
                opportunity.setTariff(true);
                opportunity.setKey(tariffOpportunity.getKey());
                opportunity.setValue((long) (Long.parseLong(tariffOpportunity.getValue()) * rate));
                opportunity.setSubscriber(subscriber);
                opportunities.add(opportunity);
            }
        }

        subscriber.setActivationDate(now);
        subscriber.setTariff(tariff);
        subscriber.setOpportunities(opportunities);

        ActivityType activityType = activityAndActivityType.createActivityType("CHANGE_TARIFF");

        Map<String, Object> details = new HashMap<>();
        details.put("activityName", activityType.getName());
        details.put("amount", null);
        details.put("other", "Tariff has been changed to " + tariff.getName());

        activityAndActivityType.createActivity(subscriber, activityType, details);

        subscriberRepo.save(subscriber);
        return new ApiResponse("OK", true);
    }

    @Override
    public ApiResponse attachToService(AttachServiceDto dto) {
        Optional<Subscriber> optionalSubscriber = subscriberRepo.findById(dto.getSubscriberId());
        if (!optionalSubscriber.isPresent())
            return new ApiResponse("Subscriber not found", false);
        Optional<ServiceEntity> optionalService = serviceRepo.findByIdAndStatusTrue(dto.getServiceId());
        if (!optionalService.isPresent())
            return new ApiResponse("Service not found", false);

        ServiceEntity service = optionalService.get();
        Subscriber subscriber = optionalSubscriber.get();
        if (subscriber.getSimCard().getBalance() < service.getPrice())
            return new ApiResponse("You don't have enough money for activate service", false);

        simCardService.decreaseBalance(subscriber.getSimCard().getId(), service.getPrice(), "service");
        subscriber.getServices().add(service);

        ActivityType activityType = activityAndActivityType.createActivityType("ACTIVATE_SERVICE");

        Map<String, Object> details = new HashMap<>();
        details.put("activityName", activityType.getName());
        details.put("amount", null);
        details.put("other", "The " + service.getName() + " service is connected");

        activityAndActivityType.createActivity(subscriber, activityType, details);


        subscriberRepo.save(subscriber);
        return new ApiResponse("Attached", true);
    }

    @Override
    public ApiResponse attachToPackage(AttachPackageDto dto) {
        Optional<Subscriber> optionalSubscriber = subscriberRepo.findById(dto.getSubscriberId());
        if (!optionalSubscriber.isPresent())
            return new ApiResponse("Subscriber not found", false);
        Optional<Package> optionalPackage = packageRepo.findByIdAndStatusTrue(dto.getPackageId());
        if (!optionalPackage.isPresent())
            return new ApiResponse("Package not found", false);

        Package aPackage = optionalPackage.get();
        Subscriber subscriber = optionalSubscriber.get();
        if (!aPackage.getTariffs().contains(subscriber.getTariff()))
            return new ApiResponse("Yor tariff doesn't support the package", false);

        if (subscriber.getSimCard().getBalance() < aPackage.getPrice())
            return new ApiResponse("You don't have enough money for activate package", false);

        simCardService.decreaseBalance(subscriber.getSimCard().getId(), aPackage.getPrice(), "package");

        SubscriberPackage subscriberPackage = new SubscriberPackage();
        subscriberPackage.setAPackage(aPackage);
        subscriberPackage.setSubscriber(subscriber);
        subscriberPackage.setActivationDate(LocalDate.now());
        subscriberPackage.setExpirationDate(LocalDate.now().plusDays(aPackage.getValidityDay()));
        subscriber.getSubscriberPackages().add(subscriberPackage);

        for (ResidueOpportunity opportunity : subscriber.getOpportunities()) {
            if (!opportunity.isTariff() && opportunity.getKey().equals(aPackage.getKey())) {
                if (aPackage.isAddResidue()) {
                    opportunity.setValue(opportunity.getValue() + aPackage.getValue());
                } else opportunity.setValue(aPackage.getValue());
            }
        }

        subscriberRepo.save(subscriber);
        return new ApiResponse("Attached", true);
    }
}
