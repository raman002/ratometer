package io.cfapps.ratometer.repository.master;

import io.cfapps.ratometer.entity.master.CategoriesMaster;
import io.cfapps.ratometer.entity.master.RoleMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CategoriesMasterRepository extends JpaRepository<CategoriesMaster, Long> {

    List<CategoriesMaster> findAllByIsDeleted(Boolean isDeleted);

    @Query("select cm.pk from CategoriesMaster cm where cm.uuid = :uid")
    Long findPrimaryKeyByCategoryUid(@Param("uid") UUID uid);
}
