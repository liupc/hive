/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hive.ql.optimizer.optiq.cost;

import org.apache.hadoop.hive.ql.optimizer.optiq.reloperators.HiveRel;
import org.apache.hadoop.hive.ql.optimizer.optiq.reloperators.HiveTableScanRel;
import org.eigenbase.relopt.RelOptCost;

public class HiveCostUtil {
  private static final double cpuCostInNanoSec          = 1.0;
  private static final double netCostInNanoSec          = 150 * cpuCostInNanoSec;
  private static final double localFSWriteCostInNanoSec = 4 * netCostInNanoSec;
  private static final double localFSReadCostInNanoSec  = 4 * netCostInNanoSec;
  private static final double hDFSWriteCostInNanoSec    = 10 * localFSWriteCostInNanoSec;
  @SuppressWarnings("unused")
  private static final double hDFSReadCostInNanoSec     = 1.5 * localFSReadCostInNanoSec;

  public static RelOptCost computCardinalityBasedCost(HiveRel hr) {
    return new HiveCost(hr.getRows(), 0, 0);
  }

  public static HiveCost computeCost(HiveTableScanRel t) {
    double cardinality = t.getRows();
    return new HiveCost(cardinality, 0, hDFSWriteCostInNanoSec * cardinality * 0);
  }
}
