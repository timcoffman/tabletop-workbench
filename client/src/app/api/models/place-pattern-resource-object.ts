/* tslint:disable */
import { PartPatternResourceObject } from './part-pattern-resource-object';
import { ResourceMetaData } from './resource-meta-data';
export interface PlacePatternResourceObject {
  type?: string;
<<<<<<< HEAD
  label?: string;
  part?: PartPatternResourceObject;
=======
  part?: PartPatternResourceObject;
  label?: string;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
  meta?: ResourceMetaData;
}
