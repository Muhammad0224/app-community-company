package uz.pdp.appcommunicationcompany.service;

import uz.pdp.appcommunicationcompany.model.req.LoginDto;
import uz.pdp.appcommunicationcompany.model.resp.ApiResponse;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    ApiResponse login(LoginDto dto);
}
