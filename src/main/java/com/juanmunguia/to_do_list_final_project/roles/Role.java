package com.juanmunguia.to_do_list_final_project.roles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.juanmunguia.to_do_list_final_project.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "rol")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    Long id;

    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    Set<User> users;
}
