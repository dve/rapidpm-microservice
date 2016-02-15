/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.rapidpm.microservice.persistence.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import org.rapidpm.ddi.Produces;
import org.rapidpm.ddi.producer.Producer;
import org.rapidpm.microservice.persistence.jdbc.JDBCConnectionPool.Builder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class JDBCConnectionPools {

  private static final Map<String, JDBCConnectionPool> POOL_MAP = new ConcurrentHashMap<>();
  private static final JDBCConnectionPools OUR_INSTANCE = new JDBCConnectionPools();

  private JDBCConnectionPools() {
  }

  public static JDBCConnectionPools instance() {
    return OUR_INSTANCE;
  }

  private JDBCConnectionPools withJDBCConnectionPool(JDBCConnectionPool pool) {
    POOL_MAP.put(pool.getPoolname(), pool);
    return this;
  }

  public Builder addJDBCConnectionPool(String poolname) {
    final Builder builder = JDBCConnectionPool.newBuilder().withParentBuilder(this);
    return builder.withPoolname(poolname);
  }

  public void shutdownPools() {
    POOL_MAP.forEach((k, v) -> v.close());
  }

  public void connectPools() {
    POOL_MAP.forEach((k, v) -> v.connect());
  }


  public HikariDataSource getDataSource(String poolname) {
    return  POOL_MAP.get(poolname) != null ? POOL_MAP.get(poolname).getDataSource() : null;
  }


  @Produces(JDBCConnectionPools.class)
  public static class JDBCConnectionPoolsProducer implements Producer<JDBCConnectionPools> {

    @Override
    public JDBCConnectionPools create() {
      return instance();
    }
  }


}
