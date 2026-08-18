package com.bangaloredairy.order.service;

import com.bangaloredairy.common.dto.SubscriptionRequestDTO;
import com.bangaloredairy.order.model.Subscription;
import com.bangaloredairy.order.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    public Subscription createSubscription(SubscriptionRequestDTO request) {
        Subscription sub = Subscription.builder()
                .userId(request.getUserId() != null ? request.getUserId() : 1L)
                .productId(request.getProductId())
                .productName("Nandini Daily Fresh Milk")
                .quantity(request.getQuantity() != null ? request.getQuantity() : 1)
                .frequency(request.getFrequency() != null ? request.getFrequency() : "DAILY")
                .deliverySlot(request.getDeliverySlot() != null ? request.getDeliverySlot() : "MORNING_5_30_AM")
                .startDate(request.getStartDate() != null ? request.getStartDate() : LocalDate.now().plusDays(1))
                .endDate(request.getEndDate())
                .status("ACTIVE")
                .deliveryAddress(request.getDeliveryAddress() != null ? request.getDeliveryAddress() : "#128, 4th Cross, Indiranagar, Bangalore")
                .pincode(request.getPincode() != null ? request.getPincode() : "560038")
                .specialInstructions(request.getSpecialInstructions())
                .build();

        Subscription saved = subscriptionRepository.save(sub);
        log.info("Dairy daily subscription created: ID={}, ProductID={}, Frequency={}", saved.getId(), saved.getProductId(), saved.getFrequency());
        return saved;
    }

    public List<Subscription> getUserSubscriptions(Long userId) {
        return subscriptionRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Transactional
    public Subscription updateStatus(Long subscriptionId, String status) {
        Subscription sub = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));
        sub.setStatus(status);
        return subscriptionRepository.save(sub);
    }
}
