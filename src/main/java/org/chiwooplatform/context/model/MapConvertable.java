package org.chiwooplatform.context.model;

import java.util.Map;

import java.io.Serializable;

public interface MapConvertable
    extends Serializable {

    /**
     * Convert Java Bean to Map
     * @return map Map object against PoJo
     * @throws Exception Error occurred while converting to a Map object.
     */
    Map<String, Object> toMap()
        throws Exception;
}
