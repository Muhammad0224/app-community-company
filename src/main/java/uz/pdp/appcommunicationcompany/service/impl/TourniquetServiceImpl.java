package uz.pdp.appcommunicationcompany.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import uz.pdp.appcommunicationcompany.domain.Employee;
import uz.pdp.appcommunicationcompany.domain.TourniquetCard;
import uz.pdp.appcommunicationcompany.domain.TourniquetHistory;
import uz.pdp.appcommunicationcompany.model.dto.create.TourniquetCardDto;
import uz.pdp.appcommunicationcompany.model.dto.create.TourniquetHistoryDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;
import uz.pdp.appcommunicationcompany.repository.EmployeeRepo;
import uz.pdp.appcommunicationcompany.repository.TourniquetHistoryRepo;
import uz.pdp.appcommunicationcompany.repository.TourniquetRepo;
import uz.pdp.appcommunicationcompany.service.TourniquetService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static uz.pdp.appcommunicationcompany.helper.Check.access;

@Service
@RequiredArgsConstructor
public class TourniquetServiceImpl implements TourniquetService {
    private final EmployeeRepo employeeRepo;
    private final TourniquetRepo tourniquetRepo;
    private final TourniquetHistoryRepo tourniquetHistoryRepo;

    public ApiResponse create(TourniquetCardDto dto) {
        Optional<Employee> optionalEmployee = employeeRepo.findByLoginAndStatusTrue(dto.getEmployeeLogin());
        if (!optionalEmployee.isPresent())
            return new ApiResponse("Employee not found", false);

        Employee employee = optionalEmployee.get();
        if (!access(getCurrentUser(), employee))
            return new ApiResponse("You don't have access", false);

        TourniquetCard card = new TourniquetCard();
        card.setBranch(employee.getBranch());
        card.setEmployee(employee);
        tourniquetRepo.save(card);
        return new ApiResponse("Tourniquet card is successfully created", true);
    }

    public ApiResponse edit(TourniquetCardDto dto, String login) {
        Optional<Employee> optionalEmployee = employeeRepo.findByLoginAndStatusTrue(dto.getEmployeeLogin());
        if (!optionalEmployee.isPresent())
            return new ApiResponse("Employee not found", false);

        Optional<TourniquetCard> optionalTourniquetCard = tourniquetRepo.findByEmployee_LoginAndStatusTrue(login);
        Employee employee = optionalEmployee.get();
        if (!access(getCurrentUser(), employee))
            return new ApiResponse("You don't have access", false);
        if (optionalTourniquetCard.isPresent()) {
            TourniquetCard card = optionalTourniquetCard.get();
            card.setEmployee(employee);
            tourniquetRepo.save(card);
            return new ApiResponse("Card updated", true);
        }
        return new ApiResponse("Card not found", false);
    }

    public ApiResponse enter(TourniquetHistoryDto dto) {
        Optional<TourniquetCard> cardOptional = tourniquetRepo.findById(UUID.fromString(dto.getCardId()));
        if (!cardOptional.isPresent())
            return new ApiResponse("Card not found", false);
        TourniquetCard card = cardOptional.get();
        if (card.isStatus()) {
            TourniquetHistory tourniquetHistory = new TourniquetHistory();
            tourniquetHistory.setEnteredAt(LocalDateTime.now());
            tourniquetHistory.setTourniquetCard(card);
            tourniquetHistoryRepo.save(tourniquetHistory);
            return new ApiResponse("Entered", true);
        }
        return new ApiResponse("Expiration date of the card", false);
    }

    public ApiResponse exit(TourniquetHistoryDto dto) {
        Optional<TourniquetCard> cardOptional = tourniquetRepo.findById(UUID.fromString(dto.getCardId()));
        if (!cardOptional.isPresent())
            return new ApiResponse("Card not found", false);
        TourniquetCard card = cardOptional.get();
        if (card.isStatus()) {
            TourniquetHistory tourniquetHistory = new TourniquetHistory();
            tourniquetHistory.setExitedAt(LocalDateTime.now());
            tourniquetHistory.setTourniquetCard(card);
            tourniquetHistoryRepo.save(tourniquetHistory);
            return new ApiResponse("Exited", true);
        }
        return new ApiResponse("Expiration date of the card", false);
    }

    public ApiResponse activate(TourniquetHistoryDto dto) {
        Optional<TourniquetCard> optionalTourniquetCard =
                tourniquetRepo.findById(UUID.fromString(dto.getCardId()));
        if (optionalTourniquetCard.isPresent()) {
            TourniquetCard card = optionalTourniquetCard.get();
            card.setStatus(true);
            tourniquetRepo.save(card);
            return new ApiResponse("Card activated", true);
        }
        return new ApiResponse("Card not found", true);
    }

    public ApiResponse delete(String id) {
        Optional<TourniquetCard> optionalTourniquetCard =
                tourniquetRepo.findById(UUID.fromString(id));
        if (!optionalTourniquetCard.isPresent())
            return new ApiResponse("Card not found", true);
        TourniquetCard card = optionalTourniquetCard.get();
        card.setStatus(false);
        tourniquetRepo.save(card);
        return new ApiResponse("Card deleted", true);
    }

    public Employee getCurrentUser() {
        return employeeRepo.findByLoginAndStatusTrue(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).get();
    }
}
