package com.trip.finalProject.tourInfo.service;

import lombok.Data;

import java.util.Date;

@Data
public class SpotDetailReviewVO {
    private int reviewId;        //리뷰번호
    private String writerId;        //작성자아이디
    private String reviewType;      //리뷰게시판타입 : 관광지리뷰 E1
    private String originPostId;    //원글번호 : contentId
    private int grade;              //평점
    private String content;         //내용
    private Date writeDate;         //작성일자

}
