package com.trip.finalProject.guideMain.service;

import lombok.Data;

import java.util.Date;

@Data
public class GuideQuestionVO {
    private String questionTitle;
    private String name;
    private String answerResult;
    private Date questionDate;
}
