/* tslint:disable */
import { PlacesResource } from './places-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface PartResource {
  resource?: string;
<<<<<<< HEAD
  label?: string;
  prototypeResource?: string;
  role?: string;
  places?: PlacesResource;
=======
  role?: string;
  label?: string;
  places?: PlacesResource;
  prototypeResource?: string;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
  meta?: ResourceMetaData;
}
