package com.trip.finalProject.guideMain.service;

import lombok.Data;

import java.util.Date;

@Data
public class CalculationVO {
    private int calAmount;
    private String calStatus;
    private Date calDate;
    private String name;
}
