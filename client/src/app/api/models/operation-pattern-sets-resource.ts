/* tslint:disable */
import { OperationPatternSetResource } from './operation-pattern-set-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface OperationPatternSetsResource {
  resource?: string;
  operationPatternSets?: Array<OperationPatternSetResource>;
  meta?: ResourceMetaData;
}
