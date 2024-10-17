package com.example.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroundsReviewsDTO {
  private Long gno;
  private Long grno;
  private Long mid; // 구장 id
  private String maxpeople;
  private String nowpeople;
  private String reservation;
  private String groundsTime;

  private String email; // 구장 id
  private Integer likes;
  private String text; // 경기내용에 관한 text
  private LocalDateTime regDate, modDate;
}
