package uz.pdp.appcommunicationcompany.service.impl;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.appcommunicationcompany.domain.Activity;
import uz.pdp.appcommunicationcompany.domain.ActivityType;
import uz.pdp.appcommunicationcompany.domain.Subscriber;
import uz.pdp.appcommunicationcompany.repository.ActivityRepo;
import uz.pdp.appcommunicationcompany.repository.ActivityTypeRepo;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ActivityAndActivityType {
    private final ActivityRepo activityRepo;

    private final ActivityTypeRepo activityTypeRepo;

    public void createActivity(Subscriber subscriber, ActivityType activityType, Map<String, Object> details) {
        Activity activity = new Activity();
        activity.setActivityType(activityType);
        activity.setDetails(new Gson().toJson(details));
        activity.setSubscriber(subscriber);
        activityRepo.save(activity);
    }

    public ActivityType createActivityType(String name) {
        ActivityType activityType;
        Optional<ActivityType> optionalActivityType = activityTypeRepo.findByName(name);
        if (optionalActivityType.isPresent()) {
            return optionalActivityType.get();
        } else {
            activityType = new ActivityType();
            activityType.setName(name);
            return activityTypeRepo.save(activityType);
        }
    }
}
