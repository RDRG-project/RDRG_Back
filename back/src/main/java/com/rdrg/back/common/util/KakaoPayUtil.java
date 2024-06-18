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
    @Value("${approvalUrl}")
    private String approvalUrl;
    @Value("${cancelUrl}")
    private String cancelUrl;
    @Value("${failUrl}")
    private String failUrl;
    @Value("${kakaoReady}")
    private String kakaoReady;

    RentItem rentItem;
    
    public KakaoReady prepareKakaoPayment(PostPaymentRequestDto dto, int rentNumber) {
        String  orderId = UUID.randomUUID().toString();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", kakaoKey);
            headers.set("Content-Type", "application/json");

            Map<String, String> payParams = new HashMap<String, String>();

            String totalAmount = dto.getRentTotalPrice().toString();
            Integer vatAmount =  dto.getRentTotalPrice() / 11;

            payParams.put("cid", "TC0ONETIME");
            payParams.put("partner_order_id", orderId);
            payParams.put("partner_user_id", "RDRG");
            payParams.put("item_name", "RDRG 예약");
            payParams.put("quantity", "1");
            payParams.put("total_amount", totalAmount);
            payParams.put("vat_amount", vatAmount.toString());
            payParams.put("tax_free_amount", "0");
            payParams.put("approval_url", approvalUrl);
            payParams.put("cancel_url", cancelUrl + rentNumber);
            payParams.put("fail_url", failUrl + rentNumber);

            HttpEntity<Map> request = new HttpEntity<>(payParams, headers);

            RestTemplate template = new RestTemplate();
            String url = kakaoReady;

            return template.postForObject(url, request, KakaoReady.class);
    }
}
