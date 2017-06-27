package org.chiwooplatform.security.core;

import org.chiwooplatform.context.model.ParameterMap;

public interface PermissionResolver {

    boolean hasPermission( ParameterMap param );
}
