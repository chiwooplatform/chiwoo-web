package org.chiwooplatform.security.core;

public class UserInfo
    implements UserPrincipal {

    private String name;

    private String userid;

    private Integer userno;

    public UserInfo( String userid, Integer userno ) {
        this.userid = userid;
        this.name = userid;
        this.userno = userno;
    }

    public String getName() {
        return name;
    }

    public String getUserid() {
        return userid;
    }

    public Integer getUserno() {
        return userno;
    }
}
