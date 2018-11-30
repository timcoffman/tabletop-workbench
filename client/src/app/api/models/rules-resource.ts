/* tslint:disable */
import { RuleResource } from './rule-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface RulesResource {
  rules?: Array<RuleResource>;
  meta?: ResourceMetaData;
}
