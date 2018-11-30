/* tslint:disable */
import { OperationPatternResource } from './operation-pattern-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface RuleResource {
  resource?: string;
  label?: string;
  resultLabel?: string;
  resultResource?: string;
  operationPatterns?: Array<OperationPatternResource>;
  meta?: ResourceMetaData;
}
