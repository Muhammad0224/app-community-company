package uz.pdp.appcommunicationcompany.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import uz.pdp.appcommunicationcompany.domain.*;
import uz.pdp.appcommunicationcompany.domain.Package;
import uz.pdp.appcommunicationcompany.model.resp.*;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
@Component
public interface MapstructMapper {

    /**
     * Employee
     */
    @Mapping(source = "branch.name", target = "name")
    EmployeeRespDto toEmployeeDto(Employee employee);

    @Mapping(source = "branch.name", target = "name")
    List<EmployeeRespDto> toEmployeeDto(List<Employee> employees);


    /**
     * Branch
     */
    @Mapping(source = "manager.login", target = "manager")
    BranchRespDto toBranchDto(Branch branch);

    @Mapping(source = "manager.login", target = "manager")
    List<BranchRespDto> toBranchDto(List<Branch> branches);


    /**
     * SimCard
     */
    @Mapping(source = "number.fullNumber", target = "number")
    @Mapping(source = "client.passport", target = "client")
    @Mapping(source = "branch.name", target = "branch")
    SimCardRespDto toSimCardDto(SimCard simCard);

    @Mapping(source = "number.fullNumber", target = "number")
    @Mapping(source = "client.passport", target = "client")
    @Mapping(source = "branch.name", target = "branch")
    List<SimCardRespDto> toSimCardDto(List<SimCard> simCards);

    /**
     * Client
     */
    @Mapping(source = "clientType.code", target = "clientType")
    ClientRespDto toClientDto(Client client);

    @Mapping(source = "clientType.code", target = "clientType")
    List<ClientRespDto> toClientDto(List<Client> clients);

    /**
     * Tariff
     */
    TariffRespDto toTariffDto(Tariff tariff);

    List<TariffRespDto> toTariffDto(List<Tariff> tariffs);

    /**
     * Subscriber
     */
    @Mapping(source = "simCard.number.fullNumber", target = "number")
    @Mapping(source = "tariff.name", target = "tariffName")
    SubscriberRespDto toSubsDto(Subscriber subscriber);

    @Mapping(source = "simCard.number.fullNumber", target = "number")
    @Mapping(source = "tariff.name", target = "tariffName")
    List<SubscriberRespDto> toSubsDto(List<Subscriber> subscribers);


    /**
     * Service
     */
    @Mapping(source = "serviceCategory.name", target = "serviceCategory")
    ServiceRespDto toServiceDto(ServiceEntity service);

    @Mapping(source = "serviceCategory.name", target = "serviceCategory")
    List<ServiceRespDto> toServiceDto(List<ServiceEntity> services);

    /**
     * Payment
     */
    @Mapping(source = "subscriber.simCard.number.fullNumber", target = "subscriberNumber")
    PaymentRespDto toPaymentDto(Payment payment);

    @Mapping(source = "subscriber.simCard.number.fullNumber", target = "subscriberNumber")
    List<PaymentRespDto> toPaymentDto(List<Payment> payments);

    /**
     * Package
     */
    PackageRespDto toPackageDto(Package aPackage);

    List<PackageRespDto> toPackageDto(List<Package> aPackages);

    /**
     * Activity
     */
    @Mapping(source = "activityType.name", target = "name")
    ActivityRespDto toActivityDto(Activity activity);

    @Mapping(source = "activityType.name", target = "name")
    List<ActivityRespDto> toActivityDto(List<Activity> activity);
}
