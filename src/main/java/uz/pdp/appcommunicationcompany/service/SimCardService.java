package uz.pdp.appcommunicationcompany.service;

import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;

public interface SimCardService {

    ApiResponse getSimCards();

    ApiResponse getSimCard(Long id);

    ApiResponse deActivate(Long id);

    ApiResponse reportSimCards();

    ApiResponse reportSimCards(String date1, String date2);
}
