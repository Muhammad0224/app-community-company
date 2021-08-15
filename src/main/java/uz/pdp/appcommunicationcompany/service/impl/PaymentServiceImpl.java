package uz.pdp.appcommunicationcompany.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.appcommunicationcompany.domain.Activity;
import uz.pdp.appcommunicationcompany.domain.ActivityType;
import uz.pdp.appcommunicationcompany.domain.Payment;
import uz.pdp.appcommunicationcompany.domain.Subscriber;
import uz.pdp.appcommunicationcompany.enums.PaymentType;
import uz.pdp.appcommunicationcompany.mapper.MapstructMapper;
import uz.pdp.appcommunicationcompany.model.dto.create.PaymentDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;
import uz.pdp.appcommunicationcompany.repository.PaymentRepo;
import uz.pdp.appcommunicationcompany.service.PaymentService;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepo paymentRepo;

    private final MapstructMapper mapper;

    private final SimCardServiceImpl simCardService;

    private final ActivityAndActivityType activityAndActivityType;

    @Override
    public ApiResponse getPayments() {
        return new ApiResponse("OK", true, mapper.toPaymentDto(paymentRepo.findAll()));
    }

    @Override
    public ApiResponse getPayment(Long id) {
        Optional<Payment> optionalPayment = paymentRepo.findById(id);
        return optionalPayment.map(payment -> new ApiResponse("OK", true, mapper.toPaymentDto(payment))).orElseGet(() -> new ApiResponse("Payment not found", false));
    }

    @Override
    public ApiResponse createPayment(PaymentDto dto) {
        try {
            ApiResponse apiResponse = simCardService.getSubscriberFromNumber(dto.getNumber());
            if (!apiResponse.isStatus())
                return new ApiResponse("Number not found", false);
            Subscriber subscriber = (Subscriber) apiResponse.getObject();
            Payment payment = new Payment();
            payment.setPaymentType(PaymentType.valueOf(dto.getPaymentType()));
            payment.setPayer(dto.getPayer());
            payment.setSubscriber(subscriber);
            payment.setAmount(dto.getAmount());

            ActivityType activityType = activityAndActivityType.createActivityType("PAYMENT");

            Map<String, Object> details = new HashMap<>();
            details.put("activityName", activityType.getName());
            details.put("amount", dto.getAmount());
            details.put("other", "Money was transferred to " + subscriber.getSimCard().getNumber().getFullNumber());

            activityAndActivityType.createActivity(subscriber, activityType, details);

            paymentRepo.save(payment);
            return new ApiResponse("Saved", true);
        } catch (Exception e) {
            return new ApiResponse("Payment type is given incorrect", false);
        }
    }
}
