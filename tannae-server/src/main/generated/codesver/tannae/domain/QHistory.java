package codesver.tannae.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QHistory is a Querydsl query type for History
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHistory extends EntityPathBase<History> {

    private static final long serialVersionUID = -943937130L;

    public static final QHistory history = new QHistory("history");

    public final StringPath arrivalTime = createString("arrivalTime");

    public final StringPath boardingTime = createString("boardingTime");

    public final StringPath destination = createString("destination");

    public final NumberPath<Double> destinationLatitude = createNumber("destinationLatitude", Double.class);

    public final NumberPath<Double> destinationLongitude = createNumber("destinationLongitude", Double.class);

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

    public final StringPath requestTime = createString("requestTime");

    public final BooleanPath share = createBoolean("share");

    public final NumberPath<Integer> usn = createNumber("usn", Integer.class);

    public final NumberPath<Integer> vsn = createNumber("vsn", Integer.class);

    public QHistory(String variable) {
        super(History.class, forVariable(variable));
    }

    public QHistory(Path<? extends History> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHistory(PathMetadata metadata) {
        super(History.class, metadata);
    }

}

