/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.beans;

/**
 *
 *
 */
public interface ISystemMeta
{
  /** Looks up sub-system meta objects by string id */
  public ISystemMeta getSubSystemMeta(String progid) throws Exception;

  /** Looks up IEntityMeta by string id. */
  public IEntityMeta getEntityMeta(String cls) throws Exception;
}