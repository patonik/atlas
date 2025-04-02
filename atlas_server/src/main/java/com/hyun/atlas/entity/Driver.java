package com.hyun.atlas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "PP_CM_DRIVER_LIST")
public class Driver {
    @Id
    @Column(name = "DRID", nullable = false)
    private Long id;

    @Column(name = "ZDRLN", length = 4000)
    private String license;

    @Column(name = "DRVNM", length = 30)
    private String surname;

    @Column(name = "DRNM2", length = 30)
    private String firstName;

    @Column(name = "DRNM3", length = 30)
    private String middleName;

    @Column(name = "VEND_CD", length = 10)
    private String vendorCode;

    @Column(name = "UPDDT")
    private LocalDate upddt;

    @Column(name = "FILNM", length = 20)
    private String filnm;

    @Column(name = "ZDRLN_NORM", length = 30)
    private String zdrlnNorm;

    @Column(name = "RETST", length = 1)
    private String retst;

    @Column(name = "UPD_FILNM", length = 30)
    private String updFilnm;

    @Column(name = "INP_DT")
    private LocalDate inpDt;

    @OneToMany(mappedBy = "driver")
    private Set<DriverDocument> driverDocuments = new LinkedHashSet<>();

}