/* tslint:disable */
import { ResourceMetaData } from './resource-meta-data';
export interface PlacePrototypeResource {
  resource?: string;
  properties?: {[key: string]: string};
  placeType?: string;
  label?: string;
  meta?: ResourceMetaData;
}
