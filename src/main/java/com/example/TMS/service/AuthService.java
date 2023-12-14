package com.example.TMS.service;

import com.example.TMS.dto.request.AuthRequest;
import com.example.TMS.dto.response.AuthResponse;
import com.example.TMS.entity.User;

public interface AuthService {

    AuthResponse auth(AuthRequest authRequest);

    AuthResponse refresh(User user);
}
