package com.juanmunguia.to_do_list_final_project.users;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class UserDTO {
    private String username;
    private String password;
    private String email;
}
