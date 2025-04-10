package com.rabbiter.ol.service;

// MarkingService.java 服务接口

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rabbiter.ol.entity.MarkingEntity;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MarkingService extends IService<MarkingEntity> {
    void saveMarking(MarkingEntity marking);
    List<MarkingEntity> getByStudentId(Long stuId);
    MarkingEntity getByDocId(Long docId);
}