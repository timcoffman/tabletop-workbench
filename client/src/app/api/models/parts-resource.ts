/* tslint:disable */
import { PartResource } from './part-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface PartsResource {
  parts?: Array<PartResource>;
  meta?: ResourceMetaData;
}
