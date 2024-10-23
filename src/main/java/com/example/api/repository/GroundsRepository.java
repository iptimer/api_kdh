package com.example.api.repository;

import com.example.api.entity.Grounds;
import com.example.api.repository.search.GsearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroundsRepository extends JpaRepository<Grounds, Long>, GsearchRepository {

  // 경기 게시판에 대한 리뷰의 현재 신청한 인원 수를 출력
  @Query("select g, count(r), count(distinct r) " +
      "from Grounds g left outer join GroundsReviews r on r.grounds=g group by g ")
  Page<Object[]> getListPage(Pageable pageable);

  @Query("select g, max(p), count(r), count(distinct r) from Grounds g " +
      "left outer join Gphotos p on p.grounds = g " +
      "left outer join GroundsReviews r on r.grounds = g group by g ")
  Page<Object[]> getListPageMaxImg(Pageable pageable);

  @Query(value = "select g.gno, p.gpnum, p.gphotos_name, " +
      "r.members, count(r), g.maxpeople, g.reservation, g.groundstime " +
      "from db7.gphotos p left outer join db7.grounds g on g.gno=p.grounds_gno " +
      "left outer join db7.GroundsReviews r on g.gno=r.grounds_gno " +
      "where p.gpnum = " +
      "(select max(gpnum) from db7.gphotos p2 where p2.grounds_gno=g.gno) " +
      "group by g.gno ", nativeQuery = true)
  Page<Object[]> getListPageImgNative(Pageable pageable);

  // JPQL
  @Query("select g, p, count(r), count(distinct r) from Grounds g " +
      "left outer join Gphotos p on p.grounds = g " +
      "left outer join GroundsReviews r on r.grounds = g " +
      "where p.gpnum = (select max(p2.gpnum) from Gphotos p2 where p2.grounds=g) " +
      "group by g ")
  Page<Object[]> getListPageImgJPQL(Pageable pageable);

  @Query("select grounds, max(p.gpnum) from Gphotos p group by grounds")
  Page<Object[]> getMaxQuery(Pageable pageable);

  @Query("select g, p, count(r), count(r) " +
      "from Grounds g left outer join Gphotos p on p.grounds=g " +
      "left outer join GroundsReviews r on r.grounds = g " +
      "where g.gno = :gno group by p ")
  List<Object[]> getGroundsWithAll(Long gno); // 특정 게시글 조회

}