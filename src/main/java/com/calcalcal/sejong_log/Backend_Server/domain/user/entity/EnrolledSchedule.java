package com.calcalcal.sejong_log.Backend_Server.domain.user.entity;

import com.calcalcal.sejong_log.Backend_Server.domain.schedule.entity.Schedule;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "enrolled_schedule")
public class EnrolledSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @Builder
    public EnrolledSchedule(User user, Schedule schedule) {
        this.user = user;
        this.schedule = schedule;
    }

    public EnrolledSchedule of() {
        return new EnrolledSchedule(
                user,
                schedule
        );
    }
}
