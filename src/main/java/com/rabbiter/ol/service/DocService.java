package com.rabbiter.ol.service;

import com.rabbiter.ol.entity.DocEntity;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface DocService {
    DocEntity saveDoc(byte[] file,String title,Long stuId) throws Exception;
    DocEntity getDocById(Long id);
    void deleteDoc(Integer id);
}