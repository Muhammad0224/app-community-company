package uz.pdp.appcommunicationcompany.service;

import uz.pdp.appcommunicationcompany.model.dto.create.PaymentDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;

public interface PaymentService {
    ApiResponse getPayments();

    ApiResponse getPayment(Long id);

    ApiResponse createPayment(PaymentDto dto);
}
