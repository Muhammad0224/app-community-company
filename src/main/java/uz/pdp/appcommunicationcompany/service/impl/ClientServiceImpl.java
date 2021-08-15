package uz.pdp.appcommunicationcompany.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appcommunicationcompany.domain.*;
import uz.pdp.appcommunicationcompany.domain.Number;
import uz.pdp.appcommunicationcompany.enums.ClientType;
import uz.pdp.appcommunicationcompany.enums.TariffType;
import uz.pdp.appcommunicationcompany.enums.UserRole;
import uz.pdp.appcommunicationcompany.helper.CurrentUser;
import uz.pdp.appcommunicationcompany.mapper.MapstructMapper;
import uz.pdp.appcommunicationcompany.model.dto.create.ClientDto;
import uz.pdp.appcommunicationcompany.model.dto.edit.ClientEditDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;
import uz.pdp.appcommunicationcompany.repository.*;
import uz.pdp.appcommunicationcompany.service.ClientService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepo clientRepo;

    private final MapstructMapper mapper;

    private final NumberRepo numberRepo;

    private final CurrentUser currentUser;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepo roleRepo;

    private final TariffRepo tariffRepo;

    private final SubscribeServiceImpl subscribeService;

    private final SimCardRepo simCardRepo;

    private final SimCardServiceImpl simCardService;

    @Override
    public ApiResponse getClients() {
        List<Client> clients = clientRepo.findAll();
        return new ApiResponse("OK", true, mapper.toClientDto(clients));
    }

    @Override
    public ApiResponse getClient(Long id) {
        Optional<Client> optionalClient = clientRepo.findById(id);
        return optionalClient.map(client -> new ApiResponse("Ok", true, mapper.toClientDto(client))).orElseGet(() -> new ApiResponse("Client not found", false));
    }

    @Override
    public ApiResponse createClient(ClientDto dto) {
        try {
            if (clientRepo.existsByLogin(dto.getLogin()))
                return new ApiResponse("Client has already existed", false);
            Optional<Tariff> optionalTariff = tariffRepo.findByIdAndStatusTrue(dto.getTariffId());
            if (!optionalTariff.isPresent())
                return new ApiResponse("Tariff not found", false);
            Tariff tariff = optionalTariff.get();
            if (!tariff.getClientType().name().equals(dto.getClientType()))
                return new ApiResponse("Chosen tariff is not for this client type", false);
            if (tariff.getConnectPrice() > dto.getBalance())
                return new ApiResponse("You do not have enough money for the tariff", false);

            Client client = new Client();

            Number number = new Number();
            Optional<Number> optionalNumber = numberRepo.findByCodeAndNumber(dto.getCode(), dto.getNumber());
            if (optionalNumber.isPresent()) {
                number = optionalNumber.get();
                if (number.isOwned())
                    return new ApiResponse("This number is in use", false);

                number.setOwned(true);
                number = numberRepo.save(number);
            } else {
                number.setNumber(dto.getNumber());
                number.setCode(dto.getCode());
                number = numberRepo.save(number);
            }
            SimCard simCard = new SimCard();
            simCard.setClient(client);
            simCard.setNumber(number);
            simCard.setBalance(dto.getBalance());
            simCard.setBranch(currentUser.getCurrentUser().getBranch());
            simCard.setPrice(dto.getPrice());

            Optional<Client> optionalClient = clientRepo.findByPassportSeriesAndPassportNumber(dto.getPassportSeries(), dto.getPassportNumber());
            if (optionalClient.isPresent()) {
                client = optionalClient.get();
            } else {
                client.setFirstname(dto.getFirstname());
                client.setLastname(dto.getLastname());
                client.setLogin(dto.getLogin());
                client.setClientType(ClientType.valueOf(dto.getClientType()));
                client.setPassword(passwordEncoder.encode(dto.getPassword()));
                client.setPassportSeries(dto.getPassportSeries());
                client.setPassportNumber(dto.getPassportNumber());
                client.setRole(roleRepo.findByName("ROLE_USER").get());
            }
            client.getSimCards().add(simCard);
            Client savedClient = clientRepo.save(client);

            subscribeService.createSubscriber(simCardRepo.findByNumber(simCard.getNumber()), tariff, dto.isServiceDebt());
            return new ApiResponse("Created", true, mapper.toClientDto(savedClient));
        } catch (Exception e) {
            return new ApiResponse("Wrong format client type (Use PHYSICAL_PERSON, LEGAL_PERSON)", false);
        }


    }

    @Override
    public ApiResponse updateClient(Long id, ClientEditDto dto) {
        if (clientRepo.existsByLoginAndIdNot(dto.getLogin(), id))
            return new ApiResponse("This login is in use", false);
        Optional<Client> optionalClient = clientRepo.findById(id);
        if (!optionalClient.isPresent())
            return new ApiResponse("Client not found", false);
        Client client = optionalClient.get();
        client.setFirstname(dto.getFirstname());
        client.setLastname(dto.getLastname());
        client.setLogin(dto.getLogin());
        client.setPassword(passwordEncoder.encode(dto.getPassword()));
        clientRepo.save(client);
        return new ApiResponse("Updated", true);
    }
}
