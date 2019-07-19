package star16m.bootsample.web.common.typehandler;

import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.postgresql.util.PGobject;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class BaseEnumTypeHandler<E extends Enum<E>> extends EnumTypeHandler<E> {
    private final String jdbcType;
    public BaseEnumTypeHandler(Class<E> type, String jdbcType) {
        super(type);
        this.jdbcType = jdbcType;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E enumValue, JdbcType jdbcType) throws SQLException {
        PGobject object = new PGobject();
        object.setType(this.jdbcType);
        object.setValue(enumValue.name());
        ps.setObject(i, object);
    }
}
