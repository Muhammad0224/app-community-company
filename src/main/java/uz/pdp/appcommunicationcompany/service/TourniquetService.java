package uz.pdp.appcommunicationcompany.service;

import uz.pdp.appcommunicationcompany.model.dto.create.TourniquetCardDto;
import uz.pdp.appcommunicationcompany.model.dto.create.TourniquetHistoryDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;

public interface TourniquetService {
    ApiResponse create(TourniquetCardDto dto);

    ApiResponse edit(TourniquetCardDto dto, String login);

    ApiResponse activate(TourniquetHistoryDto dto);

    ApiResponse enter(TourniquetHistoryDto dto);

    ApiResponse exit(TourniquetHistoryDto dto);

    ApiResponse delete(String id);
}
