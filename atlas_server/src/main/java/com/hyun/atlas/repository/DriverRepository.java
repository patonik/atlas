package com.hyun.atlas.repository;

import com.hyun.atlas.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    @Query("""
        select  fn_convert_string(d.license)
                from Driver d
                where d.license is not null
                  and fn_russian_to_latin(d.license)  = fn_convert_string(:license)
        """)
    String getLicenseByLicense(String license);

    @Query("""
        SELECT Driver from Driver d
                where d.license is not null
                  and fn_russian_to_latin(d.license) = fn_convert_string(:license)
        """)
    Driver findByLicense(String license);
}
