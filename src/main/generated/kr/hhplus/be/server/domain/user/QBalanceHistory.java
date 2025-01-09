package kr.hhplus.be.server.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBalanceHistory is a Querydsl query type for BalanceHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBalanceHistory extends EntityPathBase<BalanceHistory> {

    private static final long serialVersionUID = 1081257470L;

    public static final QBalanceHistory balanceHistory = new QBalanceHistory("balanceHistory");

    public final kr.hhplus.be.server.domain.QBaseEntity _super = new kr.hhplus.be.server.domain.QBaseEntity(this);

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<BalanceHistoryType> type = createEnum("type", BalanceHistoryType.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QBalanceHistory(String variable) {
        super(BalanceHistory.class, forVariable(variable));
    }

    public QBalanceHistory(Path<? extends BalanceHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBalanceHistory(PathMetadata metadata) {
        super(BalanceHistory.class, metadata);
    }

}

