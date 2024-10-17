package com.example.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"groundsreviews", "members"})
public class GroundsReviews2 {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long grno2;

  @ManyToOne(fetch = FetchType.LAZY)
  private GroundsReviews groundsReviews;

  @ManyToOne(fetch = FetchType.LAZY)
  private Members members;

  private String email;
  private String name;
  private LocalDateTime regDate, modDate;
}



