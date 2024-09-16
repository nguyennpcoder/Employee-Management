package com.vti.form;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AccountCreateForm {
    @NotBlank(message = "username can not be null")
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String role; // 500x => all, 400x
    private Integer departmentId;

}
