/* tslint:disable */
import { RelationshipResource } from './relationship-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface RelationshipsResource {
  relationships?: Array<RelationshipResource>;
  meta?: ResourceMetaData;
}
