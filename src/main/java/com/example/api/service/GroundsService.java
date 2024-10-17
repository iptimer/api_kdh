package com.example.api.service;

import com.example.api.dto.GroundsDTO;
import com.example.api.dto.PageRequestDTO;
import com.example.api.dto.PageResultDTO;
import com.example.api.dto.GphotosDTO;
import com.example.api.entity.Grounds;
import com.example.api.entity.Gphotos;
import org.apache.ibatis.jdbc.Null;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface GroundsService {
  Long register(GroundsDTO groundsDTO);

  PageResultDTO<GroundsDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

  GroundsDTO getGrounds(Long gno);

  void modify(GroundsDTO groundsDTO);

  List<String> removeWithReviewsAndGphotos(Long gno);

  void removeUuid(String uuid);

  default Map<String, Object> dtoToEntity(GroundsDTO groundsDTO) {
    Map<String, Object> entityMap = new HashMap<>();
    Grounds grounds = Grounds.builder().gno(groundsDTO.getGno())
        .gtitle(groundsDTO.getGtitle())
        .location(groundsDTO.getLocation())
        .sports(groundsDTO.getSports())
        .price(groundsDTO.getPrice())
        .build();

    entityMap.put("grounds", grounds);
    List<GphotosDTO> gphotosDTOList = groundsDTO.getGphotosDTOList();
    if (gphotosDTOList != null && gphotosDTOList.size() > 0) {
      List<Gphotos> gphotosList = gphotosDTOList.stream().map(
          new Function<GphotosDTO, Gphotos>() {
            @Override
            public Gphotos apply(GphotosDTO gphotosDTO) {
              Gphotos gphotos = Gphotos.builder()
                  .path(gphotosDTO.getPath())
                  .gphotosName(gphotosDTO.getGphotosName())
                  .uuid(gphotosDTO.getUuid())
                  .grounds(grounds)
                  .build();
              return gphotos;
            }
          }
      ).collect(Collectors.toList());
      entityMap.put("gphotosList", gphotosList);
    }
    return entityMap;
  }

  default GroundsDTO entityToDto(Grounds grounds, List<Gphotos> gphotosList, Long nowpeople, Long reviewsCnt) {
    GroundsDTO groundsDTO = GroundsDTO.builder()
        .gno(grounds.getGno())
        .gtitle(grounds.getGtitle())
        .location(grounds.getLocation())
        .sports(grounds.getSports())
        .price(grounds.getPrice())
        .regDate(grounds.getRegDate())
        .modDate(grounds.getModDate())
        .build();

    List<GphotosDTO> gphotosDTOList = new ArrayList<>();
    if (gphotosList != null && !gphotosList.isEmpty()) {
      gphotosDTOList = gphotosList.stream()
          .filter(gphotos -> gphotos != null)
          .map(gphotos -> GphotosDTO.builder()
              .gphotosName(gphotos.getGphotosName())
              .path(gphotos.getPath())
              .uuid(gphotos.getUuid())
              .build())
          .collect(Collectors.toList());
    }

    groundsDTO.setGphotosDTOList(gphotosDTOList);
    return groundsDTO;
  }


}