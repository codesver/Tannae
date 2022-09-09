package codesver.tannae.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVehicle is a Querydsl query type for Vehicle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVehicle extends EntityPathBase<Vehicle> {

    private static final long serialVersionUID = -1528802258L;

    public static final QVehicle vehicle = new QVehicle("vehicle");

    public final BooleanPath gender = createBoolean("gender");

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final NumberPath<Integer> num = createNumber("num", Integer.class);

    public final BooleanPath run = createBoolean("run");

    public final BooleanPath share = createBoolean("share");

    public final NumberPath<Integer> usn = createNumber("usn", Integer.class);

    public final StringPath vrn = createString("vrn");

    public final NumberPath<Integer> vsn = createNumber("vsn", Integer.class);

    public QVehicle(String variable) {
        super(Vehicle.class, forVariable(variable));
    }

    public QVehicle(Path<? extends Vehicle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVehicle(PathMetadata metadata) {
        super(Vehicle.class, metadata);
    }

}

