package school.management.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Entity
@Data
@Table(name = "settings")
public class Settings extends BaseEntity {

    private String theme;

    @Column(name = "casual_leaves")
    private Integer casualLeaves;

    @Column(name = "sick_leaves")
    private Integer sickLeaves;

    @Column(name = "earned_leaves")
    private Integer earnedLeaves;

    @Column(name = "two_level_approval")
    private boolean twoLevelApproval;

    @Column(name = "work_start")
    private LocalTime workStart;

    @Column(name = "work_end")
    private LocalTime workEnd;

    @Column(name = "grace_minutes")
    private Integer graceMinutes;
}