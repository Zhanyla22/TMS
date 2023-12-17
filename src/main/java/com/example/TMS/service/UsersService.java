package com.example.TMS.service;

import com.example.TMS.dto.request.ConfirmCodeRequest;
import com.example.TMS.dto.request.RegistrationRequest;

public interface UsersService {

    String registration(RegistrationRequest request);

    String confirm(ConfirmCodeRequest confirmCodeRequest);
}
