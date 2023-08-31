package com.trip.finalProject.question.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class QuestionVO {

	//question
	//	QUESTION_ID      NOT NULL CHAR(13)     
	//	QUESTION_TYPE    NOT NULL CHAR(2)      
	//	QUESTION_CONTENT NOT NULL LONG         
	//	QUESTION_DATE    NOT NULL DATE         
	//	PRODUCT_ID       NOT NULL VARCHAR2(13) 
	//	MEMBER_ID        NOT NULL VARCHAR2(70) 
	//	ANSWER_RESULT    NOT NULL CHAR(2)
	private String questionId;
	private String questionType;
	private String questionContent;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date questionDate;
	private String productId;
	private String memberId;
	private String answerResult;
	//question_answer
//	ANSWER_ID       NOT NULL CHAR(13)     
//	ANSWER_CONTEXTS NOT NULL LONG         
//	ANSWER_DATE     NOT NULL DATE         
//	MEMBER_ID       NOT NULL VARCHAR2(70) 
//	QUESTION_ID     NOT NULL CHAR(13)
	private String answerId;
	private String answerContent;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date answerDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date answerModifyDate;
	private String answerMemberId;
}
