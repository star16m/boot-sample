package star16m.bootsample.resource.entity;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes({InetAddress.class})
    public class InetAddressTypeHandler extends BaseTypeHandler<InetAddress> {
        @Override
        public void setNonNullParameter(PreparedStatement ps, int i, InetAddress parameter, JdbcType jdbcType) throws SQLException
        {
            PGobject object = new PGobject();
            object.setValue(parameter.getHostAddress());
            object.setType("inet");
            ps.setObject(i, object);
        }

        @Override
        public InetAddress getNullableResult(ResultSet rs, String columnName) throws SQLException
        {
            String v = rs.getString(columnName);
            InetAddress address = toInetAddress(v);
            if (rs.wasNull()) {
                return null;
            } else {
                return address;
            }
        }

        @Override
        public InetAddress getNullableResult(ResultSet rs, int columnIndex) throws SQLException
        {
            String v = rs.getString(columnIndex);
            InetAddress address = toInetAddress(v);
            if (rs.wasNull()) {
                return null;
            } else {
                return address;
            }
        }

        @Override
        public InetAddress getNullableResult(CallableStatement cs, int columnIndex) throws SQLException
        {
            String v = cs.getString(columnIndex);
            InetAddress address = toInetAddress(v);
            if (cs.wasNull()) {
                return null;
            } else {
                return address;
            }
        }

        private InetAddress toInetAddress(String v)
        {
            InetAddress address = null;
            try {
                address = InetAddress.getByName(v);
            } catch (UnknownHostException e) {
                return null;
            }
            return address;
        }
}
