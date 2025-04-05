package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.SubscriptionPlan;
import com.dancestudio.erp.entry.SubscriptionPlanEntry;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.enums.SubscriptionStatus;
import com.dancestudio.erp.enums.SubscriptionType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.StudioManager;
import com.dancestudio.erp.manager.SubscriptionPlanManager;
import com.dancestudio.erp.repository.SubscriptionPlanRepository;
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
import java.util.Objects;

@Slf4j
@Service
public class SubscriptionPlanManagerImpl implements SubscriptionPlanManager {
    private final SubscriptionPlanRepository subscriptionPlanRepository;

    @Value("${razorpay.api_secret}")
    private String razorpaySecret;

    @Autowired
    private RazorpayClient razorpayClient;

    @Autowired
    private StudioManager studioManager;

    @Autowired
    public SubscriptionPlanManagerImpl(SubscriptionPlanRepository subscriptionPlanRepository) {
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    @Override
    public SubscriptionPlanEntry addSubscriptionPlan(SubscriptionPlanEntry subscriptionPlanEntry) throws EntityNotFoundException {
        SubscriptionPlan subscriptionPlan = convertToEntity(subscriptionPlanEntry, null);
        return convertToEntry(subscriptionPlanRepository.save(subscriptionPlan));
    }

    @Override
    public SubscriptionPlanEntry updateSubscriptionPlan(Long subscriptionPlanId, SubscriptionPlanEntry subscriptionPlanEntry) throws EntityNotFoundException {
        SubscriptionPlan existingSubscriptionPlan = subscriptionPlanRepository.findById(subscriptionPlanId)
                .orElseThrow(() -> new EntityNotFoundException("SubscriptionPlan not found"));

        SubscriptionPlan updatedSubscriptionPlan = convertToEntity(subscriptionPlanEntry, existingSubscriptionPlan);
        return convertToEntry(subscriptionPlanRepository.save(updatedSubscriptionPlan));
    }

    @Override
    public void deleteSubscriptionPlan(Long subscriptionPlanId) throws EntityNotFoundException {
        subscriptionPlanRepository.findById(subscriptionPlanId)
                .orElseThrow(() -> new EntityNotFoundException("SubscriptionPlan not found"));

        subscriptionPlanRepository.deleteById(subscriptionPlanId);
    }

    @Override
    public SubscriptionPlanEntry getSubscriptionPlanByStudioId(Long studioId) throws EntityNotFoundException {
        SubscriptionPlan subscriptionPlan = subscriptionPlanRepository.findLatestSubscriptionByStudioId(studioId)
                .orElse(null);
        if (Objects.isNull(subscriptionPlan)) {
            return null;
        }
        return convertToEntry(subscriptionPlan);
    }

    private SubscriptionPlanEntry convertToEntry(SubscriptionPlan subscriptionPlan) throws EntityNotFoundException {

        SubscriptionPlanEntry subscriptionPlanEntry = new SubscriptionPlanEntry();
        subscriptionPlanEntry.setPlanId(subscriptionPlan.getId());

        StudioEntry entry = studioManager.getStudioById(subscriptionPlan.getStudio().getId());
        subscriptionPlanEntry.setStudioId(entry.getStudioId());

        subscriptionPlanEntry.setSubscriptionPlan(SubscriptionType.valueOf(subscriptionPlan.getSubscriptionPlan()));
        subscriptionPlanEntry.setStartDate(subscriptionPlan.getStartDate());
        subscriptionPlanEntry.setEndDate(subscriptionPlan.getEndDate());
        subscriptionPlanEntry.setStatus(SubscriptionStatus.valueOf(subscriptionPlan.getStatus()));
        subscriptionPlanEntry.setPrice(subscriptionPlan.getPrice().doubleValue());
        subscriptionPlanEntry.setRenewalDate(subscriptionPlan.getRenewalDate());
        subscriptionPlanEntry.setOrderId(subscriptionPlan.getOrderId());
        subscriptionPlanEntry.setPaymentId(subscriptionPlan.getPaymentId());

        return subscriptionPlanEntry;
    }

    @Override
    public SubscriptionPlanEntry createOrder(SubscriptionPlanEntry subscriptionPlanEntry) {
        try {
            subscriptionPlanEntry.setStatus(SubscriptionStatus.valueOf("CREATED"));
            JSONObject options = new JSONObject();
            options.put("amount", subscriptionPlanEntry.getPrice() * 100);
            options.put("currency", "INR");
            options.put("receipt", "receipt#1");

            Order order = razorpayClient.Orders.create(options);
            subscriptionPlanEntry.setOrderId(order.get("id"));
            subscriptionPlanEntry.setMessage(order.toString());
            addSubscriptionPlan(subscriptionPlanEntry);
            return subscriptionPlanEntry;
        } catch (Exception e) {
            log.error("Error creating order", e);
            throw new RuntimeException("Order not created");
        }
    }

    @Override
    public SubscriptionPlanEntry verifyPayment(String orderId, String paymentId, String signature) throws Exception {
        try {
            SubscriptionPlanEntry entry = convertToEntry(subscriptionPlanRepository.findByOrderId(orderId)
                    .orElseThrow(() -> new EntityNotFoundException("SubscriptionPlan not found")));
            boolean isVerified = verifySignature(orderId, paymentId, signature);
            log.info("Order ID: {}, Payment ID: {}, Signature: {}", orderId, paymentId, signature);

            if (isVerified) {
                entry.setPaymentId(paymentId);
                entry.setStatus(SubscriptionStatus.ACTIVE);
                updateSubscriptionPlan(entry.getPlanId(), entry);
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

    private SubscriptionPlan convertToEntity(SubscriptionPlanEntry subscriptionPlanEntry,
            SubscriptionPlan existingSubscriptionPlan) throws EntityNotFoundException {
        SubscriptionPlan subscriptionPlan = (existingSubscriptionPlan != null) ? existingSubscriptionPlan
                : new SubscriptionPlan();

        if (Objects.nonNull(subscriptionPlanEntry.getPlanId())) {
            subscriptionPlan.setId(subscriptionPlanEntry.getPlanId());
        }
        if (Objects.nonNull(subscriptionPlanEntry.getStudioId())) {
            StudioEntry entry = studioManager.getStudioById(subscriptionPlanEntry.getStudioId());
            subscriptionPlan.setStudio(ConvertToEntryUtil.convertToEntity(entry, null));
        }
        if (Objects.nonNull(subscriptionPlanEntry.getSubscriptionPlan())) {
            subscriptionPlan.setSubscriptionPlan(String.valueOf(subscriptionPlanEntry.getSubscriptionPlan()));
        }
        if (Objects.nonNull(subscriptionPlanEntry.getStatus())) {
            subscriptionPlan.setStatus(String.valueOf(subscriptionPlanEntry.getStatus()));
        }
        if (Objects.nonNull(subscriptionPlanEntry.getOrderId())) {
            subscriptionPlan.setOrderId(subscriptionPlanEntry.getOrderId());
        }
        if (Objects.nonNull(subscriptionPlanEntry.getPaymentId())) {
            subscriptionPlan.setPaymentId(subscriptionPlanEntry.getPaymentId());
        }
        if (Objects.nonNull(subscriptionPlanEntry.getPrice())) {
            subscriptionPlan.setPrice(BigDecimal.valueOf(subscriptionPlanEntry.getPrice()));
        }
        if (Objects.nonNull(subscriptionPlanEntry.getRenewalDate())) {
            subscriptionPlan.setRenewalDate(subscriptionPlanEntry.getRenewalDate());
        }
        SubscriptionUtils.setSubscriptionDates(subscriptionPlan, subscriptionPlanEntry.getSubscriptionPlan());
        return subscriptionPlan;
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
