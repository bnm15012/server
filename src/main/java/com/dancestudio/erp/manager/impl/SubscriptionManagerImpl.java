package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Subscription;
import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.entry.PlanEntry;
import com.dancestudio.erp.entry.SubscriptionEntry;
import com.dancestudio.erp.enums.SubscriptionStatus;
import com.dancestudio.erp.enums.SubscriptionType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.BranchManager;
import com.dancestudio.erp.manager.PlanManager;
import com.dancestudio.erp.manager.SubscriptionManager;
import com.dancestudio.erp.repository.SubscriptionRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import com.dancestudio.erp.util.SubscriptionUtils;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class SubscriptionManagerImpl implements SubscriptionManager {
    private final SubscriptionRepository subscriptionRepository;

    @Value("${razorpay.api_secret}")
    private String razorpaySecret;

    @Autowired
    private RazorpayClient razorpayClient;

    @Autowired
    private BranchManager branchManager;

    @Autowired
    private PlanManager planManager;

    @Autowired
    public SubscriptionManagerImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public SubscriptionEntry add(SubscriptionEntry subscriptionEntry) throws Exception {
        Subscription subscription = convertToEntity(subscriptionEntry, null);
        return convertToEntry(subscriptionRepository.save(subscription));
    }

    @Override
    public SubscriptionEntry update(Long subscriptionId, SubscriptionEntry subscriptionEntry) throws Exception {
        Subscription existingSubscriptionPlan = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new EntityNotFoundException("SubscriptionPlan not found"));

        Subscription updatedSubscriptionPlan = convertToEntity(subscriptionEntry, existingSubscriptionPlan);
        return convertToEntry(subscriptionRepository.save(updatedSubscriptionPlan));
    }

    @Override
    public void delete(Long subscriptionId) throws EntityNotFoundException {
        subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new EntityNotFoundException("SubscriptionPlan not found"));

        subscriptionRepository.deleteById(subscriptionId);
    }

    @Override
    public SubscriptionEntry getById(Long subscriptionId) throws Exception {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new EntityNotFoundException("SubscriptionPlan not found"));
        return convertToEntry(subscription);
    }

    @Override
    public SubscriptionEntry getSubscriptionPlanByBranchId(Long branchId) throws Exception {
        Subscription subscription = subscriptionRepository.findLatestSubscriptionByBranchId(branchId)
                .orElse(null);
        if (Objects.isNull(subscription)) {
            return null;
        }
        return convertToEntry(subscription);
    }

    private SubscriptionEntry convertToEntry(Subscription subscription) throws Exception {

        SubscriptionEntry subscriptionEntry = new SubscriptionEntry();
        subscriptionEntry.setPlanId(subscription.getId());

        BranchEntry entry = branchManager.getById(subscription.getBranch().getId());
        subscriptionEntry.setBranchId(entry.getBranchId());

        subscriptionEntry.setSubscriptionPlan(SubscriptionType.valueOf(subscription.getSubscriptionPlan()));
        subscriptionEntry.setStartDate(subscription.getStartDate());
        subscriptionEntry.setEndDate(subscription.getEndDate());
        subscriptionEntry.setStatus(SubscriptionStatus.valueOf(subscription.getStatus()));
        subscriptionEntry.setPrice(subscription.getPrice().doubleValue());
        subscriptionEntry.setRenewalDate(subscription.getRenewalDate());
        subscriptionEntry.setOrderId(subscription.getOrderId());
        subscriptionEntry.setPaymentId(subscription.getPaymentId());

        return subscriptionEntry;
    }

    @Override
    public SubscriptionEntry createOrder(SubscriptionEntry subscriptionEntry, String countryCode) {
        try {
            PlanEntry planEntry = planManager.getPlansByMembershipTypeAndCountryCode(subscriptionEntry.getSubscriptionPlan().name(), countryCode);

            subscriptionEntry.setStatus(SubscriptionStatus.CREATED);
            JSONObject options = new JSONObject();
            options.put("amount", planEntry.getAmount() * 100);
            options.put("currency", "INR");
            options.put("receipt", "receipt#1");

            Order order = razorpayClient.Orders.create(options);
            subscriptionEntry.setOrderId(order.get("id"));
            subscriptionEntry.setMessage(order.toString());
            subscriptionEntry.setPrice(planEntry.getAmount());

            // Fetch the latest ACTIVE subscription for the given studio
            Optional<Subscription> activeSubscription = subscriptionRepository.findLatestSubscriptionByBranchId(subscriptionEntry.getStudioId());

            if (activeSubscription.isPresent()) {
                subscriptionEntry.setStartDate(activeSubscription.get().getEndDate());
                subscriptionEntry.setEndDate(SubscriptionUtils.calculateEndDate(subscriptionEntry.getStartDate(), subscriptionEntry.getSubscriptionPlan()));
            } else {
                subscriptionEntry.setStartDate(new Date());
                subscriptionEntry.setEndDate(SubscriptionUtils.calculateEndDate(subscriptionEntry.getStartDate(), subscriptionEntry.getSubscriptionPlan()));
            }

            add(subscriptionEntry);
            return subscriptionEntry;
        } catch (Exception e) {
            log.error("Error creating order", e);
            throw new RuntimeException("Order not created");
        }
    }

    @Override
    public SubscriptionEntry verifyPayment(String orderId, String paymentId, String signature) throws Exception {
        try {
            SubscriptionEntry entry = convertToEntry(subscriptionRepository.findByOrderId(orderId)
                    .orElseThrow(() -> new EntityNotFoundException("SubscriptionPlan not found")));
            boolean isVerified = verifySignature(orderId, paymentId, signature);
            log.info("Order ID: {}, Payment ID: {}, Signature: {}", orderId, paymentId, signature);

            if (isVerified) {
                entry.setPaymentId(paymentId);
                entry.setStatus(SubscriptionStatus.ACTIVE);
                update(entry.getPlanId(), entry);
                entry.setMessage("Payment verified successfully!");
                return entry;
            } else {
                entry.setMessage("Payment verification failed: Invalid signature");
                return entry;
            }
        } catch (Exception e) {
            log.error("Payment verification failed: {}", e.getMessage(), e);
            throw new Exception("Payment verification failed: " + e.getMessage(), e);
        }
    }

    private Subscription convertToEntity(SubscriptionEntry subscriptionEntry, Subscription existingSubscriptionPlan) throws Exception {
        Subscription subscription = (existingSubscriptionPlan != null) ? existingSubscriptionPlan : new Subscription();

        if (Objects.nonNull(subscriptionEntry.getPlanId())) {
            subscription.setId(subscriptionEntry.getPlanId());
        }
        if (Objects.nonNull(subscriptionEntry.getBranchId())) {
            BranchEntry entry = branchManager.getById(subscriptionEntry.getBranchId());
            subscription.setBranch(ConvertToEntryUtil.convertToEntity(entry, null));
        }
        if (Objects.nonNull(subscriptionEntry.getSubscriptionPlan())) {
            subscription.setSubscriptionPlan(String.valueOf(subscriptionEntry.getSubscriptionPlan()));
        }
        if (Objects.nonNull(subscriptionEntry.getStatus())) {
            subscription.setStatus(String.valueOf(subscriptionEntry.getStatus()));
        }
        if (Objects.nonNull(subscriptionEntry.getOrderId())) {
            subscription.setOrderId(subscriptionEntry.getOrderId());
        }
        if (Objects.nonNull(subscriptionEntry.getPaymentId())) {
            subscription.setPaymentId(subscriptionEntry.getPaymentId());
        }
        if (Objects.nonNull(subscriptionEntry.getPrice())) {
            subscription.setPrice(BigDecimal.valueOf(subscriptionEntry.getPrice()));
        }
        if (Objects.nonNull(subscriptionEntry.getRenewalDate())) {
            subscription.setRenewalDate(subscriptionEntry.getRenewalDate());
        }
        SubscriptionUtils.setSubscriptionDates(subscription, subscriptionEntry, subscriptionEntry.getSubscriptionPlan());
        return subscription;
    }

    private boolean verifySignature(String orderId, String paymentId, String providedSignature) {
        try {
            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", orderId);
            options.put("razorpay_payment_id", paymentId);
            options.put("razorpay_signature", providedSignature);

            return com.razorpay.Utils.verifyPaymentSignature(options, razorpaySecret);
        } catch (Exception e) {
            log.error("Error verifying signature: ", e);
            return false;
        }
    }
}
