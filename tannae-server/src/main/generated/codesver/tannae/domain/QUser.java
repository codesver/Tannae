package codesver.tannae.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1349642743L;

    public static final QUser user = new QUser("user");

    public final StringPath email = createString("email");

    public final BooleanPath gender = createBoolean("gender");

    public final StringPath id = createString("id");

    public final BooleanPath isDriver = createBoolean("isDriver");

    public final StringPath name = createString("name");

    public final BooleanPath onBoard = createBoolean("onBoard");

    public final StringPath phone = createString("phone");

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final StringPath pw = createString("pw");

    public final StringPath rrn = createString("rrn");

    public final NumberPath<Float> score = createNumber("score", Float.class);

    public final NumberPath<Integer> usn = createNumber("usn", Integer.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

