package com.smbt.pickod.dto.template;

import lombok.Data;

import java.util.List;

@Data
public class TempImgDTO {
    private Long tempImgsId;
    private String tempImgsGuid;
    private String fileName;
    private String uploadPath;
    private Long tempImgsOrder;
    private Long placeId;
    private Long tempId;
    private Long tempDayId;
    private List<TempDayDTO> tempDayList; //Temp_day 정보
    private List<TemplateDTO> templateList; //Template 정보
    //place 리스트 받아 넣어야함
}