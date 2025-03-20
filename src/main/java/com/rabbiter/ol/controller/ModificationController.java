package com.rabbiter.ol.controller;

import com.rabbiter.ol.entity.DocEntity;
import com.rabbiter.ol.entity.ModificationEntity;
import com.rabbiter.ol.service.ModificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/modification")
public class ModificationController {
    @Autowired
    private ModificationService modificationService;

    @RequestMapping("/newDoc/{docId}/{modificationId}")
    public String getDocByModificationId(@PathVariable("docId") Long docId, @PathVariable("modificationId") Long modificationId) {
        DocEntity docEntity = modificationService.getDocByModificationId(docId,modificationId);
        return docEntity.getTxt();
    }
}
