package com.hyun.atlas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "TC_PROG")
public class Program {
    @Id
    @Column(name = "PGM_ID", nullable = false, length = 10)
    private String pgmId;

    @Column(name = "PGM_SEQ", length = 10)
    private String pgmSeq;

    @Nationalized
    @Column(name = "PGM_NM", length = 30)
    private String pgmNm;

    @Column(name = "PGM_CD", length = 10)
    private String pgmCd;

    @Column(name = "USE_YN", length = 1)
    private String useYn;

    @Column(name = "PGM_TYP", length = 10)
    private String pgmTyp;

    @Nationalized
    @Column(name = "PGM_PATH", length = 100)
    private String pgmPath;

    @Column(name = "PGM_LVL", length = 1)
    private String pgmLvl;

    @Column(name = "PGM_ABLE_TAG", length = 1)
    private String pgmAbleTag;

    @Nationalized
    @Column(name = "PGM_IMG_PATH", length = 100)
    private String pgmImgPath;

    @Column(name = "GRP_FLD_NM", length = 1)
    private String grpFldNm;

    @Column(name = "DTL_FLD_NM", length = 4)
    private String dtlFldNm;

    @Nationalized
    @Column(name = "GRP_MENU_NM", length = 30)
    private String grpMenuNm;

    @Nationalized
    @Column(name = "DTL_MENU_NM", length = 30)
    private String dtlMenuNm;

    @Nationalized
    @Column(name = "PGM_EXT_NM", length = 100)
    private String pgmExtNm;

    @Nationalized
    @Column(name = "RMK", length = 200)
    private String rmk;

    @Column(name = "INP_USR", length = 30)
    private String inpUsr;

    @ColumnDefault("sysdate")
    @Column(name = "INP_YMD")
    private LocalDate inpYmd;

    @Column(name = "UPD_USR", length = 30)
    private String updUsr;

    @Column(name = "UPD_YMD")
    private LocalDate updYmd;

    @Column(name = "SECU_TAG", length = 1)
    private String secuTag;

    @Column(name = "HMCIS", length = 1)
    private String hmcis;

}