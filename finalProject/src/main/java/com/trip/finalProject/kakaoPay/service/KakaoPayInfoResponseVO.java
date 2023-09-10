package com.trip.finalProject.kakaoPay.service;

import com.trip.finalProject.kakaoPay.service.KakaoCancelResponseVO.CancelAvailableAmountVO;
import com.trip.finalProject.kakaoPay.service.KakaoCancelResponseVO.CanceledAmountVO;

import lombok.Data;

@Data
public class KakaoPayInfoResponseVO {
	 private String paymentId;
	 private String postId;
	 private String memberId;
	 private int price;
	 private String orderDate;
	 private String orderStatus;
	 private String orderDetailId;
	 private String specialtyType;
	 private String nowReservation;
	 private String maxReservation;
	 private String tid; // 결제 고유 번호
	 private String cid; // 가맹점 코드
	 private String status; // 결제 상태
	 private String partner_order_id; // 가맹점 주문 번호
	 private String partner_user_id; // 가맹점 회원 ID
	 private String payment_method_type; // 결제 수단
	 private AmountVO amount; // 결제 금액 정보, 결제 요청 구현할때 이미 구현해놓음
	 private CanceledAmountVO canceled_amount; // 누계 취소 금액
	 private CancelAvailableAmountVO cancel_available_amount; // 남은 취소 금액
	 private String item_name; // 상품 이름
	 private String item_code; // 상품 코드
	 private int quantity; // 상품 수량
	 private String created_at; // 결제 준비 요청 시각
	 private String approved_at; // 결제 승인 시각
	 private String canceled_at; // 결제 취소 시각
	 //private SelectedCardInfoVO selected_card_info;
	 private PaymentActionDetailsVO payment_action_details; // 결제/취소 상세
	 //취소 금액
	 private int cancelAmount;
	 //취소 비과세 금액
	 private int cancelTaxFreeAmount;
	 @Data
	 public class PaymentActionDetailsVO{
		 private String aid; //Request 고유 번호
		 private String approved_at; //거래 시간
		 private int amount; //결제/취소총액
		 private int point_amount; //결제 취소 포인트 금액
		 private String payment_action_type; //결제 타입 PAYMENT(결제), CANCEL(결제취소), ISSUED_SID(SID 발급) 중 하나
		 private String payload; 
	 }
}
