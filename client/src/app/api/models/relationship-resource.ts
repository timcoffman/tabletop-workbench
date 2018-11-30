/* tslint:disable */
import { ResourceMetaData } from './resource-meta-data';
export interface RelationshipResource {
  type?: string;
  label?: string;
  source?: string;
  destination?: string;
  meta?: ResourceMetaData;
}
