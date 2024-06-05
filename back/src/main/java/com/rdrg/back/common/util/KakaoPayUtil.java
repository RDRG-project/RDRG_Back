package com.rdrg.back.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.rdrg.back.common.object.KakaoReady;
import com.rdrg.back.common.object.RentItem;
import com.rdrg.back.dto.request.payment.PostPaymentRequestDto;

@Component
public class KakaoPayUtil {
    
    @Value("${kakaoSecretKey}")
    private String kakaoKey;

    RentItem rentItem;
    
    public KakaoReady prepareKakaoPayment(PostPaymentRequestDto dto, int rentNumber) {
        String  orderId = UUID.randomUUID().toString();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", kakaoKey);
            headers.set("Content-Type", "application/json");

            Map<String, String> payParams = new HashMap<String, String>();

            String totalAmount = dto.getRentTotalPrice().toString();
            Integer vatAmount =  dto.getRentTotalPrice() / 11;

            payParams.put("cid", "TC0ONETIME"); // 가맹점 코드
            payParams.put("partner_order_id", orderId); // 가맹점 주문번호
            payParams.put("partner_user_id", "RDRG"); // 가맹점 회원 아이디
            payParams.put("item_name", "RDRG 예약"); // 상품명
            payParams.put("quantity", "1"); // 수량
            payParams.put("total_amount", totalAmount);
            payParams.put("vat_amount", vatAmount.toString());
            payParams.put("tax_free_amount", "0");
            payParams.put("approval_url", "http://localhost:3000/rdrg/pay/success");
            payParams.put("cancel_url", "http://localhost:3000/rdrg/pay/cancel/" + rentNumber);
            payParams.put("fail_url", "http://localhost:3000/rdrg/pay/fail/" + rentNumber);

            HttpEntity<Map> request = new HttpEntity<>(payParams, headers);

            RestTemplate template = new RestTemplate();
            String url = "https://open-api.kakaopay.com/online/v1/payment/ready";

            return template.postForObject(url, request, KakaoReady.class);
    }
}
