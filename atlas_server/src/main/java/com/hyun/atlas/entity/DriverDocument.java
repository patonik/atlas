package com.hyun.atlas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "PP_CM_DRIVER_DOCUMENTS")
public class DriverDocument {
    @Id
    @Column(name = "DD_ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "DRIVER_ID")
    private Driver driver;

    @Column(name = "DOC_SERIES")
    private Long docSeries;

    @Column(name = "DOC_NUMBER", length = 4000)
    private String docNumber;

    @Column(name = "DOC_WHO_ISSUED", length = 4000)
    private String docWhoIssued;

    @Column(name = "DOC_DEPARTAMENT_CODE")
    private Long docDepartamentCode;

    @ColumnDefault("sysdate")
    @Column(name = "DATE_FROM")
    private LocalDate dateFrom;

    @ColumnDefault("to_date('09.09.9999','DD.MM.YYYY')")
    @Column(name = "DATE_TO")
    private LocalDate dateTo;

    @Column(name = "COMMENTS", length = 4000)
    private String comments;

    @Column(name = "WHO_CREATED", length = 2000)
    private String whoCreated;

    @Column(name = "WHEN_CREATED")
    private LocalDate whenCreated;

    @Column(name = "WHO_EDITED", length = 2000)
    private String whoEdited;

    @Column(name = "WHEN_EDITED")
    private LocalDate whenEdited;

}