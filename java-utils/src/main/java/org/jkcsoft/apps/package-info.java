/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

/**
 * <p>Contains a basic object model of an Application itself, the central
 * notion being the {@link org.jkcsoft.apps.Application} object.
 * This object is analogous to, say, the ServletContext object, in the
 * Servlet API in that provices access to application-level services for
 * other objects that compose the application.  Services include:</p>
 * <ul>
 *     <li>Logging</li>
 *     <li>Configuration</li>
 *     <li>Local file system</li>
 * </ul>
 *
 * <h2>Package Specification</h2>
 * <p> Many element classes are named per the following: <br>
 * <h2>Related Documentation</h2>
 *
 * @author Jim Coles
 */
package org.jkcsoft.apps;