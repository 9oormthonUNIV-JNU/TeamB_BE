package com.example.gifty.dto.support;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

public class SupportResponseDTO {
    @Getter
    @Setter
    @ToString
    public static class KakaoPayReadyDTO {
        private String tid;
        private String next_redirect_mobile_url;
        private String next_redirect_pc_url;
        private Date created_at;
    }

    @Getter
    @Setter
    @ToString
    public static class KakaoPayApprovementDTO {
        private String aid;
        private String tid;
        private String cid;
        private String sid;
        private String partner_order_id;
        private String partner_user_id;
        private String payment_method_type;
        private Amount amount;
        private CardInfo card_info;
        private String item_name;
        private String item_code;
        private Integer quantity;
        private Date created_at;
        private Date approved_at;
        private String payload;

        @Getter
        @Setter
        @ToString
        public static class Amount {
            private Integer total;
            private Integer tax_free;
            private Integer vat;
            private Integer point;
            private Integer discount;
            private Integer green_deposit;
        }

        @Getter
        @Setter
        @ToString
        public static class CardInfo {
            private String kakaopay_purchase_corp;
            private String kakaopay_purchase_corp_code;
            private String kakaopay_issuer_corp;
            private String kakaopay_issuer_corp_code;
            private String bin;
            private String card_type;
            private String install_month;
            private String approved_id;
            private String card_mid;
            private String interest_free_install;
            private String installment_type;
            private String card_item_code;
        }
    }
}
