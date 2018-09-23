package com.gen.type;

/**
 * @author fanhbon2018/9/21
 * @desc jdbc-java对应关系
 */
public enum TypeMapping {

    BIGINT("java.lang.Long","Long", "desc"),
    TINYINT("java.lang.Byte","Byte", "desc"),
    SMALLINT("java.lang.Short","Short", "desc"),
    MEDIUMIN("java.lang.Integer","Integer", "desc"),
    INTEGER("java.lang.Integer","Integer", "desc"),
    INT("java.lang.Integer","Integer", "desc"),
    FLOAT("java.lang.Float","Float", "desc"),
    DOUBLE("java.lang.Double","Double", "desc"),
    DECIMAL("java.math.BigDecimal","BigDecimal", "desc"),
    NUMERIC("java.math.BigDecimal","BigDecimal", "desc"),
    CHAR("java.lang.String","String", "desc"),
    VARCHAR("java.lang.String","String", "desc");

    private String path;
    private String type;
    private String desc;

    TypeMapping(String path, String type, String desc) {
        this.path = path;
        this.type = type;
        this.desc = desc;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static TypeMapping getJavaType(String jdbcType) {

        for (TypeMapping typeMapping : TypeMapping.values()) {
            if (typeMapping.name().equalsIgnoreCase(jdbcType)){
                return typeMapping;
            }
        }
        return VARCHAR;
    }

}
