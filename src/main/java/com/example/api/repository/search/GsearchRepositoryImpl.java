package com.example.api.repository.search;

import com.example.api.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class GsearchRepositoryImpl extends QuerydslRepositorySupport
    implements GsearchRepository {
  public GsearchRepositoryImpl() {
    super(Grounds.class);
  }

//  @Override
//  public Feeds search1() {
//    log.info("search1.................");
//    QFeeds feeds = QFeeds.feeds;
//    QPhotos photos = QPhotos.photos;
//    QReviews reviews = QReviews.reviews;
//
//    JPQLQuery<Feeds> jpqlQuery = from(feeds);
//    jpqlQuery.leftJoin(bphotos).on(bphotos.feeds.eq(feeds));
//    jpqlQuery.leftJoin(reviews).on(reviews.feeds.eq(feeds));
//
//    JPQLQuery<Tuple> tuple = jpqlQuery.select(feeds, bphotos.feeds, reviews.count());
//    tuple.groupBy(feeds);
//
//    log.info("==========================");
//    log.info(tuple);
//    log.info("==========================");
//
//    List<Tuple> result = tuple.fetch();
//    log.info("result: " + result);
//
//    return null;
//  }

  @Override
  public Page<Object[]> searchPage(String type, String keyword,String day, Pageable pageable) {
    log.info("searchPage...............");

    //1) Q 도메인 확보
    QGrounds grounds = QGrounds.grounds;
    QGphotos gphotos = QGphotos.gphotos;
    QGroundsReviews groundsReviews = QGroundsReviews.groundsReviews;

    //2) Q 도메인 조인
    JPQLQuery<Grounds> jpqlQuery = from(grounds);
    jpqlQuery.leftJoin(gphotos).on(gphotos.grounds.eq(grounds));
    jpqlQuery.leftJoin(groundsReviews).on(groundsReviews.grounds.eq(grounds));

    //3) Tuple 생성 : 조인된 결과를 tuple로 생성
    JPQLQuery<Tuple> tuple = jpqlQuery.select(grounds, gphotos, groundsReviews, grounds.location, grounds.sports);

    //4) 조건절 생성
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    BooleanExpression expression = grounds.gno.gt(0L);  // gno가 0보다 큰 경우
    booleanBuilder.and(expression);

    //5) day 검색 필터링
    if (day != null && !day.isEmpty()) {
      try {
        int dayKeyword = Integer.parseInt(day);  // day 값을 int로 변환
        booleanBuilder.and(grounds.day.eq(dayKeyword));  // 정확한 날짜 필터링
      } catch (NumberFormatException e) {
        log.warn("Invalid day: {}", day);
      }
    }

    //6) 검색 조건 파악 - 제목, 내용 등 추가 검색 조건 처리
    if (type != null) {
      String[] typeArr = type.split("");
      BooleanBuilder conditionBuilder = new BooleanBuilder();
      for (String t : typeArr) {
        switch (t) {
          case "t":  // 제목 검색
            conditionBuilder.or(grounds.gtitle.contains(keyword));
            break;
          case "w":  // 위치 검색
            conditionBuilder.or(grounds.location.contains(keyword));
            break;
          case "c":  // 스포츠 종류 검색
            conditionBuilder.or(grounds.sports.contains(keyword));
            break;
        }
      }
      booleanBuilder.and(conditionBuilder);  // 날짜 필터링 후 추가 조건 적용
    }

    //7) 조건을 tuple에 적용
    tuple.where(booleanBuilder);

    //8) 정렬 조건 적용
    Sort sort = pageable.getSort();
    sort.stream().forEach(order -> {
      Order direction = order.isAscending() ? Order.ASC : Order.DESC;
      String prop = order.getProperty();
      PathBuilder orderByExpression = new PathBuilder<>(Grounds.class, "grounds");
      tuple.orderBy(new OrderSpecifier<>(direction, orderByExpression.get(prop)));
    });

    //9) 그룹핑
    tuple.groupBy(grounds);

    //10) 페이징 처리
    tuple.offset(pageable.getOffset());
    tuple.limit(pageable.getPageSize());

    //11) 결과 가져오기
    List<Tuple> result = tuple.fetch();
    log.info(result);

    //12) 총 갯수 가져오기
    long count = tuple.fetchCount();
    log.info("COUNT: " + count);

    //13) 페이지 객체 반환
    return new PageImpl<>(result.stream().map(t -> t.toArray()).collect(Collectors.toList()), pageable, count);
  }
}
