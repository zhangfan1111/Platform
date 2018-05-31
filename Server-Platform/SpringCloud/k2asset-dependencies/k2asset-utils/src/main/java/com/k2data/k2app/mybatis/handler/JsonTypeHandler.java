package com.k2data.k2app.mybatis.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.k2data.k2app.exception.K2Exception;
import com.k2data.k2app.utils.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * VARCHAR 存储 json 数据用到的 typeHandler
 *
 * @author lidong9144@163.com 17-3-21.
 */
public class JsonTypeHandler extends BaseTypeHandler<Object> {

    private ObjectMapper jsonObjectMapper = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, parseJson(parameter));
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return reflectJson(rs.getString(columnName));
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return reflectJson(rs.getString(columnIndex));
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return reflectJson(cs.getString(columnIndex));
    }

    private String parseJson(Object obj) {
        if (obj == null) {
            return null;
        }

        try {
            return jsonObjectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new K2Exception(e);
        }
    }

    private Object reflectJson(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }

        try {
            return jsonObjectMapper.readValue(json, Object.class);
        } catch (IOException e) {
            throw new K2Exception(e);
        }
    }

}
