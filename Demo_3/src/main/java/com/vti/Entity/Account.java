package com.vti.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "`Account`")
@Setter
@Getter
@NoArgsConstructor

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username" ,length = 50, nullable = false, unique = true, updatable = false)
    private String userName;

    @Column(name = "`password`", length = 800, nullable = false)
    private String password;

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Formula("concat(first_name,' ',last_name)")
    private String fullName;

    @Column(name = "email", nullable = false, unique = true, length = 50, updatable = false)
    private String email;

    @Column(name = "token")
    private String token;

    @Column(name = "token_created")
    private LocalDateTime tokenCreated;

    @Column(name = "`role`", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @PrePersist
    public void prePersist() {
        if (role == null) {
            role = Role.EMPLOYEE;
        }
        password = new BCryptPasswordEncoder().encode(password);
    }

}
