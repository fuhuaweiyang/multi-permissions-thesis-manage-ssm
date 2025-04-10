package com.rabbiter.ol.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rabbiter.ol.entity.MarkingEntity;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface MarkingDao extends BaseMapper<MarkingEntity> {

    void saveMark(MarkingEntity marking);
    List<MarkingEntity> getByStudentId(Long stuId);
    MarkingEntity getByDocId(Long markingId);
}