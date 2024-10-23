package com.example.api.service;

import com.example.api.dto.GroundsReviewsDTO;
import com.example.api.entity.Grounds;
import com.example.api.entity.GroundsReviews;
import com.example.api.repository.GroundsReviewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class GroundsReviewServiceImpl implements GroundsReviewService {
  private final GroundsReviewsRepository groundsReviewsRepository;

  @Override
  public List<GroundsReviewsDTO> getListOfGrounds(Long gno) {
    List<GroundsReviews> result = groundsReviewsRepository.findByGrounds(
        Grounds.builder().gno(gno).build());
    return result.stream().map(this::entityToDto).collect(Collectors.toList());
  }

  @Override
  public Long register(GroundsReviewsDTO groundsReviewsDTO) {
    log.info("reviewsDTO >> ", groundsReviewsDTO);
    GroundsReviews groundsReviews = dtoToEntity(groundsReviewsDTO);
    groundsReviewsRepository.save(groundsReviews); // 리뷰 저장
    return groundsReviews.getGrno(); // 저장된 리뷰 ID 반환
  }

  @Override
  public void modify(GroundsReviewsDTO groundsReviewsDTO) {
    Optional<GroundsReviews> result = groundsReviewsRepository.findById(groundsReviewsDTO.getGrno());
    if (result.isPresent()) {
      GroundsReviews groundsReviews = result.get();
      groundsReviewsRepository.save(groundsReviews);
    }
  }

  @Override
  public void remove(Long grno) {
    Optional<GroundsReviews> result = groundsReviewsRepository.findById(grno);
    if (result.isPresent()) {
      groundsReviewsRepository.deleteById(grno); // 리뷰 삭제
    }
  }
}