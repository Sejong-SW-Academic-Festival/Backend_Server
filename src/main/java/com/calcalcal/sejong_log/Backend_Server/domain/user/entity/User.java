package com.calcalcal.sejong_log.Backend_Server.domain.user.entity;

import com.calcalcal.sejong_log.Backend_Server.global.entity.BaseTimestampEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User extends BaseTimestampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String department;

    @OneToMany(mappedBy = "user")
    private List<EnrolledSchedule> enrolledSchedules;

    @OneToMany(mappedBy = "user")
    private List<BookedSchedule> bookedSchedules;

    @OneToMany(mappedBy = "user")
    private List<SubscribedCategory> subscribedCategories;

    @Builder
    public User(String name, String email, String password, String department) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.department = department;
    }
}
