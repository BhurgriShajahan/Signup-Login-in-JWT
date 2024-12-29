package jwt.spring.security.service;

import jwt.spring.security.model.CustomResponseEntity;
import jwt.spring.security.model.request.LoginRequest;
import jwt.spring.security.model.request.SignupRequest;

public interface AuthService {
    CustomResponseEntity<?> signup(SignupRequest request);
    CustomResponseEntity<?> login(LoginRequest request);}

