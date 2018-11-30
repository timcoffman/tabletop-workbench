/* tslint:disable */
import { PlaceTypesResource } from './place-types-resource';
import { RelationshipTypesResource } from './relationship-types-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface ModelPluginResource {
  resource?: string;
  label?: string;
  placeTypes?: PlaceTypesResource;
  relationshipTypes?: RelationshipTypesResource;
  meta?: ResourceMetaData;
}
