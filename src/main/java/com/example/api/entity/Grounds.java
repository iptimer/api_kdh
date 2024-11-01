package com.example.api.entity;

import com.example.api.dto.GphotosDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

// Grounds는 구장 정보를 저장하는 엔티티입니다.
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Grounds extends BasicEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long gno; // 구장 ID

  @ManyToOne(fetch = FetchType.LAZY)
  private Members members; // 구장 등록자

  private String gtitle; // 구장 제목
  private String location; // 위치
  private String sports; // 스포츠 종류
  private String info; // 구장 정보
  private String groundstime; // 경기 시간
  private int price; // 가격
  private int maxpeople; // 최대 인원
  private int day;
//  private int greviewsCnt; // GroundsReviews 개수

  // 제목 변경 메서드
  public void changeGtitle(String gtitle) { this.gtitle = gtitle; }
}
