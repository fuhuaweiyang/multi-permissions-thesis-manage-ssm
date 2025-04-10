// M
package com.rabbiter.ol.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbiter.ol.dao.MarkingDao;
import com.rabbiter.ol.entity.MarkingEntity;
import com.rabbiter.ol.service.MarkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MarkingServiceImpl extends ServiceImpl<MarkingDao, MarkingEntity> implements MarkingService {

    @Autowired
    private MarkingDao markingDao;

    @Override
    public void saveMarking(MarkingEntity marking) {
        markingDao.saveMark(marking);
    }

    @Override
    public List<MarkingEntity> getByStudentId(Long stuId) {
        return markingDao.getByStudentId(stuId);
    }

    @Override
    public MarkingEntity getByDocId(Long docId) {
        return markingDao.getByDocId(docId);
    }
}
