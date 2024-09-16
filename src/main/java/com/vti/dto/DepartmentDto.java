package com.vti.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DepartmentDto extends RepresentationModel<DepartmentDto> {
    private Integer id;
    private String name;
    private Integer totalMembers;
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    private List<AccountDto> accounts;

    @Getter
    @Setter
    public static class AccountDto extends RepresentationModel<AccountDto>  {
        @JsonProperty("accountId")
        private Integer id;
        private String username;
    }






}
