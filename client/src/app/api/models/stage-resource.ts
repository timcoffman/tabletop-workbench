/* tslint:disable */
import { RulesResource } from './rules-resource';
import { StagesResource } from './stages-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface StageResource {
  resource?: string;
  rules?: RulesResource;
<<<<<<< HEAD
  label?: string;
  stages?: StagesResource;
=======
  stages?: StagesResource;
  label?: string;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
  meta?: ResourceMetaData;
}
