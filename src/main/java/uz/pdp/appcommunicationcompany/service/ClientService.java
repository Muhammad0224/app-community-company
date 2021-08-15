package uz.pdp.appcommunicationcompany.service;

import uz.pdp.appcommunicationcompany.model.dto.create.ClientDto;
import uz.pdp.appcommunicationcompany.model.dto.edit.ClientEditDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;

public interface ClientService {
    ApiResponse getClients();

    ApiResponse getClient(Long id);

    ApiResponse createClient(ClientDto dto);

    ApiResponse updateClient(Long id, ClientEditDto dto);
}
