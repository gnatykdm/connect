package org.chatgui.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@Table(name = "usercontext")
public class UserEntity {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private String userId;

    @NotNull
    @Column(name = "name", unique = true)
    private String userName;

    @Column(name = "lastname")
    private String userLastName;

    @NotNull
    @Column(name = "birthdate")
    private String userBirthDate;

    @Email
    @NotNull
    @Column(name = "email", unique = true)
    private String userEmail;

    @NotNull
    @Column(name = "password")
    private String userPassword;

    public UserEntity(String userName, String userBirthDate, String userEmail, String userPassword) {
        this.userName = userName;
        this.userBirthDate = userBirthDate;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }
}
