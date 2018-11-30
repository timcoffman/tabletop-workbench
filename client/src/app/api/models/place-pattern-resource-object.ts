/* tslint:disable */
import { PartPatternResourceObject } from './part-pattern-resource-object';
import { ResourceMetaData } from './resource-meta-data';
export interface PlacePatternResourceObject {
  type?: string;
  label?: string;
  part?: PartPatternResourceObject;
  meta?: ResourceMetaData;
}
