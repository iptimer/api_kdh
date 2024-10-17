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
public class GroundsReviews2DTO {
  private Long grno;
  private Long grno2;
  private Long mid; // 구장 id

  private String email;
  private LocalDateTime regDate, modDate;
}
