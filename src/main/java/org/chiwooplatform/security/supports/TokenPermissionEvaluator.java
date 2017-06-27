package org.chiwooplatform.security.supports;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import org.chiwooplatform.context.Constants;
import org.chiwooplatform.context.model.ParameterMap;
import org.chiwooplatform.security.authentication.RestAuthenticationToken;
import org.chiwooplatform.security.core.PermissionResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenPermissionEvaluator
    implements PermissionEvaluator {

    private final transient Logger logger = LoggerFactory.getLogger( TokenPermissionEvaluator.class );

    @Autowired
    private PermissionResolver permissionResolver;

    public TokenPermissionEvaluator() {
        super();
    }

    @Override
    public boolean hasPermission( Authentication authentication, Object requestArgs, Object targetPermId ) {
        if ( !( authentication instanceof RestAuthenticationToken ) ) {
            return false;
        }
        RestAuthenticationToken auth = (RestAuthenticationToken) authentication;
        final String principal = auth.getName();
        final String requestToken = (String) requestArgs;
        final String permissionId = (String) targetPermId;
        logger.debug( "principal: {}, requestToken: {}, permissionId: {}", principal, requestToken, permissionId );
        ParameterMap param = new ParameterMap();
        param.put( Constants.TOKEN, requestToken );
        param.put( Constants.PERM_CODE, permissionId );
        boolean valid = permissionResolver.hasPermission( param );
        return valid;
    }

    @Override
    public boolean hasPermission( Authentication authentication, Serializable targetId, String targetType,
                                  Object permission ) {
        logger.warn( "principal: {}, method: {}, permissionId", authentication, targetId, targetType );
        throw new UnsupportedOperationException( "ID based permission evaluation currently not supported." );
    }
}
