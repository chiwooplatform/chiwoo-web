package org.chiwooplatform.context.model;

import org.chiwooplatform.security.AuthUser;

public class UserSimple
    implements AuthUser {

    private String userId;

    private Integer userNo;

    public UserSimple() {
        super();
    }

    /**
     * @return the userId
     */
    public String userId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId( String userId ) {
        this.userId = userId;
    }

    /**
     * @return the userNo
     */
    public Integer userNo() {
        return userNo;
    }

    /**
     * @param userNo the userNo to set
     */
    public void setUserNo( Integer userNo ) {
        this.userNo = userNo;
    }
}
