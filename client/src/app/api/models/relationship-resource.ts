/* tslint:disable */
import { ResourceMetaData } from './resource-meta-data';
export interface RelationshipResource {
  type?: string;
  source?: string;
  label?: string;
  destination?: string;
  meta?: ResourceMetaData;
}
