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
package org.apache.gravitino.catalog.lakehouse.hudi;

public class TestHudiTable extends HudiTable<TestHudiTable> {
  public static Builder builder() {
    return new Builder();
  }

  @Override
  public TestHudiTable fromHudiTable() {
    return this;
  }

  public static class Builder extends HudiTable.Builder<TestHudiTable> {
    @Override
    protected TestHudiTable simpleBuild() {
      TestHudiTable table = new TestHudiTable();
      table.name = name;
      table.comment = comment;
      table.properties = properties;
      table.auditInfo = auditInfo;
      return table;
    }

    @Override
    protected HudiTable<TestHudiTable> buildFromTable(TestHudiTable table) {
      return simpleBuild();
    }
  }
}
