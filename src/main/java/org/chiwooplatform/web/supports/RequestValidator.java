package org.chiwooplatform.web.supports;

import org.chiwooplatform.web.exception.GeneralException;
import org.chiwooplatform.web.exception.client.BadRequestException;
import org.chiwooplatform.web.exception.client.InvalidMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Component
public class RequestValidator {

    private LocalValidatorFactoryBean localValidator;

    public void setLocalValidatorFactory( LocalValidatorFactoryBean localValidator ) {
        this.localValidator = localValidator;
    }

    public void validate( Object target, final long TXID )
        throws GeneralException {
        DataBinder binder = new DataBinder( target );
        // System.out.println( "Validator.getClass().getName(): " + localValidator.getClass().getName() );
        binder.setValidator( localValidator );
        try {
            binder.validate();
            // System.out.println( "RequestValidator.binder.getTarget(): " + binder.getTarget().getClass().getName() );
        } catch ( Exception e ) {
            throw new BadRequestException( HttpStatus.BAD_REQUEST, e.getMessage() );
        }
        BindingResult results = binder.getBindingResult();
        if ( results.hasErrors() ) {
            final String objectName = target.getClass().getSimpleName();
            InvalidMessageException ime = new InvalidMessageException( "Invalid Message Error", objectName, results );
            throw new GeneralException( ime, HttpStatus.BAD_REQUEST, TXID );
        }
    }
}
