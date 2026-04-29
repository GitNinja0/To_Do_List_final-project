package com.juanmunguia.to_do_list_final_project.users;

import com.juanmunguia.to_do_list_final_project.roles.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    Long id;

    private String username;
    private String password;
    private String email;
    private String fullname;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_users", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id_user"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id_role"))
    Set<Role> roles;
}
