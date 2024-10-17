package com.example.api.service;

import com.example.api.dto.GroundsReviewsDTO;
import com.example.api.dto.ReviewsDTO;
import com.example.api.entity.*;

import java.util.List;

public interface GroundsReviewService {
  List<GroundsReviewsDTO> getListOfGrounds(Long gno);

  Long register(GroundsReviewsDTO groundsReviewsDTO);

  void modify(GroundsReviewsDTO groundsReviewsDTO);

  void remove(Long grno);

  public default GroundsReviews dtoToEntity(GroundsReviewsDTO groundsReviewsDTO) {
    GroundsReviews groundsReviews = GroundsReviews.builder()
        .grno(groundsReviewsDTO.getGrno())
        .grounds(Grounds.builder().gno(groundsReviewsDTO.getGrno()).build())
        .members(Members.builder().mid(groundsReviewsDTO.getMid()).build())
        .maxpeople(groundsReviewsDTO.getMaxpeople())
        .nowpeople(groundsReviewsDTO.getNowpeople())
        .reservation(groundsReviewsDTO.getReservation())
        .groundsTime(groundsReviewsDTO.getGroundsTime())
        .build();
    return groundsReviews;
  }

  default GroundsReviewsDTO entityToDto(GroundsReviews groundsReviews) {
    GroundsReviewsDTO groundsReviewsDTO = GroundsReviewsDTO.builder()
        .gno(groundsReviews.getGrno())
        .grno(groundsReviews.getGrounds().getGno())
        .mid(groundsReviews.getMembers().getMid())
        .email(groundsReviews.getMembers().getEmail())
        .maxpeople(groundsReviews.getMaxpeople())
        .nowpeople(groundsReviews.getNowpeople())
        .regDate(groundsReviews.getRegDate())
        .modDate(groundsReviews.getModDate())
        .build();
    return groundsReviewsDTO;
  }
}
