package com.rabbiter.ol.service.impl;

import com.rabbiter.ol.dao.DocDao;
import com.rabbiter.ol.dao.ModificationDao;
import com.rabbiter.ol.entity.DocEntity;
import com.rabbiter.ol.entity.ModificationEntity;
import com.rabbiter.ol.service.ModificationService;
import com.rabbiter.ol.tool.diff_match_patch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedList;
import java.util.List;

@Service
public class ModificationServiceImpl implements ModificationService {

    @Autowired
    private ModificationDao modificationDao;

    @Autowired
    private DocDao docDao;

    public static LinkedList<diff_match_patch.Diff> generateDiff(String textA, String textB) {
        diff_match_patch dmp = new diff_match_patch();
        return dmp.diff_main(textA, textB);
    }

    public static String applyPatch(String textA, String patchText) {
        diff_match_patch dmp = new diff_match_patch();
        LinkedList<diff_match_patch.Patch> patches = new LinkedList<>(dmp.patch_fromText(patchText));
        Object[] result = dmp.patch_apply(patches, textA);
        return (String) result[0];
    }

    @Override
    public DocEntity getDocByModificationId(Long docId,Long modificationId) {
        DocEntity docEntity = docDao.findByDocId(docId);
        ModificationEntity modificationEntity = modificationDao.queryById(docId,modificationId);
        String patchText = modificationEntity.getModification();
        System.out.println(patchText);
//        diff_match_patch dmp = new diff_match_patch();
//        LinkedList<diff_match_patch.Diff> diffs = generateDiff(docEntity.getTxt(), docEntity.getTxt());
//
//        List<diff_match_patch.Patch> patches = dmp.patch_make(diffs);
//        String patchText = dmp.patch_toText(patches);
//        System.out.println("\nGenerated Patch:");
//        System.out.println(patchText);
        String oldText = docEntity.getTxt();
        String restoredTextB = applyPatch(oldText, patchText);
        System.out.println(oldText);
        System.out.println(restoredTextB);
        DocEntity docEntity1 = new DocEntity();
        docEntity1.setTxt(restoredTextB);
        return docEntity1;
    }
}
