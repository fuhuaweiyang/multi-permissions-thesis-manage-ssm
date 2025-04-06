package com.rabbiter.ol.service;

import com.rabbiter.ol.entity.DocEntity;
import com.rabbiter.ol.entity.ModificationEntity;

public interface ModificationService {
    ModificationEntity getDocByModificationId(Long docId,Long modificationId);

    ModificationEntity[] selectByStuIdAndLimit(Long stuId, Integer limit, Integer page);
}
