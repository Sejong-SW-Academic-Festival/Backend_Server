package com.calcalcal.sejong_log.Backend_Server.domain.schedule.entity;

import com.calcalcal.sejong_log.Backend_Server.domain.category.entity.Category;
import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.BookedSchedule;
import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.EnrolledSchedule;
import com.calcalcal.sejong_log.Backend_Server.global.entity.BaseTimestampEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "schedule")
public class Schedule extends BaseTimestampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50, unique = true)
    private String name;

    @Column(name = "description", nullable = false, length = 150)
    private String description;

    @Column(name = "location", nullable = true, length = 50)
    private String location;

    @Builder.Default
    @Column(name = "user_email", nullable = true)
    private String userEmail = null;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "schedule")
    private List<EnrolledSchedule> enrolledUsers;

    @OneToMany(mappedBy = "schedule")
    private List<BookedSchedule> likedUsers;
}
