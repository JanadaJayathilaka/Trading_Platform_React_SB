package com.example.trading.service;

import com.example.trading.domain.PaymentMethod;
import com.example.trading.domain.PaymentOrderStatus;
import com.example.trading.model.PaymentOrder;
import com.example.trading.model.User;
import com.example.trading.repository.PaymentOrderRepository;
import com.example.trading.response.PaymentResponse;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;




@Service
public class PaymentServiceImpl implements PaymentService{
    @Autowired
    private PaymentOrderRepository paymentOrderRepository;

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecretKey;


    @Override
    public PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setUser(user);
        paymentOrder.setAmount(amount);
        paymentOrder.setPaymentMethod(paymentMethod);
        paymentOrder.setStatus(PaymentOrderStatus.PENDING);
        return paymentOrderRepository.save(paymentOrder);
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {
        return paymentOrderRepository.findById(id).orElseThrow(
                ()->new Exception("Payment order not found"));
    }

    @Override
    public Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException {
        if(paymentOrder.getStatus()==null){
            paymentOrder.setStatus(PaymentOrderStatus.PENDING);
        }
        if(PaymentOrderStatus.PENDING.equals(paymentOrder.getStatus())){
            if(paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY)){
                RazorpayClient razorPay =  new RazorpayClient(apiKey,apiSecretKey);
                Payment payment = razorPay.payments.fetch(paymentId);

                Integer amount = payment.get("amount");
                String status = payment.get("status");

                if(status.equals("captured")){
                    paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                    return true;
                }
                paymentOrder.setStatus(PaymentOrderStatus.FAILED);
                paymentOrderRepository.save(paymentOrder);
                return false;
            }
            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
        paymentOrderRepository.save(paymentOrder);
        return true;
        }
        return false;
    }

//    @Override
//    public Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentIntentId) throws StripeException {
//        if (paymentOrder.getStatus() == null) {
//            paymentOrder.setStatus(PaymentOrderStatus.PENDING);
//        }
//
//        if (paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)) {
//            if (paymentOrder.getPaymentMethod().equals(PaymentMethod.STRIPE)) {
//                Stripe.apiKey = stripeSecretKey;
//
//                // Fetch the payment intent using Stripe's API
//                PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
//
//                String status = paymentIntent.getStatus(); // "succeeded", "requires_payment_method", etc.
//
//                if ("succeeded".equals(status)) {
//                    paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
//                    paymentOrderRepository.save(paymentOrder);
//                    return true;
//                } else {
//                    paymentOrder.setStatus(PaymentOrderStatus.FAILED);
//                    paymentOrderRepository.save(paymentOrder);
//                    return false;
//                }
//            }
//
//            // Fallback if not Stripe, assume success
//            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
//            paymentOrderRepository.save(paymentOrder);
//            return true;
//        }
//
//        return false;
//    }

    @Override
    public PaymentResponse createRazorpayPaymentLink(User user, Long amount,Long orderId) throws RazorpayException {
         Long Amount =amount*100;

         try{
             RazorpayClient razorpay = new RazorpayClient(apiKey,apiSecretKey);

             JSONObject paymentLinkRequest = new JSONObject();
             paymentLinkRequest.put("amount",amount);
             paymentLinkRequest.put("amount","INR");

             JSONObject customer =  new JSONObject();
             customer.put("name",user.getFullName());

             customer.put("name",user.getEmail());
             paymentLinkRequest.put("customer",customer);

             JSONObject notify = new JSONObject();
             notify.put("email", true);
             paymentLinkRequest.put("notify",notify);

             paymentLinkRequest.put("reminder_enable",true);
             PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
             String paymentLinkId = payment.get("id");
             String paymentLinkUrl = payment.get("short_url");

             paymentLinkRequest.put("callback_url","http://localhost:8080/wallet?order_id=" +orderId+paymentLinkId);
             paymentLinkRequest.put("callback_method","get");






             PaymentResponse res = new PaymentResponse();
             res.setPaymentUrl(paymentLinkUrl);

             return res;

         } catch (RazorpayException e) {
             System.out.println("Error creating payment Link: "+e.getMessage());
             throw new RazorpayException(e.getMessage());
         }


    }

    @Override
    public PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException {

        Stripe.apiKey = stripeSecretKey;


        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/wallet?order_id=" +orderId)
                .setCancelUrl("http://localhost:8080/payment/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(amount*100)
                                .setProductData(SessionCreateParams
                                        .LineItem
                                        .PriceData
                                        .ProductData
                                        .builder()
                                        .setName("Top up wallet")
                                        .build()
                                ).build()
                        ).build()
                ).build();

        Session session =Session.create(params);

        System.out.println("session _____"+session);

        PaymentResponse res =  new PaymentResponse();

        res.setPaymentUrl(session.getUrl());

        return res;
    }
}
