package com.hockey.infrastructure.specifications;

import com.hockey.core.entity.BaseEntity;
import com.hockey.core.interfaces.SQLSpecification;

public class FindByIdSpecification<T extends BaseEntity> implements SQLSpecification {
    @Override
    public String toSqlQuery() {
        return String.format(
                "SELECT * FROM %1$s WHERE id = %2$d';", "12"
//                T.getTableName(),1
//                T.getId()
        );
    }
}
