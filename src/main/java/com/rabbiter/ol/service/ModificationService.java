package com.rabbiter.ol.service;

import com.rabbiter.ol.entity.DocEntity;

public interface ModificationService {
    DocEntity getDocByModificationId(Long docId,Long modificationId);
}
