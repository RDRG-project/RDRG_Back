package com.rdrg.back.common.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KakaoReady {
    private String tid; //결제고유번호
    private String next_redirect_mobile_url; //요청한 클라이언트가 모바일 웹
    private String next_redirect_pc_url; //요청한 클라이언트가 PC 웹
    private String partner_order_id; //가맹점 주문번호
}
