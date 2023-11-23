package ivanovvasil.u5d5w3Project.controllers;

import ivanovvasil.u5d5w3Project.exceptions.BadRequestException;
import ivanovvasil.u5d5w3Project.payloadsDTO.CreatedUserDTO;
import ivanovvasil.u5d5w3Project.payloadsDTO.UserDTO;
import ivanovvasil.u5d5w3Project.payloadsDTO.UserLoginDTO;
import ivanovvasil.u5d5w3Project.payloadsDTO.UserTokenDTO;
import ivanovvasil.u5d5w3Project.services.UserAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/authentication")
public class UserAuthenticationController {
  @Autowired
  private UserAuthenticationService authenticationService;


  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public CreatedUserDTO saveUser(@RequestBody @Validated @Valid UserDTO body, BindingResult validation) {
    if (validation.hasErrors()) {
      throw new BadRequestException("Empty or not respected fields", validation.getAllErrors());
    } else {
      try {
        return authenticationService.registerUser(body);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @PostMapping("/login")
  public UserTokenDTO login(@RequestBody @Validated UserLoginDTO body, BindingResult validation) {
    if (validation.hasErrors()) {
      throw new BadRequestException("Empty or not respected fields", validation.getAllErrors());
    } else {
      return new UserTokenDTO(authenticationService.authenticateUser(body));
    }
  }
}
