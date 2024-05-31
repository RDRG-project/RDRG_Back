package com.rdrg.back.dto.response.payment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rdrg.back.common.object.KakaoReady;
import com.rdrg.back.dto.response.ResponseCode;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.ResponseMessage;

import lombok.Getter;

@Getter
public class PostPaymentResponseDto extends ResponseDto{
    
    private String tid;
    private String nextRedirectMobileUrl;
    private String nextRedirectPcUrl;
    private String partnerOrderId;

    private PostPaymentResponseDto(KakaoReady kakaopay) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.tid = kakaopay.getTid();
        this.nextRedirectMobileUrl = kakaopay.getNext_redirect_mobile_url();
        this.nextRedirectPcUrl = kakaopay.getNext_redirect_pc_url();
        this.partnerOrderId = kakaopay.getPartner_order_id();
    }

    public static ResponseEntity<PostPaymentResponseDto> success(KakaoReady kakaopay) {
        PostPaymentResponseDto body = new PostPaymentResponseDto(kakaopay);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

}
