/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package marquez.service.models;

import static java.util.stream.Collectors.toList;
import static marquez.common.models.CommonModelGenerator.newConnectionUrl;
import static marquez.common.models.CommonModelGenerator.newDatasetName;
import static marquez.common.models.CommonModelGenerator.newDatasetUrn;
import static marquez.common.models.CommonModelGenerator.newDatasourceName;
import static marquez.common.models.CommonModelGenerator.newDatasourceUrn;
import static marquez.common.models.CommonModelGenerator.newDescription;
import static marquez.common.models.CommonModelGenerator.newNamespaceName;
import static marquez.common.models.CommonModelGenerator.newOwnerName;

import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;
import marquez.common.models.ConnectionUrl;
import marquez.common.models.DatasetName;
import marquez.common.models.DatasetUrn;
import marquez.common.models.DatasourceName;
import marquez.common.models.DatasourceUrn;
import marquez.common.models.Description;

public final class ServiceModelGenerator {
  private ServiceModelGenerator() {}

  public static Namespace newNamespace() {
    return new Namespace(
        null,
        newNamespaceName().getValue(),
        newOwnerName().getValue(),
        newDescription().getValue());
  }

  public static List<Datasource> newDatasources(int limit) {
    return Stream.generate(() -> newDatasource()).limit(limit).collect(toList());
  }

  public static Datasource newDatasource() {
    return newDatasourceWith(newDatasourceName(), newDatasourceUrn(), newConnectionUrl());
  }

  public static Datasource newDatasourceWith(
      DatasourceName datasourceName, DatasourceUrn datasourceUrn, ConnectionUrl connectionUrl) {
    return Datasource.builder()
        .name(datasourceName)
        .createdAt(newTimestamp())
        .urn(datasourceUrn)
        .connectionUrl(connectionUrl)
        .build();
  }

  public static List<Dataset> newDatasets(int limit) {
    return Stream.generate(() -> newDataset()).limit(limit).collect(toList());
  }

  public static Dataset newDataset() {
    return newDataset(true);
  }

  public static Dataset newDataset(boolean hasDescription) {
    return newDatasetWith(
        newDatasetName(), newDatasetUrn(), hasDescription ? newDescription() : null);
  }

  public static Dataset newDatasetWith(
      DatasetName datasetName, DatasetUrn datasetUrn, Description description) {
    return Dataset.builder()
        .name(datasetName)
        .createdAt(newTimestamp())
        .urn(datasetUrn)
        .datasourceUrn(newDatasourceUrn())
        .description(description)
        .build();
  }

  private static Instant newTimestamp() {
    return Instant.now();
  }
}
