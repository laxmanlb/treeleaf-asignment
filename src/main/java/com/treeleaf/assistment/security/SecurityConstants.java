/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.treeleaf.assistment.security;

/**
 *
 * @author Sudeep
 */
public class SecurityConstants {

  public static final String SECRET = "SECRET_KEY";
  public static final long EXPIRATION_TIME = 30000_000; // 15 mins
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  public static final String SIGN_UP_URL = "/treeleaf/";
  
}
