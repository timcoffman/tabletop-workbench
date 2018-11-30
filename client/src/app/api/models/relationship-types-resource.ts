/* tslint:disable */
import { RelationshipTypeResource } from './relationship-type-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface RelationshipTypesResource {
  resource?: string;
  label?: string;
  relationshipTypes?: Array<RelationshipTypeResource>;
  meta?: ResourceMetaData;
}
