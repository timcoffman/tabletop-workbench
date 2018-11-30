/* tslint:disable */
import { PlacePrototypesResource } from './place-prototypes-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface PartPrototypeResource {
  resource?: string;
  extends?: string;
<<<<<<< HEAD
  label?: string;
  role?: string;
=======
  role?: string;
  label?: string;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
  places?: PlacePrototypesResource;
  meta?: ResourceMetaData;
}
