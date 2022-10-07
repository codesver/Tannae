package codesver.tannae.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHistory is a Querydsl query type for History
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHistory extends EntityPathBase<History> {

    private static final long serialVersionUID = -943937130L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHistory history = new QHistory("history");

    public final DateTimePath<java.time.LocalDateTime> arrivalTime = createDateTime("arrivalTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> boardingTime = createDateTime("boardingTime", java.time.LocalDateTime.class);

    public final StringPath destination = createString("destination");

    public final NumberPath<Double> destinationLatitude = createNumber("destinationLatitude", Double.class);

    public final NumberPath<Double> destinationLongitude = createNumber("destinationLongitude", Double.class);

    public final BooleanPath end = createBoolean("end");

    public final NumberPath<Integer> hsn = createNumber("hsn", Integer.class);

    public final StringPath origin = createString("origin");

    public final NumberPath<Integer> originalDistance = createNumber("originalDistance", Integer.class);

    public final NumberPath<Integer> originalDuration = createNumber("originalDuration", Integer.class);

    public final NumberPath<Integer> originalFare = createNumber("originalFare", Integer.class);

    public final NumberPath<Double> originLatitude = createNumber("originLatitude", Double.class);

    public final NumberPath<Double> originLongitude = createNumber("originLongitude", Double.class);

    public final NumberPath<Integer> realDistance = createNumber("realDistance", Integer.class);

    public final NumberPath<Integer> realDuration = createNumber("realDuration", Integer.class);

    public final NumberPath<Integer> realFare = createNumber("realFare", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> requestTime = createDateTime("requestTime", java.time.LocalDateTime.class);

    public final BooleanPath share = createBoolean("share");

    public final QUser user;

    public final QVehicle vehicle;

    public QHistory(String variable) {
        this(History.class, forVariable(variable), INITS);
    }

    public QHistory(Path<? extends History> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHistory(PathMetadata metadata, PathInits inits) {
        this(History.class, metadata, inits);
    }

    public QHistory(Class<? extends History> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
        this.vehicle = inits.isInitialized("vehicle") ? new QVehicle(forProperty("vehicle"), inits.get("vehicle")) : null;
    }

}

