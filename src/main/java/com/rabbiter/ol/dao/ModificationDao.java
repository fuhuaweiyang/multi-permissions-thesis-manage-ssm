package com.rabbiter.ol.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rabbiter.ol.entity.HomeworkEntity;
import com.rabbiter.ol.entity.ModificationEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ModificationDao extends BaseMapper<HomeworkEntity> {
    ModificationEntity queryById(Long docId, Long modificationId);
    void save(ModificationEntity modification);
    ModificationEntity[] selectByStuIdAndLimit(Long stuId, Integer limit, Integer page);
}
