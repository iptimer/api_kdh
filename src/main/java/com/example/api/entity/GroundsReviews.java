package com.example.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"grounds", "members"})
public class GroundsReviews {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long grno;

  @ManyToOne(fetch = FetchType.LAZY)
  private Grounds grounds;

  @ManyToOne(fetch = FetchType.LAZY)
  private Members members;

  private String maxpeople; // 최대 신청 가능한 인원수   ex 18
  private String nowpeople; // 현재 신청 한 인원수      ex 18
  private String reservation; // 예약마감 상태         ex 마감
  private String groundsTime; // 경기 시작 시간
  private LocalDateTime regDate,modDate;
  public void changeReservation(String reservation) {this.reservation = reservation;}

}



