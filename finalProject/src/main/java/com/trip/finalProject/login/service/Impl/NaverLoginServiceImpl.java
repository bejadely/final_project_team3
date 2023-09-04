package com.trip.finalProject.login.service.Impl;


import com.github.scribejava.core.builder.api.DefaultApi20;
 

public class NaverLoginServiceImpl extends DefaultApi20{
 
    protected NaverLoginServiceImpl(){
    }
 
    private static class InstanceHolder{
        private static final NaverLoginServiceImpl INSTANCE = new NaverLoginServiceImpl();
    }
 
 
    public static NaverLoginServiceImpl instance(){
        return InstanceHolder.INSTANCE;
    }
 
    @Override
    public String getAccessTokenEndpoint() {
        return "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code";
    }                   
 
    @Override
    protected String getAuthorizationBaseUrl() {
        return "https://nid.naver.com/oauth2.0/authorize";
    }   
}