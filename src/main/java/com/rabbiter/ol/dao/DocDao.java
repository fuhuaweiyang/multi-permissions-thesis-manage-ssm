package com.rabbiter.ol.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rabbiter.ol.entity.DocEntity;
import com.rabbiter.ol.entity.ExercisesEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.HashMap;

@Mapper
public interface DocDao extends BaseMapper<ExercisesEntity> {
    // 其他自定义的方法
    Long save(DocEntity doc);

    void update(DocEntity doc);

    void deleteById(Long id);

    DocEntity findByStuId(Long id);

    DocEntity findByDocId(Long id);

}
