/* tslint:disable */
import { RulesResource } from './rules-resource';
import { StagesResource } from './stages-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface StageResource {
  resource?: string;
  rules?: RulesResource;
  stages?: StagesResource;
  label?: string;
  meta?: ResourceMetaData;
}
