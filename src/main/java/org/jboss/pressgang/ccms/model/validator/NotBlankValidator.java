package org.jboss.pressgang.ccms.model.validator;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.mapping.Column;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.SingleTableSubclass;
import org.hibernate.validator.PropertyConstraint;
import org.hibernate.validator.Validator;

public class NotBlankValidator implements Validator<NotBlank>, PropertyConstraint, Serializable {

    @Override
    public void initialize(NotBlank parameters) {
    }

    @Override
    public boolean isValid(Object value) {
        if ( value == null ) return false;
        if ( value.getClass().isArray() ) {
            return Array.getLength(value) > 0;
        } else if ( value instanceof Collection) {
            return ( (Collection) value ).size() > 0;
        } else if ( value instanceof Map) {
            return ( (Map) value ).size() > 0;
        } else {
            return ((String) value).trim().length() > 0;
        }
    }

    @SuppressWarnings("unchecked")
    public void apply(Property property) {
        if ( ! ( property.getPersistentClass() instanceof SingleTableSubclass )
                && ! ( property.getValue() instanceof Collection ) ) {
            //single table should not be forced to null
            if ( !property.isComposite() ) { //composite should not add not-null on all columns
                Iterator<Column> iter = (Iterator<Column>) property.getColumnIterator();
                while ( iter.hasNext() ) {
                    iter.next().setNullable( false );
                }
            }
        }
    }
}

