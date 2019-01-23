package com.hockey.infrastructure.specifications;

import com.hockey.core.entity.BaseEntity;
import com.hockey.core.interfaces.SQLSpecification;

public class FindAllSpecification<T extends BaseEntity> implements SQLSpecification {
    @Override
    public String toSqlQuery() {
        return String.format(
                "SELECT * FROM %1$s;", "12"
//                T.getTableName()
        );
    }
}
