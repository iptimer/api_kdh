// 구장 정보
package com.example.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Grounds extends BasicEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long gno;

  @ManyToOne(fetch = FetchType.LAZY)
  private Members members;

  private String gtitle;
  private String location;
  private String sports;
  private int price;

  public void changeGtitle(String gtitle) {this.gtitle = gtitle;}
}