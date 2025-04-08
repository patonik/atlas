package com.hyun.atlas.repository;

import com.hyun.atlas.dto.MainPageDTO;
import com.hyun.atlas.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MainRepository extends JpaRepository<Program, Long> {

    @Query(nativeQuery = true, value = """
                with fr as (select :userCode u1, :userCode u2 from dual),
             vpauth as (SELECT
                            PGM_ID,
                            AUT_INP_TAG,
                            AUT_UPD_TAG,
                            AUT_DEL_TAG,
                            AUT_QRY_TAG,
                            AUT_PRT_TAG
                        FROM V_TC_PAUTH_LNK L
                                 LEFT JOIN fr ON 1=1
                        WHERE L.USR_CD = fr.u1
                          AND (AUT_INP_TAG = 'T'
                                   OR AUT_UPD_TAG = 'T'
                                   OR AUT_DEL_TAG = 'T'
                                   OR AUT_QRY_TAG = 'T'
                                   OR AUT_PRT_TAG = 'T')),
             use as ((select
                          pgm_cd,
                          LISTAGG(DISTINCT pgm_nm, ',') WITHIN GROUP (ORDER BY pgm_nm) AS pgm_nm,
                          count(*) AS cnt,
                          ROW_NUMBER() OVER (ORDER BY count(*) DESC) as prio,
                          'Fav' as category
                      from TC_PGM_USE_LOG lg
                               left join fr on 1 = 1
                      where lg.user_id like fr.u1
                        and use_dt > sysdate - 90
                        and pgm_cd is not null
                        and pgm_cd not like 'POTVISI640'
                      group by pgm_cd
                      order by cnt desc
                          fetch first 10 rows only)
                     union all
                     (select pgm_cd,
                             LISTAGG(DISTINCT pgm_nm, ',') WITHIN GROUP (ORDER BY pgm_nm) AS pgm_nm,
                             count(*) AS cnt,
                             ROW_NUMBER() OVER (ORDER BY MAX(use_dt) DESC) as prio,
                             'RECENT' as category
                      from TC_PGM_USE_LOG lg
                               left join fr on 1 = 1
                      where lg.user_id like fr.u1
                        and use_dt > sysdate - 30
                        and pgm_cd is not null
                        and pgm_cd not like 'POTVISI640'
                      group by pgm_cd
                      order by MAX(use_dt) desc
                          fetch first 10 rows only))
        SELECT
                PGM_NM AS programName,
                PGM_LVL AS programLevel,
                PGM_ID AS programId,
                PGM_ABLE_TAG AS programableTag,
                PGM_PATH AS programPath,
                GRP_FLD_NM AS groupFieldName,
                DTL_FLD_NM AS detailFieldName,
                PGM_CD AS programCode,
                GRP_MENU_NM AS groupMenuName,
                DTL_MENU_NM AS detailMenuName,
                PGM_EXT_NM AS programExtensionName,
                AUT_INP_TAG AS authorityInput,
                AUT_UPD_TAG AS authorityUpdate,
                AUT_DEL_TAG AS authorityDelete,
                AUT_QRY_TAG AS authorityQuery,
                AUT_PRT_TAG AS authorityPrint,
                PGM_SEQ AS programSequence
        FROM
           (SELECT
                A.PGM_NM as PGM_NM,
                A.PGM_LVL,
                A.PGM_ID,
                CASE
                    WHEN A.PGM_LVL IN ('1', '2') AND B.PGM_ID IS NULL AND A.PGM_NM = '|' THEN 'false'
                    WHEN A.PGM_LVL IN ('1', '2') THEN 'true'
                    WHEN A.GRP_MENU_NM = '' THEN 'false'
                    ELSE 'true'
                    END AS PGM_ABLE_TAG,
                A.PGM_PATH,
                A.GRP_FLD_NM,
                A.DTL_FLD_NM,
                A.PGM_CD,
                A.GRP_MENU_NM,
                A.DTL_MENU_NM,
                A.PGM_EXT_NM,
                B.AUT_INP_TAG,
                B.AUT_UPD_TAG,
                B.AUT_DEL_TAG,
                B.AUT_QRY_TAG,
                B.AUT_PRT_TAG,
                A.PGM_SEQ
            FROM TC_PROG A
                     JOIN vpauth B ON A.PGM_ID = B.PGM_ID
            WHERE USE_YN = 'Y'
              AND HMCIS = 'Y'
              UNION
            SELECT
                PGM_NM,
                PGM_LVL,
                PGM_ID,
                PGM_ABLE_TAG,
                PGM_PATH,
                GRP_FLD_NM,
                DTL_FLD_NM,
                PGM_CD,
                cast(category as NVARCHAR2 (30)),
                cast(category as NVARCHAR2 (30)),
                PGM_EXT_NM,
                AUT_INP_TAG,
                AUT_UPD_TAG,
                AUT_DEL_TAG,
                AUT_QRY_TAG,
                AUT_PRT_TAG,
                case when category = 'RECENT' then '0' || to_char(200000+prio) else '0' || to_char(100000+prio) end AS PGM_SEQ
            FROM (SELECT
                      ROW_NUMBER () OVER (PARTITION BY a.pgm_cd, category ORDER BY a.pgm_seq) rn,
                      prio,
                      A.PGM_NM,
                      A.PGM_LVL,
                      A.PGM_ID,
                      DECODE(A.GRP_MENU_NM,'','false','true') AS PGM_ABLE_TAG,
                      A.PGM_PATH,
                      A.GRP_FLD_NM,
                      A.DTL_FLD_NM,
                      A.PGM_CD,
                      A.GRP_MENU_NM,
                      A.DTL_MENU_NM,
                      A.PGM_EXT_NM,
                      B.AUT_INP_TAG,
                      B.AUT_UPD_TAG,
                      B.AUT_DEL_TAG,
                      B.AUT_QRY_TAG,
                      B.AUT_PRT_TAG,
                      A.PGM_SEQ,
                      fp.category
                  FROM TC_PROG A
                           left join vpauth B on A.PGM_ID = B.PGM_ID
                           join use fp on fp.pgm_cd like A.PGM_CD
                  WHERE USE_YN = 'Y' AND HMCIS = 'Y'
                  order by prio)
            where rn = 1)
        ORDER BY NLSSORT(PGM_SEQ, 'NLS_SORT=BINARY')
        """)
    List<MainPageDTO> findAllByUserCode(String userCode);
}
