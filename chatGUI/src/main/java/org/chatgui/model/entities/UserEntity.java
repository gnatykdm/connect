package org.chatgui.model.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @NotNull
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    @Column(name = "username")
    @Size(min = 4, message = "Name should be at least 4 characters long")
    private String userName;

    @NotNull
    @Column(name = "password")
    @Size(min = 5, message = "Password should be at least 5 characters long")
    private String userPassword;

    @Email
    @NotNull
    @Column(name = "email")
    private String userEmail;

    @NotNull
    private LocalDate creationDate;

    public UserEntity(String userName, String userPassword, String userEmail, LocalDate creationDate) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.creationDate = creationDate;
    }
}
