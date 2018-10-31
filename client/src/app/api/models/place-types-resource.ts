/* tslint:disable */
import { PlaceTypeResource } from './place-type-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface PlaceTypesResource {
  resource?: string;
  label?: string;
  placeTypes?: Array<PlaceTypeResource>;
  meta?: ResourceMetaData;
}
