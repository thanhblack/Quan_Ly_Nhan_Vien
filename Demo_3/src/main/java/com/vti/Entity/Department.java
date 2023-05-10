package com.vti.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "`Department`")
@Data
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "`name`", length = 50, nullable = false, unique = true)
    private String name;

    @Column(name = "total_member")
    private int totalMember;

    @Column(name = "`type`", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "created_date", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createDate;

    @OneToMany(mappedBy = "department", cascade = CascadeType.REMOVE)
    private List<Account> accounts;

    @PrePersist
    public void prePersist() {
        if (type == null) {
            type = Type.Dev;
        }
    }
    @PreUpdate
    public void PreUpdate() {
        if (type == null) {
            type = Type.Dev;
        }
    }
}
