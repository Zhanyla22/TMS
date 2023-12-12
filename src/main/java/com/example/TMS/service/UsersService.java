package com.example.TMS.service;

import com.example.TMS.dto.request.AuthRequest;
import com.example.TMS.dto.response.AuthResponse;

public interface UsersService {

    AuthResponse auth(AuthRequest authRequest);
}
