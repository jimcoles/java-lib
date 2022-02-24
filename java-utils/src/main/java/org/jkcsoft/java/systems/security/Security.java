/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */
package org.jkcsoft.java.systems.security;

import java.security.Permission;

/**
 * This class can be reprogrammed depending on what underlying system is
 * used to store permissions (access control lists, etc).
 * <p>
 * Did not use JAAS authorization (permissions) scheme because it seems to want us
 * to use policy files to store permissions.
 *
 * @author coles
 */
public class Security {


    public static boolean hasPermission(String username, Permission perm) throws Exception {
        boolean has = false;

/* TODO: impl with JAAS
        UserDO tsessUser = TsessUpsGateway.getInstance().getDb().selectUserByUsername(username);

        if (tsessUser != null) {
            if (perm == Permission.ADMIN) {
                has = "admin".equals(username) ||
                        JavaHelper.toBoolean(tsessUser.getHasAdminPerm());
            }
            else if (perm == Permission.DEVICE_OWNER) {
                has = JavaHelper.toBoolean(tsessUser.getHasDeviceOwnerPerm());
            }
        }
        else {
            throw new Exception("User does not exist");
        }
*/

        return has;
    }

}
