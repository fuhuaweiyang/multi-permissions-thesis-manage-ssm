package com.rabbiter.ol.controller;

import com.rabbiter.ol.entity.DocEntity;
import com.rabbiter.ol.entity.ModificationEntity;
import com.rabbiter.ol.service.ModificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/modification")
public class ModificationController {
    @Autowired
    private ModificationService modificationService;

    @GetMapping("/getDocByModificationId/{docId}/{modificationId}")
    public ModificationEntity getDocByModificationId(@PathVariable("docId") Long docId, @PathVariable("modificationId") Long modificationId) {
        ModificationEntity modificationEntity = modificationService.getDocByModificationId(docId,modificationId);
        return modificationEntity;
    }

    @PostMapping("/getModification")
    public ModificationEntity[] getModification(@RequestBody Map<String, Object> params) {
        Long stuId = Long.parseLong(params.get("stuId").toString());
        Integer page = Integer.parseInt(params.get("page").toString());
        Integer limit = Integer.parseInt(params.get("pageSize").toString());
        ModificationEntity[] modificationEntities = modificationService.selectByStuIdAndLimit(stuId,limit,page-1);
        return modificationEntities;
    }
}
