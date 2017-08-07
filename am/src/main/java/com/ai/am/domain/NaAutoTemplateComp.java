package com.ai.am.domain;

import javax.persistence.*;

/**
 * @author defaultekey
 * @date 2017/3/2
 */
@Entity
@Table(name = "NA_AUTO_TEMPLATE_COMP")
public class NaAutoTemplateComp {
    private Long relaId;
    private Long tempId;
    private Long compId;
    private Long compOrder;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_AUTO_TEMPLATE_COMP$SEQ")
    @SequenceGenerator(name="NA_AUTO_TEMPLATE_COMP$SEQ",sequenceName="NA_AUTO_TEMPLATE_COMP$SEQ",allocationSize=1)
    @Column(name = "RELA_ID")
    public Long getRelaId() {
        return relaId;
    }

    public void setRelaId(Long relaId) {
        this.relaId = relaId;
    }

    @Basic
    @Column(name = "TEMP_ID")
    public Long getTempId() {
        return tempId;
    }

    public void setTempId(Long tempId) {
        this.tempId = tempId;
    }

    @Basic
    @Column(name = "COMP_ID")
    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    @Basic
    @Column(name = "COMP_ORDER")
    public Long getCompOrder() {
        return compOrder;
    }

    public void setCompOrder(Long compOrder) {
        this.compOrder = compOrder;
    }

}
