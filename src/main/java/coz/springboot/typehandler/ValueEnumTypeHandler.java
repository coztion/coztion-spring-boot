package coz.springboot.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import coz.springboot.enums.common.ValueEnum;
import coz.springboot.exception.ServerException;
import coz.springboot.global.constant.Message;

@MappedTypes(ValueEnum.class)
public class ValueEnumTypeHandler<E extends Enum<E> & ValueEnum> extends BaseTypeHandler<E> {

    private Class<E> type;

    public ValueEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String code = rs.getString(columnName);
        return code == null ? null : convertCodeToEnum(code);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String code = rs.getString(columnIndex);
        return code == null ? null : convertCodeToEnum(code);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String code = cs.getString(columnIndex);
        return code == null ? null : convertCodeToEnum(code);
    }

    private E convertCodeToEnum(String code) {
        for (E e : type.getEnumConstants()) {
            if (e.getValue().equals(code)) {
                return e;
            }
        }
        throw new ServerException(Message.Exception.INTERNAL_SERVER_ERROR);
    }
}
