/* tslint:disable */
import { ResourceMetaData } from './resource-meta-data';
export interface RelationshipResource {
  type?: string;
<<<<<<< HEAD
  label?: string;
  source?: string;
=======
  source?: string;
  label?: string;
>>>>>>> dfa05573edc8209b3b75475353c18094affb6444
  destination?: string;
  meta?: ResourceMetaData;
}
