/* tslint:disable */
import { PlacePatternResourceObject } from './place-pattern-resource-object';
import { ResourceMetaData } from './resource-meta-data';
export interface OperationPatternResource {
  type?: string;
  target?: PlacePatternResourceObject;
  description?: string;
  subject?: PlacePatternResourceObject;
  meta?: ResourceMetaData;
}
