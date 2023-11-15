package com.calcalcal.sejong_log.Backend_Server.domain.event.entity;

import com.calcalcal.sejong_log.Backend_Server.domain.category.entity.Category;
import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.BookedEvent;
import com.calcalcal.sejong_log.Backend_Server.domain.user.entity.EnrolledEvent;
import com.calcalcal.sejong_log.Backend_Server.global.entity.BaseTimestampEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "event")
public class Event extends BaseTimestampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description", nullable = false, length = 150)
    private String description;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "event")
    private List<EnrolledEvent> enrolledUsers;

    @OneToMany(mappedBy = "event")
    private List<BookedEvent> likedUsers;

    @Builder
    public Event(String name, String description, Category category, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
