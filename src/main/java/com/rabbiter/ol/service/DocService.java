package com.rabbiter.ol.service;

import com.rabbiter.ol.entity.DocEntity;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface DocService {
    DocEntity saveDoc(byte[] file,String title,Long stuId) throws Exception;
    Resource getDocById(Integer id) throws Exception;
    void deleteDoc(Integer id);
}