package com.example.demo.entities.campaign;

import com.example.demo.enums.CampaignStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "campaign")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campaign_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "goal_amount", nullable = false)
    private Double goalAmount;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CampaignStatus status;

    @Column(name = "donation_amount", nullable = false)
    private BigDecimal donationAmount;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @JsonIgnore
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Donation> donationList = new ArrayList<>();

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @Transient
    private Boolean isEffective;

    // Getters and Setters

    @PostLoad
    public void computeIsEffective() {
        LocalDate current = LocalDate.now();
        isEffective = (status  == CampaignStatus.ACTIVE) && !startDate.toLocalDate().isAfter(current)
                && !endDate.toLocalDate().isBefore(current);
    }

    // Other fields...

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "";
    }

}
