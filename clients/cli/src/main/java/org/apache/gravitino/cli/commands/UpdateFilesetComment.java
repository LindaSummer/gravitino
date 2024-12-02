/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.gravitino.cli.commands;

import org.apache.gravitino.NameIdentifier;
import org.apache.gravitino.cli.ErrorMessages;
import org.apache.gravitino.client.GravitinoClient;
import org.apache.gravitino.exceptions.NoSuchCatalogException;
import org.apache.gravitino.exceptions.NoSuchFilesetException;
import org.apache.gravitino.exceptions.NoSuchMetalakeException;
import org.apache.gravitino.exceptions.NoSuchSchemaException;
import org.apache.gravitino.file.FilesetChange;

/** Update the comment of a catalog. */
public class UpdateFilesetComment extends Command {

  protected final String metalake;
  protected final String catalog;
  protected final String schema;
  protected final String fileset;
  protected final String comment;

  /**
   * Update the comment of a fileset.
   *
   * @param url The URL of the Gravitino server.
   * @param ignoreVersions If true don't check the client/server versions match.
   * @param metalake The name of the metalake.
   * @param catalog The name of the catalog.
   * @param schema The name of the schema.
   * @param fileset The name of the fileset.
   * @param comment New metalake comment.
   */
  public UpdateFilesetComment(
      String url,
      boolean ignoreVersions,
      String metalake,
      String catalog,
      String schema,
      String fileset,
      String comment) {
    super(url, ignoreVersions);
    this.metalake = metalake;
    this.catalog = catalog;
    this.schema = schema;
    this.fileset = fileset;
    this.comment = comment;
  }

  /** Update the comment of a catalog. */
  @Override
  public void handle() {
    try {
      NameIdentifier filesetName = NameIdentifier.of(schema, fileset);
      GravitinoClient client = buildClient(metalake);
      FilesetChange change = FilesetChange.updateComment(comment);

      client.loadCatalog(catalog).asFilesetCatalog().alterFileset(filesetName, change);
    } catch (NoSuchMetalakeException err) {
      System.err.println(ErrorMessages.UNKNOWN_METALAKE);
      return;
    } catch (NoSuchCatalogException err) {
      System.err.println(ErrorMessages.UNKNOWN_CATALOG);
      return;
    } catch (NoSuchSchemaException err) {
      System.err.println(ErrorMessages.UNKNOWN_SCHEMA);
      return;
    } catch (NoSuchFilesetException err) {
      System.err.println(ErrorMessages.UNKNOWN_FILESET);
      return;
    } catch (Exception exp) {
      System.err.println(exp.getMessage());
      return;
    }

    System.out.println(fileset + " comment changed.");
  }
}