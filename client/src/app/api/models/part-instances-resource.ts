/* tslint:disable */
import { PartInstanceResource } from './part-instance-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface PartInstancesResource {
  parts?: Array<PartInstanceResource>;
  meta?: ResourceMetaData;
}
