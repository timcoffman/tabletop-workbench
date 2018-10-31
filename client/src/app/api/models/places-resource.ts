/* tslint:disable */
import { PlaceResource } from './place-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface PlacesResource {
  places?: Array<PlaceResource>;
  meta?: ResourceMetaData;
}
