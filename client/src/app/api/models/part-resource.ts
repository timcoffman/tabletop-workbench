/* tslint:disable */
import { PlacesResource } from './places-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface PartResource {
  resource?: string;
  role?: string;
  label?: string;
  places?: PlacesResource;
  prototypeResource?: string;
  meta?: ResourceMetaData;
}
