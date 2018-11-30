/* tslint:disable */
import { PlacePrototypesResource } from './place-prototypes-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface PartPrototypeResource {
  resource?: string;
  extends?: string;
  label?: string;
  role?: string;
  places?: PlacePrototypesResource;
  meta?: ResourceMetaData;
}
