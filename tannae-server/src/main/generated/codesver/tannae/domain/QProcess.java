package codesver.tannae.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProcess is a Querydsl query type for Process
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProcess extends EntityPathBase<Process> {

    private static final long serialVersionUID = 2114577265L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProcess process = new QProcess("process");

    public final NumberPath<Integer> passed = createNumber("passed", Integer.class);

    public final StringPath path = createString("path");

    public final NumberPath<Integer> psn = createNumber("psn", Integer.class);

    public final QVehicle vehicle;

    public QProcess(String variable) {
        this(Process.class, forVariable(variable), INITS);
    }

    public QProcess(Path<? extends Process> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProcess(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProcess(PathMetadata metadata, PathInits inits) {
        this(Process.class, metadata, inits);
    }

    public QProcess(Class<? extends Process> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.vehicle = inits.isInitialized("vehicle") ? new QVehicle(forProperty("vehicle"), inits.get("vehicle")) : null;
    }

}

