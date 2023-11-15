package com.calcalcal.sejong_log.Backend_Server.domain.user.entity;

import com.calcalcal.sejong_log.Backend_Server.domain.event.entity.Event;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "enrolled_event")
public class EnrolledEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Builder
    public EnrolledEvent(User user, Event event) {
        this.user = user;
        this.event = event;
    }
}
