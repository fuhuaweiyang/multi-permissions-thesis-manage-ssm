// MarkingEntity.java
package com.rabbiter.ol.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

@TableName("marking")  // 假设表名为marking
public class MarkingEntity {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long docId;
    private Long stuId;
    private Long modificationId;
    private Long teacherId;
    private String markingWhat;
    private String marking;
    private Date markingTime;

    // isEmpty方法
    public boolean isEmpty() {
        return this.id == null &&
                this.docId == null &&
                this.stuId == null &&
                this.modificationId == null &&
                this.teacherId == null &&
                (this.markingWhat == null || this.markingWhat.isEmpty()) &&
                (this.marking == null || this.marking.isEmpty()) &&
                this.markingTime == null;
    }

    // toString方法
    @Override
    public String toString() {
        return "MarkingEntity{" +
                "id=" + id +
                ", docId=" + docId +
                ", stuId=" + stuId +
                ", modificationId=" + modificationId +
                ", teacherId=" + teacherId +
                ", markingWhat='" + markingWhat + '\'' +
                ", marking='" + marking + '\'' +
                ", markingTime=" + markingTime +
                '}';
    }

    // Getters and Setters
    // 这里需要为所有字段生成getter和setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getDocId() { return docId; }
    public void setDocId(Long docId) { this.docId = docId; }

    public Long getStuId() { return stuId; }
    public void setStuId(Long stuId) { this.stuId = stuId; }

    public Long getModificationId() { return modificationId; }
    public void setModificationId(Long modificationId) { this.modificationId = modificationId; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public String getMarkingWhat() { return markingWhat; }
    public void setMarkingWhat(String markingWhat) { this.markingWhat = markingWhat; }

    public String getMarking() { return marking; }
    public void setMarking(String marking) { this.marking = marking; }

    public Date getMarkingTime() { return markingTime; }
    public void setMarkingTime(Date markingTime) { this.markingTime = markingTime; }
}