package org.chiwooplatform.security.core;

import java.security.Principal;

public interface UserPrincipal
    extends Principal {

    public String getUserid();

    public Integer getUserno();
}
