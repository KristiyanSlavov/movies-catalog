package com.scalefocus.springtraining.moviecatalog.util;

import java.time.LocalDateTime;

/**
 * This GeneralConstant class
 * contains general constants which
 * will be reuse across the app.
 *
 * @author Kristiyan SLavov
 */
public final class GeneralConstant {

    private GeneralConstant(){}

    public static final LocalDateTime DATE_NOW = LocalDateTime.now();

    public static final String BEARER_TOKEN_TYPE = "bearer";
}
