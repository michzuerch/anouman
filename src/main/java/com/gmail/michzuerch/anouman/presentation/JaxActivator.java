package com.gmail.michzuerch.anouman.presentation;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * JAXActivator is an arbitrary name, what is important is that javax.ws.rs.core.Application is extended
 * and the @ApplicationPath annotation is used with a "rest" path.  Without this the rest routes linked to
 * from index.html would not be found.
 */
@ApplicationPath("rest")
public class JaxActivator extends Application {
    // Left empty intentionally
}
