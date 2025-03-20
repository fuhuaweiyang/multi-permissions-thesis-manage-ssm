package com.rabbiter.ol.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;

@TableName("modificationList") // 指定数据库表名
public class ModificationEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Long docId;           // 映射doc_id字段（驼峰命名）
    private Long stuId;           // 映射stu_id字段
    private Long teacherId;       // 映射teacher_id字段
    private String modificationWhat;  // TEXT类型（修改内容分类）
    private String modification;      // LONGTEXT类型（修改详情）
    private LocalDateTime modificationTime; // datetime类型（修改时间）

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Long getDocId() { return docId; }
    public void setDocId(Long docId) { this.docId = docId; }

    public Long getStuId() { return stuId; }
    public void setStuId(Long stuId) { this.stuId = stuId; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public String getModificationWhat() { return modificationWhat; }
    public void setModificationWhat(String modificationWhat) { this.modificationWhat = modificationWhat; }

    public String getModification() { return modification; }
    public void setModification(String modification) { this.modification = modification; }

    public LocalDateTime getModificationTime() { return modificationTime; }
    public void setModificationTime(LocalDateTime modificationTime) { this.modificationTime = modificationTime; }
}