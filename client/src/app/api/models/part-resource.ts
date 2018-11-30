/* tslint:disable */
import { PlacesResource } from './places-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface PartResource {
  resource?: string;
  label?: string;
  prototypeResource?: string;
  role?: string;
  places?: PlacesResource;
  meta?: ResourceMetaData;
}
