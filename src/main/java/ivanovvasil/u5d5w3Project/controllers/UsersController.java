package ivanovvasil.u5d5w3Project.controllers;

import ivanovvasil.u5d5w3Project.entities.User;
import ivanovvasil.u5d5w3Project.exceptions.BadRequestException;
import ivanovvasil.u5d5w3Project.payloadsDTO.ProfileResponseDTO;
import ivanovvasil.u5d5w3Project.payloadsDTO.UserDTO;
import ivanovvasil.u5d5w3Project.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UsersController {
  @Autowired
  private UsersService usersService;

  @GetMapping("")
  @PreAuthorize("hasAuthority('MANAGER')")
  public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "15") int size,
                             @RequestParam(defaultValue = "id") String orderBy) {
    return usersService.findAll(page, size, orderBy);
  }

  @GetMapping("/{id}")
  public User getUser(@PathVariable Long id) {
    return usersService.findById(id);
  }

  @GetMapping("/me")
  public ProfileResponseDTO getProfile(@AuthenticationPrincipal User user) {
    return usersService.getUserProfile(user);
  }


  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('MANAGER')")
  public User editUser(@PathVariable Long id, @RequestBody @Validated UserDTO body, BindingResult validation) {
    if (validation.hasErrors()) {
      throw new BadRequestException("Empty or not respected fields", validation.getAllErrors());
    } else {
      try {
        return usersService.findByIdAndUpdate(id, body);
      } catch (MethodArgumentTypeMismatchException e) {
        throw new BadRequestException("Entered id is invalid");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @PutMapping("/me")
  public User EditProfile(@AuthenticationPrincipal User user, @RequestBody @Validated UserDTO body, BindingResult validation) {
    if (validation.hasErrors()) {
      throw new BadRequestException("Empty or not respected fields", validation.getAllErrors());
    } else {
      try {
        return usersService.findByIdAndUpdate(user.getId(), body);
      } catch (MethodArgumentTypeMismatchException e) {
        throw new BadRequestException("Entered id is invalid");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('MANAGER')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteUser(@PathVariable Long id) throws IOException {
    usersService.findByIdAndDelete(id);
  }

  @DeleteMapping("/me")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteProfile(@AuthenticationPrincipal User user) throws IOException {
    usersService.findByIdAndDelete(user.getId());
  }

  @DeleteMapping("/me/{prenotation_id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletePrenotation(@AuthenticationPrincipal User user, @PathVariable Long prenotation_id) {
    usersService.deletePrenotationById(user, prenotation_id);
  }

}
